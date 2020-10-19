package com.cloud.mall.auth.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.auth.config.WXLoginProperties;
import com.cloud.mall.auth.entity.UserDO;
import com.cloud.mall.auth.mapper.UserMapper;
import com.cloud.mall.auth.service.AuthService;
import com.cloud.mall.auth.vo.AuthVO;
import com.cloud.mall.auth.vo.RegisterVO;
import com.cloud.mall.common.annotation.MallLog;
import com.cloud.mall.common.consts.AppConst;
import com.cloud.mall.common.exception.ApplicationException;
import com.cloud.mall.common.result.ResultCodeEnum;
import com.cloud.mall.common.utils.AppUtils;
import com.cloud.mall.common.utils.RestTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : breeze
 * @date : 2020/10/15
 * @description :
 */
@Service
@Slf4j
@EnableConfigurationProperties(WXLoginProperties.class)
public class AuthServiceImpl extends ServiceImpl<UserMapper, UserDO> implements AuthService {

    @Autowired
    private WXLoginProperties wxLoginProperties;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @MallLog
    @Override
    public UserDO authentication(AuthVO authVO) {

        //1.组装url
        String url = AppConst.WX_LOGIN_URL + wxLoginProperties.getAppId() + "&secret=" +
                wxLoginProperties.getSecret() + "&js_code=" +
                authVO.getCode() + "&grant_type=" +
                wxLoginProperties.getGrantType();
        //2.调取wx登录接口
        ResponseEntity<String> stringResponseEntity = RestTemplateUtils.get(url, String.class);
        String body = stringResponseEntity.getBody();
        Map map = JSONObject.parseObject(body, Map.class);

        //3.如果map为空或者errCode存在，授权登录异常
        Object errCode = map.get("errcode");
        if (CollectionUtil.isEmpty(map) || !StringUtils.isEmpty(errCode)) {
            log.error("微信授权登录返回参数：" + map.toString());
            throw new ApplicationException(ResultCodeEnum.WX_AUTHORIZE_LOGIN_ERROR);
        }

        String openid = map.get("openid").toString();
        String sessionKey = map.get("session_key").toString();
        //4.根据openid查询
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UserDO userDO = this.baseMapper.selectOne(wrapper);
        if (userDO == null) {
            //5.组装入库对象
            userDO = new UserDO();
            BeanUtils.copyProperties(authVO, userDO);
            userDO.setOpenid(openid);
            userDO.setSessionKey(sessionKey);

            //6.保存
            this.baseMapper.insert(userDO);
        } else {
            //7.更新
            userDO.setSessionKey(sessionKey);
            this.baseMapper.updateById(userDO);
        }

        return userDO;
    }

    @Override
    public void sendSms(String phone, int type) {

        //1.验证手机号码格式
        boolean mobilePhone = AppUtils.isMobilePhone(phone);
        if (!mobilePhone) {
            throw new ApplicationException(ResultCodeEnum.PHONE_FORMAT_ERROR);
        }

        //2.如果是绑定手机号码，先验证手机号码是否重复
        if (type == 0) {
            //判断手机号码是否被占用
            QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
            wrapper.eq("phone", phone);
            UserDO userDO = this.baseMapper.selectOne(wrapper);
            if (userDO != null) {
                throw new ApplicationException(ResultCodeEnum.PHONE_ALREADY_EXISTS_ERROR);
            }
        }

        //3.验证redis中存储的当前手机号获取验证码的次数
        //没有超过指定次数可以继续获取验证码
        //一个手机号码一天内最多获取3次验证码
        String countString = this.redisTemplate.opsForValue().get(AppConst.SMS_CODE_COUNT_PREFIX + phone);
        int count = 0;
        if (!StringUtils.isEmpty(countString)) {
            count = Integer.parseInt(countString);
        }
        if (count >= AppConst.SMS_CODE_MAX_COUNT) {
            throw new ApplicationException(ResultCodeEnum.SMS_CODE_COUNT_ERROR);
        }

        //4.验证redis中是否有未过期的验证码
        Boolean hasKey = this.redisTemplate.hasKey(AppConst.SMS_CODE_PREFIX + phone);
        if (hasKey) {
            throw new ApplicationException(ResultCodeEnum.REPEAT_GET_CODE_ERROR);
        }

        //5.随机生成6位验证码
        String code = AppUtils.generateRandomInt(6);
        Map<String, String> map = new HashMap<>(2);
        map.put("phone", phone);
        map.put("code", code);

        //6.发送rabbitMq
        this.amqpTemplate.convertAndSend("mall.message.exchange", "message.code", map);

        //7.发送之后将验证码存入redis，并设置过期时间
        this.redisTemplate.opsForValue().set(AppConst.SMS_CODE_PREFIX + phone, code, 5, TimeUnit.MINUTES);
        //8.修改获取验证码的次数，重置过期时间
        Long expire = this.redisTemplate.getExpire(AppConst.SMS_CODE_COUNT_PREFIX + phone, TimeUnit.MINUTES);
        if (expire == null || expire <= 0) {
            expire = (long) (24 * 60);
        }
        count++;
        this.redisTemplate.opsForValue().set(AppConst.SMS_CODE_COUNT_PREFIX + phone,
                String.valueOf(count), expire, TimeUnit.MINUTES);
    }

    @Override
    public UserDO register(RegisterVO registerVO) {

        String phone = registerVO.getPhone();
        String code = registerVO.getCode();
        //1.校验验证码
        checkPhoneCode(phone, code);

        //2.绑定手机
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", registerVO.getOpenid());
        UserDO userDO = new UserDO();
        userDO.setPhone(phone);
        this.baseMapper.update(userDO, wrapper);
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 校验手机验证码
     * @param phone
     * @param code
     */
    private void checkPhoneCode(String phone, String code) {
        //1.从redis中获取验证码
        String codeConfirm = this.redisTemplate.opsForValue().get(AppConst.SMS_CODE_PREFIX + phone);
        //2.redis中验证码不存在
        if (StringUtils.isEmpty(codeConfirm)) {
            throw new ApplicationException(ResultCodeEnum.CODE_HAS_EXPIRED_ERROR);
        }

        //3.验证码校验不通过
        if (!codeConfirm.equals(code)) {
            throw new ApplicationException(ResultCodeEnum.CODE_UNEQUAL_ERROR);
        }

        //4.验证完毕后删除验证码
        this.redisTemplate.delete(AppConst.SMS_CODE_PREFIX + phone);
    }

    @Override
    public void untiePhone(RegisterVO registerVO) {

        String code = registerVO.getCode();
        String phone = registerVO.getPhone();
        //1.校验验证码
        this.checkPhoneCode(phone, code);

        //2.解绑
        this.userMapper.updateOneUntiePhone(registerVO.getOpenid());
    }

    @Override
    public void updateBirthday(String birthday, Integer id) {

        UserDO userDO = new UserDO();
        userDO.setId(id);
        userDO.setBirthday(birthday);
        this.baseMapper.updateById(userDO);
    }
}

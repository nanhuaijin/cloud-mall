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
import com.cloud.mall.common.annotation.MallLog;
import com.cloud.mall.common.consts.AppConst;
import com.cloud.mall.common.exception.ApplicationException;
import com.cloud.mall.common.result.ResultCodeEnum;
import com.cloud.mall.common.utils.RestTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

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
}

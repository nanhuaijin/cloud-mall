package com.cloud.mall.user.mapper;

import com.cloud.mall.user.entity.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author breeze
 * @since 2020-10-16
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 根据手机号码解绑手机号码
     * @param openid
     * @return
     */
    void updateOneUntiePhone(String openid);
}

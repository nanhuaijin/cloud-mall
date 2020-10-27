package com.cloud.mall.user.service;

import com.cloud.mall.user.entity.AddressDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 收货地址表 服务类
 * </p>
 *
 * @author breeze
 * @since 2020-10-27
 */
public interface AddressService extends IService<AddressDO> {

    /**
     * 保存用户收货地址
     * @param addressDO
     */
    void saveAddress(AddressDO addressDO);
}

package com.cloud.mall.user.service.impl;

import com.cloud.mall.common.annotation.MallLog;
import com.cloud.mall.user.entity.AddressDO;
import com.cloud.mall.user.mapper.AddressMapper;
import com.cloud.mall.user.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收货地址表 服务实现类
 * </p>
 *
 * @author breeze
 * @since 2020-10-27
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressDO> implements AddressService {

    @MallLog
    @Override
    public void saveAddress(AddressDO addressDO) {
        this.baseMapper.insert(addressDO);
    }
}

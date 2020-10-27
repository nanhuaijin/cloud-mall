package com.cloud.mall.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.cloud.mall.common.entity.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 收货地址表
 * </p>
 *
 * @author breeze
 * @since 2020-10-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mall_address")
@ApiModel(value="Address对象", description="收货地址表")
public class AddressDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联mall_user表id")
    private Integer userId;

    @ApiModelProperty(value = "收货姓名")
    private String name;

    @ApiModelProperty(value = "0-女 1-男")
    private Integer sex;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "收货地址")
    private String address;

    @ApiModelProperty(value = "详细地址-门牌号")
    private String houseNumber;

    @ApiModelProperty(value = "0-无 1-家 2-公司 3-父母家")
    private Integer tag;

    @ApiModelProperty(value = "0-不是 1-默认收货地址")
    private Integer defaultAddress;

    @ApiModelProperty(value = "0-正常 1-删除")
    @TableLogic
    private Integer del;
}

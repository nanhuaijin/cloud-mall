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
 * 角色表
 * </p>
 *
 * @author breeze
 * @since 2020-10-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mall_role")
@ApiModel(value="Role对象", description="角色表")
public class RoleDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色")
    private String role;

    @ApiModelProperty(value = "创建人id")
    private Integer handlerId;

    @ApiModelProperty(value = "创建人昵称")
    private String handler;

    @ApiModelProperty(value = "0-未删除 1-删除")
    @TableLogic
    private Integer del;
}

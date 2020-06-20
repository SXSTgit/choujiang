package com.itsq.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sunqi
 * @since 2020-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin_menu_role")
@ApiModel(value="AdminMenuRole对象", description="")
public class AdminMenuRole implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "管理员ID")
    @TableField("adminId")
    private Integer adminId;

    @ApiModelProperty(value = "菜单ID")
    @TableField("sys_menu_id")
    private Integer sysMenuId;

    @ApiModelProperty(value = "是否被删除(0. 1.删除)")
    @TableField("state")
    @TableLogic
    private Integer state;


    public static final String ID = "id";

    public static final String ADMINID = "adminId";

    public static final String SYS_MENU_ID = "sys_menu_id";

    public static final String STATE = "state";

}

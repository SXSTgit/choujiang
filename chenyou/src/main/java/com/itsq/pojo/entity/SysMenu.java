package com.itsq.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author sunqi
 * @since 2020-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
@ApiModel(value="SysMenu对象", description="菜单管理")
public class SysMenu implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    @ApiModelProperty(value = "父菜单ID，一级菜单为-1")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "菜单名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "菜单URL")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "授权(多个用逗号分隔，如：user:list,user:create)")
    @TableField("perms")
    private String perms;

    @ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "菜单图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "排序")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty(value = "状态：1-正常，-1删除")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "1:dmp 2:PMP")
    @TableField("source")
    private Integer source;


    @ApiModelProperty(value = "如果是按钮则绑定页面的menu_id")
    @TableField("parenttwo_id")
    private Integer parenttwoId;


    @TableField(exist = false)
    private List<SysMenu> menuListTwo;
    @TableField(exist = false)
    private List<SysMenu> menuListThree;


    public static final String PARENTTWO_ID = "parenttwo_id";

    public static final String MENU_ID = "menu_id";

    public static final String PARENT_ID = "parent_id";

    public static final String NAME = "name";

    public static final String URL = "url";

    public static final String PERMS = "perms";

    public static final String TYPE = "type";

    public static final String ICON = "icon";

    public static final String ORDER_NUM = "order_num";

    public static final String STATUS = "status";

    public static final String SOURCE = "source";

}

package com.itsq.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author sunqi
 * @since 2020-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("manager")
@ApiModel(value="Manager对象", description="")
public class Manager implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "登录名（唯一）")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "cre_date",fill = FieldFill.INSERT)
    private Date creDate;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "upd_date",fill = FieldFill.UPDATE)
    private Date updDate;

    @ApiModelProperty(value = "状态0.正常 1.冻结")
    @TableField("is_status")
    private Integer isStatus;

    @ApiModelProperty(value = "性别(0.男1.女)")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(value = "管理员头像")
    @TableField("head_image")
    private String headImage;

    @ApiModelProperty(value = "是否可用0.可用1.删除")
    @TableField("state")
    @TableLogic
    private Integer state;

    @ApiModelProperty(value = "是否为超级管理员 0.是 1.否")
    @TableField("is_admin")
    private Integer isAdmin;

    @ApiModelProperty(value = "详细住址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "身份证号")
    @TableField("identity")
    private String identity;

    @ApiModelProperty(value = "微信号")
    @TableField("vx_code")
    private String vxCode;


    @ApiModelProperty(value = "0.店铺1.员工")
    private Integer  position;

    public static final String ID = "id";

    public static final String PHONE = "phone";

    public static final String PASSWORD = "password";

    public static final String USER_NAME = "user_name";

    public static final String CRE_DATE = "cre_date";

    public static final String UPD_DATE = "upd_date";

    public static final String IS_STATUS = "is_status";

    public static final String SEX = "sex";

    public static final String HEAD_IMAGE = "head_image";

    public static final String STATE = "state";

    public static final String IS_ADMIN = "is_admin";

    public static final String ADDRESS = "address";

    public static final String IDENTITY = "identity";

    public static final String VX_CODE = "vx_code";


    public static final String POSITION = "position";

}

package com.itsq.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
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
@TableName("user")
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "真实用户名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "登录名称（唯一）")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "详细地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "省")
    @TableField("sheng")
    private String sheng;

    @ApiModelProperty(value = "市")
    @TableField("shi")
    private String shi;

    @ApiModelProperty(value = "区")
    @TableField("qu")
    private String qu;

    @ApiModelProperty(value = "推荐人Id")
    @TableField("referrer_id")
    private Integer referrerId;

    @ApiModelProperty(value = "身份证号")
    @TableField("identity")
    private String identity;

    @ApiModelProperty(value = "ip")
    @TableField("ip")
    private String ip;

    @ApiModelProperty(value = "出生日期")
    @TableField("birthday")
    private Date birthday;

    @ApiModelProperty(value = "是否可用0.可用1.删除")
    @TableField("state")
    @TableLogic
    private Integer state;

    @ApiModelProperty(value = "性别(0.男1.女)")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "cre_date",fill = FieldFill.INSERT)
    private Date creDate;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "upd_date",fill = FieldFill.UPDATE)
    private Date updDate;

    @ApiModelProperty(value = "邮箱")
    @TableField("user_email")
    private String userEmail;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "头像")
    @TableField("head_image")
    private String headImage;

    @ApiModelProperty(value = "微信号")
    @TableField("vx_code")
    private String vxCode;

    @ApiModelProperty(value = "状态0.正常 1.冻结")
    @TableField("is_status")
    private Integer isStatus;


    @TableField("amount")
   private BigDecimal amount;

    @ApiModelProperty(value = "0.店铺1.员工")
    private Integer  position;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String AMOUNT = "amount";

    public static final String POSITION = "position";

    public static final String PHONE = "phone";

    public static final String USER_NAME = "user_name";

    public static final String ADDRESS = "address";

    public static final String SHENG = "sheng";

    public static final String SHI = "shi";

    public static final String QU = "qu";

    public static final String REFERRER_ID = "referrer_id";

    public static final String IDENTITY = "identity";

    public static final String IP = "ip";

    public static final String BIRTHDAY = "birthday";

    public static final String STATE = "state";

    public static final String SEX = "sex";

    public static final String CRE_DATE = "cre_date";

    public static final String UPD_DATE = "upd_date";

    public static final String USER_EMAIL = "user_email";

    public static final String PASSWORD = "password";

    public static final String HEAD_IMAGE = "head_image";

    public static final String VX_CODE = "vx_code";

    public static final String IS_STATUS = "is_status";

    

}

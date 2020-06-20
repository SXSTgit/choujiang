package com.itsq.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @author sq
 * @date 2020/4/7  14:00
 */
@Data
@AllArgsConstructor
public class AddUserDto {
    @ApiModelProperty(value = "真实用户名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "登录名称（唯一）")
    private String userName;

    @ApiModelProperty(value = "推荐人Id")
    private Integer referrerId;

    @ApiModelProperty(value = "身份证号")
    private String identity;

    @ApiModelProperty(value = "ip")
    private String ip;

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

    @ApiModelProperty(value = "性别(0.男1.女)")
    @TableField("sex")
    private Integer sex;
}

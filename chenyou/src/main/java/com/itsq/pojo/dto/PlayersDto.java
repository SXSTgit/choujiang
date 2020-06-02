package com.itsq.pojo.dto;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
@Data
public class PlayersDto implements Serializable {

    @ApiModelProperty(value = "邮箱")
    @TableField("number")
    private String number;

    @ApiModelProperty(value = "密码")
    @TableField("pwd")
    private String pwd;

    @ApiModelProperty(value = "验证码")
    @TableField("code")
    private String code;

}

package com.itsq.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sq
 * @date 2020/4/13  10:17
 */
@Data
public class updatePassWordDto {
    private int id;
    private String newPassWord;
    private String oldPassWord;
    @ApiModelProperty("1.初始化密码 2.修改密码")
    private int chushiorUpdatePassWord;
}

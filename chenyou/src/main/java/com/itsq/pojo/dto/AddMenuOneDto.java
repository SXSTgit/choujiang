package com.itsq.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sq
 * @date 2020/4/12  16:18
 */
@Data
public class AddMenuOneDto {
    @ApiModelProperty(value = "导航栏名称")
    private String name;

    @ApiModelProperty(value = "导航栏图标 例:fa fa-user")
    private String icon;
}

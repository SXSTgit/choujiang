package com.itsq.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sq
 * @date 2020/4/12  16:43
 */
@Data
public class AddMenuTwoDto {
    @ApiModelProperty(value = "父菜单ID，一级菜单为-1")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "菜单URL")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "页面名称")
    @TableField("name")
    private String name;
}

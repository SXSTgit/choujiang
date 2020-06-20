package com.itsq.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sq
 * @date 2020/4/12  18:59
 */
@Data
public class UpdateMenuInfoDto {
    //修改 URL 名称 By menuId icon

    @ApiModelProperty(value = "menu_id主键")
    @TableId(value = "menu_id")
    private Long menuId;

    @ApiModelProperty(value = "菜单URL")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "页面名称")
    @TableField("name")
    private String name;


    @ApiModelProperty(value = "图标")
    @TableField("icon")
    private String icon;
}

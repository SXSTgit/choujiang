package com.itsq.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sq
 * @date 2020/4/12  18:41
 */
@Data
public class AddMenuThreeDto {
    @ApiModelProperty(value = "父菜单ID，一级菜单为-1")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "菜单URL")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "页面名称")
    @TableField("name")
    private String name;


    @ApiModelProperty(value = "三级")
    @TableField("parenttwo_id")
    private Integer parenttwoId;

}

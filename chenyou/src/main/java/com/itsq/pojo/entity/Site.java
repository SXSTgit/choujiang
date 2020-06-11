package com.itsq.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @author 史先帅
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("site")
@ApiModel(value="Site对象", description="")
public class Site implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "站点信息")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "网站名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "网站副标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "关键字")
    @TableField("keyword")
    private String keyword;

    @ApiModelProperty(value = "网站描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "版权信息")
    @TableField("copyright")
    private String copyright;

    @ApiModelProperty(value = "浏览器ico图标")
    @TableField("ico")
    private String ico;

    @ApiModelProperty(value = "站点LOGO")
    @TableField("Logo")
    private String Logo;

    @ApiModelProperty(value = "站点开关")
    @TableField("switchs")
    private Integer switchs;

    @ApiModelProperty(value = "站点关闭提示")
    @TableField("tips")
    private String tips;

    @ApiModelProperty(value = "后台管理路径")
    @TableField("path")
    private String path;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String TITLE = "title";

    public static final String KEYWORD = "keyword";

    public static final String DESCRIPTION = "description";

    public static final String COPYRIGHT = "copyright";

    public static final String ICO = "ico";

    public static final String LOGO = "Logo";

    public static final String SWITCHS = "switchs";

    public static final String TIPS = "tips";

    public static final String PATH = "path";

}

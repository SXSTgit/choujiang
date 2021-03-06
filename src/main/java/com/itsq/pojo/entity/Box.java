package com.itsq.pojo.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

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
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("box")
@ApiModel(value="Box对象", description="")
public class Box implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "箱子")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "主图")
    @TableField("image")
    private String image;

    @ApiModelProperty(value = "价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "状态 0.正常1.冻结")
    @TableField("is_status")
    private Integer isStatus;

    @ApiModelProperty(value = "库存")
    @TableField("count")
    private Integer count;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private Integer type;

    @TableField(value = "cr_date",fill = FieldFill.INSERT)
    private Date crDate;

    @TableField(value = "out_time")
    private Date outTime;

    @TableField(exist = false)
    private List<Arms> armsList;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String IMAGE = "image";

    public static final String PRICE = "price";

    public static final String IS_STATUS = "is_status";

    public static final String CR_DATE = "cr_date";

    public static final String COUNT = "count";

    public static final String TYPE = "type";

    public static final String OUT_TIME = "out_time";
}

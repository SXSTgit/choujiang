package com.itsq.pojo.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

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
 * @author 史先帅
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("arms")
@ApiModel(value="Arms对象", description="")
public class Arms implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "武器")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "级别")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "主图")
    @TableField("imageUrl")
    private String imageUrl;

    @ApiModelProperty(value = "价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "库存")
    @TableField("count")
    private Integer count;

    @ApiModelProperty(value = "第三方武器id")
    @TableField("product_id")
    private Integer productId;

    @ApiModelProperty(value = "状态0.正常1.冻结")
    @TableField("is_status")
    private Integer isStatus;

    @TableField(value = "cre_date",fill = FieldFill.INSERT)
    private Date crDate;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String TYPE = "type";

    public static final String IMAGEUTRL = "imageUrl";

    public static final String PRICE = "price";

    public static final String COUNT = "count";

    public static final String PRODUCT_ID = "product_id";

    public static final String IS_STATUS = "is_status";

    public static final String CR_DATE = "cre_date";

}

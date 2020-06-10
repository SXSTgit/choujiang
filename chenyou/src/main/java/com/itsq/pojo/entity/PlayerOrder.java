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
 * @since 2020-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("player_order")
@ApiModel(value="PlayerOrder对象", description="")
public class PlayerOrder implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单")
    @TableField("number")
    private String number;

    @ApiModelProperty(value = "用户id")
    @TableField("player_id")
    private Integer playerId;

    @ApiModelProperty(value = "武器")
    @TableField("arms_id")
    private Integer armsId;

    @ApiModelProperty(value = "状态 0.成功1.失败")
    @TableField("is_status")
    private Integer isStatus;

    @ApiModelProperty(value = "买入价格")
    @TableField("buyPrice")
    private BigDecimal buyPrice;

    @TableField(value = "cre_date",fill = FieldFill.INSERT)
    private Date creDate;


    public static final String ID = "id";

    public static final String NUMBER = "number";

    public static final String PLAYER_ID = "player_id";

    public static final String ARMS_ID = "arms_id";

    public static final String IS_STATUS = "is_status";

    public static final String BUYPRICE = "buyPrice";

    public static final String CRE_DATE = "cre_date";

}

package com.itsq.pojo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("recharge_record")
@ApiModel(value="RechargeRecord对象", description="")
public class RechargeRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "充值记录")
    @TableField("id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("players_id")
    private Integer playersId;

    @ApiModelProperty(value = "充值金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "1.微信2.支付宝")
    @TableField("type")
    private Integer type;

    @TableField("cr_date")
    private Date crDate;


    public static final String ID = "id";

    public static final String PLAYERS_ID = "players_id";

    public static final String AMOUNT = "amount";

    public static final String TYPE = "type";

    public static final String CR_DATE = "cr_date";

}

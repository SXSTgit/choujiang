package com.itsq.pojo.entity;

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
@TableName("box_arms")
@ApiModel(value="BoxArms对象", description="")
public class BoxArms implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "箱子武器关联表")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "箱子id")
    @TableField("box_id")
    private Integer boxId;

    @ApiModelProperty(value = "武器id")
    @TableField("arms_id")
    private Integer armsId;

    @ApiModelProperty(value = "中奖几率")
    @TableField("chance")
    private Integer chance;

    @ApiModelProperty(value = "库存")
    @TableField("count")
    private Integer count;

    @ApiModelProperty(value = "状态 0.正常1.冻结")
    @TableField("is_status")
    private Integer isStatus;

    @TableField(value = "cr_date",fill = FieldFill.INSERT)
    private Date crDate;


    public static final String ID = "id";

    public static final String BOX_ID = "box_id";

    public static final String ARMS_ID = "arms_id";

    public static final String CHANCE = "chance";

    public static final String COUNT = "count";

    public static final String IS_STATUS = "is_status";

    public static final String CR_DATE = "cr_date";

}

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
 * @since 2020-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("player_box_arms")
@ApiModel(value = "PlayerBoxArms对象", description = "")
public class PlayerBoxArms implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户开箱记录")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联用户id")
    @TableField("player_id")
    private Integer playerId;

    @ApiModelProperty(value = "关联箱子id")
    @TableField("box_id")
    private Integer boxId;

    @ApiModelProperty(value = "关联武器id")
    @TableField("arms_id")
    private Integer armsId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "cre_date", fill = FieldFill.INSERT)
    private Date creDate;

    @TableField(exist = false)
    private Box box;
    @TableField(exist = false)
    private Arms arms;

    @TableField(exist = false)
    private Players players;

    public static final String ID = "id";

    public static final String PLAYER_ID = "player_id";

    public static final String BOX_ID = "box_id";

    public static final String ARMS_ID = "arms_id";

    public static final String CRE_DATE = "cre_date";

}

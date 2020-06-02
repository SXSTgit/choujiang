package com.itsq.pojo.entity;

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
@TableName("players_arms")
@ApiModel(value="PlayersArms对象", description="")
public class PlayersArms implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户 武器关联表")
    @TableField("id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("players_id")
    private Integer playersId;

    @ApiModelProperty(value = "武器id")
    @TableField("arms_id")
    private Integer armsId;

    @ApiModelProperty(value = "箱子id")
    @TableField("box_id")
    private Integer boxId;

    @ApiModelProperty(value = "状态  0.未出售1.已出售2.已取回")
    @TableField("is_status")
    private Integer isStatus;

    @ApiModelProperty(value = "0.开箱1.升级")
    @TableField("type")
    private Integer type;

    @TableField("cr_date")
    private Date crDate;


    public static final String ID = "id";

    public static final String PLAYERS_ID = "players_id";

    public static final String ARMS_ID = "arms_id";

    public static final String BOX_ID = "box_id";

    public static final String IS_STATUS = "is_status";

    public static final String TYPE = "type";

    public static final String CR_DATE = "cr_date";

}

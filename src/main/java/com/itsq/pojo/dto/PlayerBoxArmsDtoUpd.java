package com.itsq.pojo.dto;

import com.baomidou.mybatisplus.annotation.*;
import com.itsq.pojo.entity.Arms;
import com.itsq.pojo.entity.Box;
import com.itsq.pojo.entity.Players;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-04
 */
@Data

public class PlayerBoxArmsDtoUpd implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户开箱记录")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @ApiModelProperty(value = "状态  0.未出售1.已出售2.已取回")
    @TableField("is_status")
    private Integer isStatus;


}

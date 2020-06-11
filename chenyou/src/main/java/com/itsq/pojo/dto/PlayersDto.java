package com.itsq.pojo.dto;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-02
 */
@Data
public class PlayersDto implements Serializable {
    protected Integer pageIndex;

    protected Integer pageSize;

    private Integer  id;

    private String number;


    private String pwd;

    private Integer  isStatus;
    private String code;

    private BigDecimal  amount;

    private Integer usStatus;


    private Integer managrId;
}

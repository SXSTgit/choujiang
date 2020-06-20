package com.itsq.pojo.dto;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2020-06-11
 */
@Data
public class OperationRecordDto implements Serializable {
    /**
     * 当前页码
     */
    @ApiModelProperty("当前页码")
    private Integer pageIndex;
    /**
     * 分页长度
     */
    @ApiModelProperty("分页长度")
    private Integer pageSize;

    private String mangerName;


    private Integer mangerId;


    private Integer type;

    private String date;


}

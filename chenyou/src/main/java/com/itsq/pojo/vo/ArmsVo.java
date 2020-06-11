package com.itsq.pojo.vo;

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

public class ArmsVo implements Serializable {

    private Integer  id;

    private String name;


    private String type;


    private String imageUrl;


    private BigDecimal price;


    private String names;


    private String types;


    private String imageUrls;


    private BigDecimal prices;


    private Integer isStatus;




}

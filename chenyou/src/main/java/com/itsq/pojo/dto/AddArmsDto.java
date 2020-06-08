package com.itsq.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddArmsDto {

    private Integer id;
    private String name;
    private String type;
    private String imageUrl;
    private BigDecimal price;
    private Integer count;
    private Integer productId;
    private Integer isStatus;
    private Date creDate;

}

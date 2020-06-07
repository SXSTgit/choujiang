package com.itsq.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddBoxDto {

    private Integer id;
    private String name;
    private String image;
    private BigDecimal price;
    private Integer isStatus;
    private Integer count;
    private Date crDate;
    private Integer type;
    private Date outTime;
    private Integer days;

    private String glId;

}

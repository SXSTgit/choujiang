package com.itsq.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlayerBoxArmsDto {
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
    private String jilv;
    private Integer newArms;
    //private Integer oldArms;
    private Integer playerId;
    private String[] oldArms;
}

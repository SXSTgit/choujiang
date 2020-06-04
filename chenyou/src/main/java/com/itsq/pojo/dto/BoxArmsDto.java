package com.itsq.pojo.dto;

import com.itsq.pojo.entity.Arms;
import lombok.Data;

import java.util.Date;

@Data
public class BoxArmsDto {

    private Integer id;
    private Integer boxId;
    private Integer armsId;
    private Integer chance;
    private Integer count;
    private Integer isStatus;
    private Date crDate;
    private Arms arms;

}

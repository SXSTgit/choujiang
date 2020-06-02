package com.itsq.pojo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AddManagerReqDto {

    private String phone;
    private String userName;
    private Integer sex;
    private String address;
    private String identity;
    private String vxCode;

}

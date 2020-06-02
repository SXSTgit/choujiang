package com.itsq.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRespDto<T> {

    private T userInfo;

    private String token;

    private String tokenType;
}

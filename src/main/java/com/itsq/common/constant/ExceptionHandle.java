package com.itsq.common.constant;

import com.itsq.common.bean.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(value = APIException.class)
    public Response exceptionHandler(APIException e){
        log.warn("Response:{code:{},msg:{}}",e.getCode(),e.getMessage());
        return Response.fail(e.getCode(),e.getMessage());
    }
}

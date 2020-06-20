package com.itsq.common.bean;

import io.swagger.annotations.ApiModel;

/**
 * 响应对象
 *
 * @author 
 * @since 1.0.0
 */
@ApiModel("响应结果")
public class Response<T> {
    private static final long serialVersionUID = 7956490887069576517L;
    private int errorCode;
    private String message;
    private T body;
    private int total;
    private String exceptionDescription;

    public Response(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Response(int errorCode, String message, T body) {
        this.errorCode = errorCode;
        this.message = message;
        this.body = body;
    }

    public Response(int errorCode, String message, T body, int total) {
        this.errorCode = errorCode;
        this.message = message;
        this.body = body;
        this.total = total;
    }

    private Response(ErrorEnum resultMsg) {
        this.errorCode = resultMsg.getCode();
        this.message = resultMsg.getMessage();
    }

    public static <T> Response<T> success() {
        return new Response(ErrorEnum.SUCCESS.getCode(), ErrorEnum.SUCCESS.getMessage());
    }

    public static <T> Response<T> success(T body) {
        return new Response(ErrorEnum.SUCCESS.getCode(), ErrorEnum.SUCCESS.getMessage(), body);
    }

    public static <T> Response<T> success(T body, int total) {
        return new Response(ErrorEnum.SUCCESS.getCode(), ErrorEnum.SUCCESS.getMessage(), body, total);
    }

    public static <T> Response<T> fail() {
        return new Response(ErrorEnum.FAIL.getCode(), ErrorEnum.FAIL.getMessage());
    }

    public static <T> Response<T> fail(ErrorEnum resultMsg) {
        return new Response(resultMsg);
    }

    public static <T> Response<T> fail(Integer code, String message) {
        return new Response(code, message);
    }

    public static <T> Response<T> fail(String message) {
        return new Response(ErrorEnum.FAIL.getCode(), message);
    }

    public static <T> Response<T> fail(Exception e) {
        Response<T> response = new Response(ErrorEnum.ERROR_SERVER);
        response.setExceptionDescription(e.getMessage());
        return response;
    }

    public static <T> Response<T> build(Integer code, String message, T body) {
        return new Response(code, message, body);
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return this.body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getExceptionDescription() {
        return this.exceptionDescription;
    }

    public void setExceptionDescription(String exceptionDescription) {
        this.exceptionDescription = exceptionDescription;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String toString() {
        return "Response [errorCode=" + this.errorCode + ", message=" + this.message + ", body=" + this.body + ", exceptionDescription=" + this.exceptionDescription + "]";
    }
}

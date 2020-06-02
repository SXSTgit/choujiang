package com.itsq.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sq
 * @date 2020/4/15  14:10
 */
@Data
public class MemberPageDto {
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

    private String userName;

    private String phone;

    private Integer managerId;

    private Integer userId;
}

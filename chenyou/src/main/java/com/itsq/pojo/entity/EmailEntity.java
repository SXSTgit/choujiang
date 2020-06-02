package com.itsq.pojo.entity;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019-05-13 17:10:16
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_email")
@ApiModel(value="t_email对象", description="")
public class EmailEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date creDate;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updDate;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableId(value = "title")
    private String title;

    /**
     * 收件人
     */
    @ApiModelProperty(value = "收件人")
    @TableId(value = "to_emails")
    private String toEmails;

    /**
     * 发送状态 0未发送 1已发送
     */
    @ApiModelProperty(value = "发送状态 0未发送 1已发送")
    @TableId(value = "send_status")
    private Integer sendStatus;

    /**
     * 邮件内容
     */
    @ApiModelProperty(value = "邮件内容")
    @TableId(value = "content")
    private String content;

    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    @TableId(value = "fujian")
    private String fujian;
}

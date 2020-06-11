package com.itsq.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 史先帅
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("operation_record")
@ApiModel(value="OperationRecord对象", description="")
public class OperationRecord implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "操作人名字")
    @TableField("manger_name")
    private String mangerName;

    @ApiModelProperty(value = "操作人id")
    @TableField("manger_id")
    private Integer mangerId;

    @ApiModelProperty(value = "操作")
    @TableField("operation")
    private String operation;

    @ApiModelProperty(value = "内容")
    @TableField("concat")
    private String concat;

    @ApiModelProperty(value = "节点")
    @TableField("node")
    private String node;

    @ApiModelProperty(value = "0.管理员1.用户")
    @TableField("type")
    private Integer type;

    @TableField(value = "cre_date", fill = FieldFill.INSERT)
    private Date creDate;

    @TableField(exist = false)
    private String name;

    public static final String ID = "id";

    public static final String MANGER_NAME = "manger_name";

    public static final String MANGER_ID = "manger_id";

    public static final String OPERATION = "operation";

    public static final String CONCAT = "concat";
    public static final String NODE =   "node";
    public static final String TYPE = "type";

    public static final String CRE_DATE = "cre_date";


    public OperationRecord( Integer mangerId, String operation, String concat, String node, Integer type) {
        this.mangerId = mangerId;
        this.operation = operation;
        this.concat = concat;
        this.node = node;
        this.type = type;

    }
}

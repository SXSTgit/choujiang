package com.itsq.pojo.entity;

import java.math.BigDecimal;

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
 * @since 2020-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_order")
@ApiModel(value="UserOrder对象", description="")
public class UserOrder implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户表id")
    @TableField("manager_id")
    private Integer managerId;

    @ApiModelProperty(value = "会员名字")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "员工id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "0.充值  1.消费")
    @TableField("type")
    private Integer type;

    @TableField(value = "creattime",fill = FieldFill.INSERT)
    private Date creattime;

    @TableField("state")
    @TableLogic
    private Integer state;

    @ApiModelProperty(value = "消费前余额")
    @TableField("balance")
    private BigDecimal balance;

    @TableField("money")
    private Integer money;

    @ApiModelProperty(value = "会员id")
    @TableField("member_id")
    private Integer memberId;

    public static final String ID = "id";

    public static final String MANAGER_ID = "manager_id";

    public static final String NAME = "name";


    public static final String MEMBER_ID = "member_id";
    public static final String USER_ID = "user_id";

    public static final String TYPE = "type";

    public static final String CREATTIME = "creattime";

    public static final String STATE = "state";

    public static final String BALANCE = "balance";

    public static final String MONEY = "money";

    public UserOrder() {
    }

    public UserOrder(Integer managerId, Integer userId,String name, Integer memberId, Integer type,  BigDecimal balance, Integer money) {
        this.managerId = managerId;
        this.name = name;
        this.userId = userId;
        this.type = type;
        this.balance = balance;
        this.money = money;
        this.creattime=new Date();
    }
}

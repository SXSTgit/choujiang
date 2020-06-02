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
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("member")
@ApiModel(value="Member对象", description="")
public class Member implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "店铺用户id")
    @TableField("manager_id")
    private Integer managerId;

    @ApiModelProperty(value = "员工id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;


    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "余额")
    @TableField("amount")
    private BigDecimal amount;

    @TableField(value = "cre_date",fill = FieldFill.INSERT)
    private Date creDate;

    @TableField(value = "up_date",fill = FieldFill.UPDATE)
    private Date upDate;

    @TableField("state")
    @TableLogic
    private Integer state;

    @TableField(exist = false)
    private Integer money;

    @ApiModelProperty(value = "0.充值1.消费")
    @TableField(exist = false)
    private Integer status;

    public static final String ID = "id";

    public static final String MANAGER_ID = "manager_id";
    public static final String USER_ID = "user_id";
    public static final String NAME = "name";

    public static final String PHONE = "phone";

    public static final String AMOUNT = "amount";

    public static final String CRE_DATE = "cre_date";

    public static final String UP_DATE = "up_date";

    public static final String STATE = "state";


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreDate() {
        return creDate;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    public Date getUpDate() {
        return upDate;
    }

    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}

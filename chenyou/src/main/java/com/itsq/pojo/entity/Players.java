package com.itsq.pojo.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.itsq.pojo.vo.ArmsVo;
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
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("players")
@ApiModel(value="Players对象", description="")
public class Players implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "头像")
    @TableField("image")
    private String image;

    @ApiModelProperty(value = "邮箱")
    @TableField("number")
    private String number;

    @ApiModelProperty(value = "密码")
    @TableField("pwd")
    private String pwd;

    @ApiModelProperty(value = "steam交易url")
    @TableField("steam_url")
    private String steamUrl;

    @ApiModelProperty(value = "steamId")
    @TableField("steam_Id")
    private String steamId;

    @ApiModelProperty(value = "余额")
    @TableField("balance")
    private BigDecimal balance;

    @ApiModelProperty(value = "状态 0.正常1.冻结")
    @TableField("is_status")
    private Integer isStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "cre_date",fill = FieldFill.INSERT)
    private Date creDate;

    @TableField("state")
    @TableLogic
    private Integer state;


    @TableField(exist = false)
    private List<Arms> armsList;
    @TableField(exist = false)
    private List<ArmsVo> armsLists;
    @TableField(exist = false)
    private Integer  totalPages;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String IMAGE = "image";

    public static final String NUMBER = "number";

    public static final String PWD = "pwd";

    public static final String STEAM_URL = "steam_url";

    public static final String BALANCE = "balance";

    public static final String IS_STATUS = "is_status";

    public static final String CRE_DATA = "cre_data";

    public static final String STATE = "state";

    public static final String STEAM_ID = "steam_id";

}

package com.itsq.common.base.orm;


import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class Operator {
	private Integer id;
	private String userName;
	private String phone;
}

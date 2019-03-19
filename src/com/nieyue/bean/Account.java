package com.nieyue.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 账户
 */
@ApiModel(value="账户",description="账户")
@Data
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 账户id
	 */
	@ApiModelProperty(value="账户id")
	private Integer accountId;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value="姓名")
	private String realname;
	/**
	 * 学号
	 */
	@ApiModelProperty(value="学号")
	private String sid;
	/**
	 * 性别,默认为0未知，为1男性，为2女性
	 */
	@ApiModelProperty(value="性别,默认为0未知，为1男性，为2女性")
	private Integer sex;
	/**
	 * 民族
	 */
	@ApiModelProperty(value="民族")
	private String nation;
	/**
	 * 身份证
	 */
	@ApiModelProperty(value="身份证")
	private String identityCards;
	/**
	 * 联系电话
	 */
	@ApiModelProperty(value="联系电话")
	private String phone;
	/**
	 * 密码
	 */
	@ApiModelProperty(value="密码")
	private String password;

	/**
	 * 头像
	 */
	@ApiModelProperty(value="头像")
	private String icon;

	/**
	 * 出生年月日
	 */
	@ApiModelProperty(value="出生年月日")
	private Date birthDate;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value="创建时间")
	private Date createDate;
	/**
	 * 登陆时间
	 */
	@ApiModelProperty(value="登陆时间")
	private Date loginDate;
	/**
	 * 状态 0正常，1封禁，2异常
	 */
	@ApiModelProperty(value="状态 0正常，1封禁，2异常")
	private Integer status;
	/**
	 * 角色id外键
	 */
	@ApiModelProperty(value="角色id外键")
	private Integer roleId;
	/**
	 * 角色
	 */
	@ApiModelProperty(value="角色")
	private Role role;
	
	}

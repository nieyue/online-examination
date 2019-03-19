package com.nieyue.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 *
 */
@ApiModel(value="角色",description="角色")
@Data
public class Role implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 角色id
     */
    @ApiModelProperty(value="角色id")
    private Integer roleId;

    /**
     * 角色名
     */
    @ApiModelProperty(value="角色名")
    private String name;
    /**
     * 角色职责
     */
    @ApiModelProperty(value="角色职责")
    private String duty;
    /**
     * 角色更新时间
     */
    @ApiModelProperty(value="角色更新时间")
    private Date updateDate;

}




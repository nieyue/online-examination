package com.nieyue.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 阅读理解
 *
 */
@ApiModel(value="阅读理解",description="阅读理解")
@Data
public class Reading implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 阅读理解id
     */
    @ApiModelProperty(value="阅读理解id")
    private Integer readingId;
    /**
     * 内容
     */
    @ApiModelProperty(value="内容")
    private String content;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createDate;
    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date updateDate;

}




package com.nieyue.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 考试成绩
 *
 */
@ApiModel(value="考试成绩",description="考试成绩")
@Data
public class Result implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 考试成绩id
     */
    @ApiModelProperty(value="考试成绩id")
    private Integer resultId;

    /**
     * 分数
     */
    @ApiModelProperty(value="分数")
    private Double score;
    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private Date startDate;
    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private Date endDate;
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
    /**
     * 账户id外键
     */
    @ApiModelProperty(value="账户id外键")
    private Integer accountId;

}




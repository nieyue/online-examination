package com.nieyue.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 阅读理解成绩
 *
 */
@ApiModel(value="阅读理解成绩",description="阅读理解成绩")
@Data
public class ResultReading implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 阅读理解成绩id
     */
    @ApiModelProperty(value="阅读理解成绩id")
    private Integer resultReadingId;
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
    /**
     * 考试成绩id
     */
    @ApiModelProperty(value="考试成绩id")
    private Integer resultId;
    /**
     * 选择题成绩列表
     */
    @ApiModelProperty(value="选择题成绩列表")
    private List<ResultChoice> ResultChoiceList;

}




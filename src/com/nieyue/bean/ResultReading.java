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
//@Data
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getResultReadingId() {
        return resultReadingId;
    }

    public void setResultReadingId(Integer resultReadingId) {
        this.resultReadingId = resultReadingId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public List<ResultChoice> getResultChoiceList() {
        return ResultChoiceList;
    }

    public void setResultChoiceList(List<ResultChoice> resultChoiceList) {
        ResultChoiceList = resultChoiceList;
    }
}




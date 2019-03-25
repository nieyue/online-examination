package com.nieyue.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 考试成绩
 *
 */
@ApiModel(value="考试成绩",description="考试成绩")
//@Data
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
     * 状态，默认1考试中，2完成
     */
    @ApiModelProperty(value="状态，默认1考试中，2完成")
    private Integer status;
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
    /**
     * 选择题成绩列表
     */
    @ApiModelProperty(value="选择题成绩列表")
    private List<ResultChoice> resultChoiceList;
    /**
     * 阅读理解成绩列表
     */
    @ApiModelProperty(value="阅读理解成绩列表")
    private List<ResultReading> resultReadingList;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public List<ResultChoice> getResultChoiceList() {
        return resultChoiceList;
    }

    public void setResultChoiceList(List<ResultChoice> resultChoiceList) {
        this.resultChoiceList = resultChoiceList;
    }

    public List<ResultReading> getResultReadingList() {
        return resultReadingList;
    }

    public void setResultReadingList(List<ResultReading> resultReadingList) {
        this.resultReadingList = resultReadingList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}




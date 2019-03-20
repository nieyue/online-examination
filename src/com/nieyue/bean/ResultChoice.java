package com.nieyue.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 选择题成绩
 *
 */
@ApiModel(value="选择题成绩",description="选择题成绩")
//@Data
public class ResultChoice implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 选择题成绩id
     */
    @ApiModelProperty(value="选择题成绩id")
    private Integer resultChoiceId;

    /**
     * 分数
     */
    @ApiModelProperty(value="分数")
    private Double score;
    /**
     * 问题
     */
    @ApiModelProperty(value="问题")
    private String question;
    /**
     * a
     */
    @ApiModelProperty(value="a")
    private String a;
    /**
     * b
     */
    @ApiModelProperty(value="b")
    private String b;
    /**
     * c
     */
    @ApiModelProperty(value="c")
    private String c;
    /**
     * d
     */
    @ApiModelProperty(value="d")
    private String d;
    /**
     * 正确，a,b,c,d分别对应
     */
    @ApiModelProperty(value="正确的，1,2,3,4分别对应a,b,c,d")
    private Integer correct;
    /**
     * 正确，a,b,c,d分别对应
     */
    @ApiModelProperty(value="选择的，1,2,3,4分别对应a,b,c,d")
    private Integer target;
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
     * 阅读理解成绩id外键
     */
    @ApiModelProperty(value="阅读理解成绩id外键")
    private Integer resultReadingId;
    /**
     * 考试成绩id外键
     */
    @ApiModelProperty(value="考试成绩id外键")
    private Integer resultId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getResultChoiceId() {
        return resultChoiceId;
    }

    public void setResultChoiceId(Integer resultChoiceId) {
        this.resultChoiceId = resultChoiceId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
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

    public Integer getResultReadingId() {
        return resultReadingId;
    }

    public void setResultReadingId(Integer resultReadingId) {
        this.resultReadingId = resultReadingId;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }
}




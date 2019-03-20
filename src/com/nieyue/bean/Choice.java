package com.nieyue.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 选择题
 *
 */
@ApiModel(value="选择题",description="选择题")
//@Data
public class Choice implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 选择题id
     */
    @ApiModelProperty(value="选择题id")
    private Integer choiceId;

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
     * 阅读理解id
     */
    @ApiModelProperty(value="阅读理解id")
    private Integer readingId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
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

    public Integer getReadingId() {
        return readingId;
    }

    public void setReadingId(Integer readingId) {
        this.readingId = readingId;
    }
}




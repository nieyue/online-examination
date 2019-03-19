package com.nieyue.dao;

import com.nieyue.bean.Choice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 选择题数据库接口
 * @author yy
 *
 */
@Mapper
public interface ChoiceDao {
    /** 新增*/
    public boolean add(Choice choice) ;
    /** 删除 */
    public boolean delete(Integer choiceId) ;
    /** 更新*/
    public boolean update(Choice choice);
    /** 装载 */
    public Choice load(Integer choiceId);

    /** 数目 */
    public int count(
                @Param("readingId") Integer readingId,
                @Param("correct") Integer correct
    );
    /** 列表 */
    public List<Choice> list(
            @Param("readingId") Integer readingId,
            @Param("correct") Integer correct,
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("orderName") String orderName,
            @Param("orderWay") String orderWay) ;
}
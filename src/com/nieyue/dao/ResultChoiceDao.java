package com.nieyue.dao;

import com.nieyue.bean.ResultChoice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 选择题成绩数据库接口
 * @author yy
 *
 */
@Mapper
public interface ResultChoiceDao {
    /** 新增*/
    public boolean add(ResultChoice resultChoice) ;
    /** 删除 */
    public boolean delete(Integer resultChoiceId) ;
    /** 更新*/
    public boolean update(ResultChoice resultChoice);
    /** 装载 */
    public ResultChoice load(Integer resultChoiceId);

    /** 数目 */
    public int count(
            @Param("resultReadingId") Integer resultReadingId,
            @Param("resultId") Integer resultId
    );
    /** 列表 */
    public List<ResultChoice> list(
            @Param("resultReadingId") Integer resultReadingId,
            @Param("resultId") Integer resultId,
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("orderName") String orderName,
            @Param("orderWay") String orderWay) ;
}
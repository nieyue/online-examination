package com.nieyue.dao;

import com.nieyue.bean.ResultReading;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 阅读理解成绩数据库接口
 * @author yy
 *
 */
@Mapper
public interface ResultReadingDao {
    /** 新增*/
    public boolean add(ResultReading resultReading) ;
    /** 删除 */
    public boolean delete(Integer resultReadingId) ;
    /** 更新*/
    public boolean update(ResultReading resultReading);
    /** 装载 */
    public ResultReading load(Integer resultReadingId);

    /** 数目 */
    public int count(
            @Param("resultId") Integer resultId
    );
    /** 列表 */
    public List<ResultReading> list(
            @Param("resultId") Integer resultId,
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("orderName") String orderName,
            @Param("orderWay") String orderWay) ;
}
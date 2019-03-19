package com.nieyue.dao;

import com.nieyue.bean.Reading;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 阅读理解数据库接口
 * @author yy
 *
 */
@Mapper
public interface ReadingDao {
    /** 新增*/
    public boolean add(Reading reading) ;
    /** 删除 */
    public boolean delete(Integer readingId) ;
    /** 更新*/
    public boolean update(Reading reading);
    /** 装载 */
    public Reading load(Integer readingId);

    /** 数目 */
    public int count(
    );
    /** 列表 */
    public List<Reading> list(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("orderName") String orderName,
            @Param("orderWay") String orderWay) ;
}
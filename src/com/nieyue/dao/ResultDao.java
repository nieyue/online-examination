package com.nieyue.dao;

import com.nieyue.bean.Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 考试成绩数据库接口
 * @author yy
 *
 */
@Mapper
public interface ResultDao {
    /** 新增*/
    public boolean add(Result result) ;
    /** 删除 */
    public boolean delete(Integer resultId) ;
    /** 更新*/
    public boolean update(Result result);
    /** 装载 */
    public Result load(Integer resultId);

    /** 数目 */
    public int count(
            @Param("accountId") Integer accountId,
            @Param("endDate") Date endDate
    );
    /** 列表 */
    public List<Result> list(
            @Param("accountId") Integer accountId,
            @Param("endDate") Date endDate,
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("orderName") String orderName,
            @Param("orderWay") String orderWay) ;
}
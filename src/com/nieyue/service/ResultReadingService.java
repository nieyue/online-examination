package com.nieyue.service;

import com.nieyue.bean.ResultReading;

import java.util.List;

/**
 * 阅读理解成绩逻辑层接口
 * @author yy
 *
 */
public interface ResultReadingService {
	/** 新增 */
	public boolean add(ResultReading resultReading) ;
	/** 删除 */
	public boolean delete(Integer resultReadingId) ;
	/** 更新*/
	public boolean update(ResultReading resultReading);
	/** 装载 */
	public ResultReading load(Integer resultReadingId);
	/** 数目 */
	public int count(
			Integer resultId
    );
	/** 列表 */
	public List<ResultReading> list(
			Integer resultId,
            int pageNum,
            int pageSize,
            String orderName,
            String orderWay) ;
}

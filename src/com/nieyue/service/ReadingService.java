package com.nieyue.service;

import com.nieyue.bean.Reading;

import java.util.List;

/**
 * 阅读理解逻辑层接口
 * @author yy
 *
 */
public interface ReadingService {
	/** 新增 */
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
            int pageNum,
            int pageSize,
            String orderName,
            String orderWay) ;
}

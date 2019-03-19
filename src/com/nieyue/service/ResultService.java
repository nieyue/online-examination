package com.nieyue.service;

import com.nieyue.bean.Result;

import java.util.Date;
import java.util.List;

/**
 * 考试成绩逻辑层接口
 * @author yy
 *
 */
public interface ResultService {
	/** 新增 */
	public boolean add(Result result) ;
	/** 删除 */
	public boolean delete(Integer resultId) ;
	/** 更新*/
	public boolean update(Result result);
	/** 装载 */
	public Result load(Integer resultId);
	/** 数目 */
	public int count(
            Integer accountId,
            Date endDate
    );
	/** 列表 */
	public List<Result> list(
            Integer accountId,
            Date endDate,
            int pageNum,
            int pageSize,
            String orderName,
            String orderWay) ;
}

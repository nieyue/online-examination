package com.nieyue.service;

import com.nieyue.bean.ResultChoice;

import java.util.List;

/**
 * 选择题成绩逻辑层接口
 * @author yy
 *
 */
public interface ResultChoiceService {
	/** 新增 */
	public boolean add(ResultChoice resultChoice) ;
	/** 删除 */
	public boolean delete(Integer resultChoiceId) ;
	/** 更新*/
	public boolean update(ResultChoice resultChoice);
	/** 装载 */
	public ResultChoice load(Integer resultChoiceId);
	/** 数目 */
	public int count(
            Integer resultReadingId,
            Integer resultId
    );
	/** 列表 */
	public List<ResultChoice> list(
            Integer resultReadingId,
            Integer resultId,
            int pageNum,
            int pageSize,
            String orderName,
            String orderWay) ;
}

package com.nieyue.service;

import com.nieyue.bean.Choice;

import java.util.List;

/**
 * 选择题逻辑层接口
 * @author yy
 *
 */
public interface ChoiceService {
	/** 新增 */
	public boolean add(Choice choice) ;
	/** 删除 */
	public boolean delete(Integer choiceId) ;
	/** 更新*/
	public boolean update(Choice choice);
	/** 装载 */
	public Choice load(Integer choiceId);
	/** 数目 */
	public int count(
			 Integer readingId,
			Integer correct
	);
	/** 列表 */
	public List<Choice> list(
			Integer readingId,
			Integer correct,
            int pageNum,
            int pageSize,
            String orderName,
            String orderWay) ;
}

package com.nieyue.service.impl;

import com.nieyue.bean.ResultChoice;
import com.nieyue.dao.ResultChoiceDao;
import com.nieyue.service.ResultChoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ResultChoiceServiceImpl implements ResultChoiceService{
	@Resource
	ResultChoiceDao resultChoiceDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean add(ResultChoice resultChoice) {
		resultChoice.setCreateDate(new Date());
		resultChoice.setUpdateDate(new Date());
		boolean b = resultChoiceDao.add(resultChoice);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delete(Integer resultChoiceId) {
		boolean b = resultChoiceDao.delete(resultChoiceId);
		return b;
	}
	@Transactional(propagation= Propagation.REQUIRED)
	@Override
	public boolean update(ResultChoice resultChoice) {
		resultChoice.setUpdateDate(new Date());
		boolean b = resultChoiceDao.update(resultChoice);
		return b;
	}

	@Override
	public ResultChoice load(Integer resultChoiceId) {
		ResultChoice r = resultChoiceDao.load(resultChoiceId);
		return r;
	}

	@Override
	public int count(Integer resultReadingId,Integer resultId) {
		int c = resultChoiceDao.count( resultReadingId,resultId);
		return c;
	}

	@Override
	public List<ResultChoice> list(Integer resultReadingId,Integer resultId,
							int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<ResultChoice> l = resultChoiceDao.list( resultReadingId,resultId,
				 pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}

package com.nieyue.service.impl;

import com.nieyue.bean.Result;
import com.nieyue.dao.ResultDao;
import com.nieyue.service.ResultService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
	@Resource
	ResultDao resultDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean add(Result result) {
		result.setCreateDate(new Date());
		result.setUpdateDate(new Date());
		boolean b = resultDao.add(result);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delete(Integer resultId) {
		boolean b = resultDao.delete(resultId);
		return b;
	}
	@Transactional(propagation= Propagation.REQUIRED)
	@Override
	public boolean update(Result result) {
		result.setUpdateDate(new Date());
		boolean b = resultDao.update(result);
		return b;
	}

	@Override
	public Result load(Integer resultId) {
		Result r = resultDao.load(resultId);
		return r;
	}

	@Override
	public int count(Integer accountId,Date endDate) {
		int c = resultDao.count( accountId,endDate);
		return c;
	}

	@Override
	public List<Result> list(Integer accountId,Date endDate,int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<Result> l = resultDao.list( accountId,endDate,
				 pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}

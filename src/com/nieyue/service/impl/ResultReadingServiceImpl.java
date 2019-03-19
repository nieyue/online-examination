package com.nieyue.service.impl;

import com.nieyue.bean.ResultReading;
import com.nieyue.dao.ResultReadingDao;
import com.nieyue.service.ResultReadingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ResultReadingServiceImpl implements ResultReadingService{
	@Resource
	ResultReadingDao resultReadingDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean add(ResultReading resultReading) {
		resultReading.setCreateDate(new Date());
		resultReading.setUpdateDate(new Date());
		boolean b = resultReadingDao.add(resultReading);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delete(Integer resultReadingId) {
		boolean b = resultReadingDao.delete(resultReadingId);
		return b;
	}
	@Transactional(propagation= Propagation.REQUIRED)
	@Override
	public boolean update(ResultReading resultReading) {
		resultReading.setUpdateDate(new Date());
		boolean b = resultReadingDao.update(resultReading);
		return b;
	}

	@Override
	public ResultReading load(Integer resultReadingId) {
		ResultReading r = resultReadingDao.load(resultReadingId);
		return r;
	}

	@Override
	public int count(Integer resultId) {
		int c = resultReadingDao.count( resultId );
		return c;
	}

	@Override
	public List<ResultReading> list(Integer resultId,int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<ResultReading> l = resultReadingDao.list( resultId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}

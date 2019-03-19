package com.nieyue.service.impl;

import com.nieyue.bean.Reading;
import com.nieyue.dao.ReadingDao;
import com.nieyue.service.ReadingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ReadingServiceImpl implements ReadingService{
	@Resource
	ReadingDao readingDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean add(Reading reading) {
		reading.setCreateDate(new Date());
		reading.setUpdateDate(new Date());
		boolean b = readingDao.add(reading);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delete(Integer readingId) {
		boolean b = readingDao.delete(readingId);
		return b;
	}
	@Transactional(propagation= Propagation.REQUIRED)
	@Override
	public boolean update(Reading reading) {
		reading.setUpdateDate(new Date());
		boolean b = readingDao.update(reading);
		return b;
	}

	@Override
	public Reading load(Integer readingId) {
		Reading r = readingDao.load(readingId);
		return r;
	}

	@Override
	public int count() {
		int c = readingDao.count( );
		return c;
	}

	@Override
	public List<Reading> list(int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<Reading> l = readingDao.list( pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}

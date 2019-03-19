package com.nieyue.service.impl;

import com.nieyue.bean.Choice;
import com.nieyue.dao.ChoiceDao;
import com.nieyue.service.ChoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ChoiceServiceImpl implements ChoiceService{
	@Resource
	ChoiceDao choiceDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean add(Choice choice) {
		choice.setCreateDate(new Date());
		choice.setUpdateDate(new Date());
		boolean b = choiceDao.add(choice);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delete(Integer choiceId) {
		boolean b = choiceDao.delete(choiceId);
		return b;
	}
	@Transactional(propagation= Propagation.REQUIRED)
	@Override
	public boolean update(Choice choice) {
		choice.setUpdateDate(new Date());
		boolean b = choiceDao.update(choice);
		return b;
	}

	@Override
	public Choice load(Integer choiceId) {
		Choice r = choiceDao.load(choiceId);
		return r;
	}

	@Override
	public int count(Integer readingId,
					 Integer correct) {
		int c = choiceDao.count( readingId,
				 correct);
		return c;
	}

	@Override
	public List<Choice> list(Integer readingId,
							 Integer correct,int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<Choice> l = choiceDao.list( readingId,
				 correct,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}

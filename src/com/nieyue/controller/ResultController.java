package com.nieyue.controller;

import com.nieyue.bean.Choice;
import com.nieyue.bean.Result;
import com.nieyue.exception.NotAnymoreException;
import com.nieyue.exception.NotIsNotExistException;
import com.nieyue.service.ChoiceService;
import com.nieyue.service.ReadingService;
import com.nieyue.service.ResultService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResultList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 考试成绩控制类
 * @author yy
 *
 */
@Api(tags={"result"},value="考试成绩",description="考试成绩管理")
@RestController
@RequestMapping("/result")
public class ResultController {
	@Autowired
	private ResultService resultService;
	@Autowired
	private ChoiceService choiceService;
	@Autowired
	private ReadingService readingService;

	/**
	 * 考试成绩分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "考试成绩列表", notes = "考试成绩分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="accountId",value="账户id外键",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="endDate",value="小于结束时间",dataType="datetime", paramType = "query"),
			@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
			@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="update_date"),
			@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	})
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Result>> list(
			@RequestParam(value="accountId",required=false)Integer accountId,
			@RequestParam(value="endDate",required=false) Date endDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="update_date") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		List<Result> list = new ArrayList<Result>();
		list= resultService.list(accountId,endDate,pageNum, pageSize, orderName, orderWay);
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			throw new NotAnymoreException();//没有更多
		}
	}
	/**
	 * 考试成绩修改
	 * @return
	 */
	@ApiOperation(value = "考试成绩修改", notes = "考试成绩修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Result>> update(@ModelAttribute Result result,HttpSession session)  {
		boolean um = resultService.update(result);
		if(um){
			List<Result> list = new ArrayList<>();
			list.add(result);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 考试
	 * @return 
	 */
	@ApiOperation(value = "考试", notes = "考试")
	@RequestMapping(value = "/start", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Result>> start(@ModelAttribute Result result, HttpSession session) {
		int count = resultService.count(result.getAccountId(), result.getEndDate());
		if(count>0){
		//如果小于结束时间还存在有，便是在考试，不能新增
			return ResultUtil.getSlefSRFailList(null);
		}
		result.setScore(0.0);
		result.setStartDate(new Date());
		//60分钟
		result.setEndDate(new Date(new Date().getTime()+60*60*1000));
		boolean am = resultService.add(result);
		//获取选择题，10个
		List<Choice> choiceList = choiceService.list(0, null, 1, 10, "choice_id", "asc");

		if(am){
			List<Result> list = new ArrayList<>();
			list.add(result);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 考试成绩增加
	 * @return
	 */
	@ApiOperation(value = "考试成绩增加", notes = "考试成绩增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Result>> add(@ModelAttribute Result result, HttpSession session) {
		boolean am = resultService.add(result);
		if(am){
			List<Result> list = new ArrayList<>();
			list.add(result);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 考试成绩删除
	 * @return
	 */
	@ApiOperation(value = "考试成绩删除", notes = "考试成绩删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="resultId",value="考试成绩ID",dataType="int", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Result>> delete(@RequestParam("resultId") Integer resultId,HttpSession session)  {
		Result result = resultService.load(resultId);
		boolean dm = resultService.delete(resultId);
		if(dm){
			List<Result> list = new ArrayList<>();
			list.add(result);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 考试成绩浏览数量
	 * @return
	 */
	@ApiOperation(value = "考试成绩数量", notes = "考试成绩数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="accountId",value="账户id外键",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="endDate",value="小于结束时间",dataType="datetime", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="accountId",required=false)Integer accountId,
			@RequestParam(value="endDate",required=false)Date endDate,
			HttpSession sessiorn)  {
		Integer count = resultService.count(accountId,endDate);
			List<Integer> list = new ArrayList<Integer>();
			list.add(count);
			return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 考试成绩单个加载
	 * @return
	 */
	@ApiOperation(value = "考试成绩单个加载", notes = "考试成绩单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="resultId",value="考试成绩ID",dataType="int", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Result>> load(@RequestParam("resultId") Integer resultId,HttpSession session)  {
		List<Result> list = new ArrayList<>();
		Result result = resultService.load(resultId);
			if(result!=null &&!result.equals("")){
				list.add(result);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				throw new NotIsNotExistException("考试成绩");//不存在
			}
	}
	
}

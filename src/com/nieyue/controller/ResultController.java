package com.nieyue.controller;

import com.nieyue.bean.*;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.exception.NotAnymoreException;
import com.nieyue.exception.NotIsNotExistException;
import com.nieyue.service.*;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResultList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


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
	@Autowired
	private ResultChoiceService resultChoiceService;
	@Autowired
	private ResultReadingService resultReadingService;

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
			for (int i = 0; i < list.size(); i++) {
				Result result = list.get(i);
				List<ResultChoice> resultChoiceList = resultChoiceService.list(0, result.getResultId(), 1, Integer.MAX_VALUE, "result_choice_id", "asc");
				result.setResultChoiceList(resultChoiceList);
				List<ResultReading> resultReadingList = resultReadingService.list(result.getResultId(), 1, Integer.MAX_VALUE, "result_reading_id", "asc");
				for (int j = 0; j < resultReadingList.size(); j++) {
					ResultReading resultReading = resultReadingList.get(j);
					List<ResultChoice> rcl= resultChoiceService.list(resultReading.getResultReadingId(), result.getResultId(), 1, Integer.MAX_VALUE, "result_choice_id", "asc");
					resultReading.setResultChoiceList(rcl);
				}
				result.setResultReadingList(resultReadingList);
			}
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
	 * 考试提交
	 * @return
	 */
	@ApiOperation(value = "考试提交", notes = "考试提交")
	@RequestMapping(value = "/submit", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Result>> submit(
			@RequestParam(value="accountId",required=false)Integer accountId,
			@RequestParam(value="resultId",required=false)Integer resultId,
			@RequestParam(value="selectResultChoice",required=false) String selectResultChoice
			,HttpSession session)  {
		Result result = resultService.load(resultId);
		if(result==null){
			throw new CommonRollbackException("考试不存在");
		}
		if(result.getScore()>0){
			throw new CommonRollbackException("已经考过");
		}
		Double total=0.0;
		JSONArray jsonarray = JSONArray.fromObject(selectResultChoice);
		for (int i = 0; i < jsonarray.size(); i++) {
			JSONObject json = jsonarray.getJSONObject(i);
			Integer resultChoiceId = json.getInt("resultChoiceId");
			Integer target = json.getInt("target");
			ResultChoice resultChoice = resultChoiceService.load(resultChoiceId);
			if(resultChoice.getCorrect().equals(target)){
				total+=resultChoice.getScore();
			}
			resultChoice.setTarget(target);
			resultChoiceService.update(resultChoice);
		}
			result.setScore(total);
		result.setEndDate(new Date());
		result.setUpdateDate(new Date());
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
		int count = resultService.count(result.getAccountId(), new Date());
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
		int choiceNumber = choiceService.count(0, null);
		int startChoiceNumber=1;
		int mastChoiceNumber=10;
		if(choiceNumber>mastChoiceNumber){
			startChoiceNumber=new Random().nextInt((choiceNumber-mastChoiceNumber));
		}
		List<Choice> choiceList = choiceService.list(0, null, startChoiceNumber, mastChoiceNumber, "choice_id", "asc");
		//添加选择题
		for (int i = 0; i <choiceList.size() ; i++) {
			Choice choice = choiceList.get(i);
			ResultChoice resultChoice=new ResultChoice();
			resultChoice.setA(choice.getA());
			resultChoice.setB(choice.getB());
			resultChoice.setC(choice.getC());
			resultChoice.setD(choice.getD());
			resultChoice.setScore(choice.getScore());
			resultChoice.setCorrect(choice.getCorrect());
			resultChoice.setQuestion(choice.getQuestion());
			resultChoice.setResultId(result.getResultId());
			resultChoice.setResultReadingId(0);//0代表不是阅读理解
			resultChoiceService.add(resultChoice);
		}

		//获取2个阅读理解，每题5个
		int readingNumber = readingService.count();
		int startReadingNumber=1;
		int mastReadingNumber=2;
		if(readingNumber>mastReadingNumber){
			startReadingNumber=new Random().nextInt((readingNumber-mastReadingNumber));
		}
		//添加阅读理解
		List<Reading> readingList = readingService.list(startReadingNumber, mastReadingNumber, "reading_id", "asc");
		for (int i = 0; i <readingList.size() ; i++) {
			Reading reading = readingList.get(i);
			ResultReading resultReading=new ResultReading();
			resultReading.setContent(reading.getContent());
			resultReading.setResultId(result.getResultId());
			resultReadingService.add(resultReading);
			//添加阅读理解的选择题
			List<Choice> cl = choiceService.list(reading.getReadingId(), null, 1, Integer.MAX_VALUE, "choice_id", "asc");
			for (int j = 0; j < cl.size(); j++) {
				Choice c = cl.get(j);
				ResultChoice resultChoice=new ResultChoice();
				resultChoice.setA(c.getA());
				resultChoice.setB(c.getB());
				resultChoice.setC(c.getC());
				resultChoice.setD(c.getD());
				resultChoice.setCorrect(c.getCorrect());
				resultChoice.setScore(c.getScore());
				resultChoice.setQuestion(c.getQuestion());
				resultChoice.setResultId(result.getResultId());
				resultChoice.setResultReadingId(resultReading.getResultReadingId());//是阅读理解
				resultChoiceService.add(resultChoice);
			}
		}
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
				List<ResultChoice> resultChoiceList = resultChoiceService.list(0, result.getResultId(), 1, Integer.MAX_VALUE, "result_choice_id", "asc");
				result.setResultChoiceList(resultChoiceList);
				List<ResultReading> resultReadingList = resultReadingService.list(result.getResultId(), 1, Integer.MAX_VALUE, "result_reading_id", "asc");
				for (int j = 0; j < resultReadingList.size(); j++) {
					ResultReading resultReading = resultReadingList.get(j);
					List<ResultChoice> rcl= resultChoiceService.list(resultReading.getResultReadingId(), result.getResultId(), 1, Integer.MAX_VALUE, "result_choice_id", "asc");
					resultReading.setResultChoiceList(rcl);
				}
				result.setResultReadingList(resultReadingList);
				list.add(result);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				throw new NotIsNotExistException("考试成绩");//不存在
			}
	}
	/**
	 * 导出Excel
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequestMapping(value = "/exportExcel", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList<List<List<List<Object>>>> exportExcel(
			@RequestParam("accountId") Integer accountId,
			@RequestParam("resultId") Integer resultId,
			HttpSession	 session
	) throws IllegalStateException, IOException {
		/*String name="";
		name="D:/nieyue"+"/uploaderPath/img/"+ SnowflakeIdWorker.getId().toString()+multipartFile.getOriginalFilename();
		File file = new File(name);
		multipartFile.transferTo(file);
		List<List<List<Object>>> lll = MyExcel.importData(file);
		for (int i = 0; i < lll.size(); i++) {
			List<List<Object>> ll = lll.get(i);
			//数据从1行开始，0是列名
			for (int j = 1; j < lll.size(); j++) {
				List<Object> l = ll.get(j);
				Account account = new Account();
				for (int z = 0; z < l.size(); z++) {
					//account.set
				}

			}
		}*/
		//MyExcel.exportData("考试"+resultId,list,"D://home","/",null);
		Result result = resultService.load(resultId);
		ArrayList<String> listname = new ArrayList<>();
		listname.add("考试成绩"+result.getResultId());

		return ResultUtil.getSlefSRSuccessList(null);
	}
}

package com.nieyue.controller;

import com.nieyue.bean.*;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.exception.NotAnymoreException;
import com.nieyue.exception.NotIsNotExistException;
import com.nieyue.service.*;
import com.nieyue.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
			@ApiImplicitParam(name="status",value="状态，默认1考试中，2完成",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="endDate",value="小于结束时间",dataType="datetime", paramType = "query"),
			@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
			@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="update_date"),
			@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	})
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Result>> list(
			@RequestParam(value="accountId",required=false)Integer accountId,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="endDate",required=false) Date endDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="update_date") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		List<Result> list = new ArrayList<Result>();
		list= resultService.list(accountId,status,endDate,pageNum, pageSize, orderName, orderWay);
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
		if(result.getStatus()==2){
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
		Date newdate = new Date();
		result.setEndDate(newdate);
		result.setUpdateDate(newdate);
		result.setStatus(2);
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
		int count = resultService.count(result.getAccountId(),1, new Date());
		if(count>0){
			throw new CommonRollbackException("已经有考试在进行");
		}
		result.setScore(0.0);
		Date newdate = new Date();
		result.setStartDate(newdate);
		result.setStatus(1);
		//60分钟
		result.setEndDate(new Date(newdate.getTime()+60*60*1000));
		boolean am = resultService.add(result);
		//获取选择题，10个
		int choiceNumber = choiceService.count(0, null);
		List<Integer> list=new ArrayList<>();
		for (int i = 0; i < choiceNumber; i++) {
			list.add(i+1);
		}
		List<Integer> array = new NumberUtil<Integer>().getRandomArrayElements(list, 10);
		List<Choice> choiceList=new ArrayList<>();
		for (int i = 0; i < array.size(); i++) {
			choiceList.addAll(choiceService.list(0, null, array.get(i),1, "choice_id", "asc"));
		}
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
		List<Integer> list2=new ArrayList<>();
		for (int i = 0; i < readingNumber; i++) {
			list2.add(i+1);
		}
		List<Integer> array2 = new NumberUtil<Integer>().getRandomArrayElements(list2, 2);
		List<Reading> readingList=new ArrayList<>();
		for (int i = 0; i < array2.size(); i++) {
			readingList.addAll(readingService.list( array2.get(i),1, "reading_id", "asc"));
		}
		//添加阅读理解
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
			List<Result> l = new ArrayList<>();
			l.add(result);
			return ResultUtil.getSlefSRSuccessList(l);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 考试重修
	 * @return
	 */
	@ApiOperation(value = "考试重修", notes = "考试重修")
	@RequestMapping(value = "/reset", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Result>> reset(
			@RequestParam(value="accountId",required=false)Integer accountId,
			@RequestParam(value="resultId",required=false)Integer resultId
			,HttpSession session)  {
		int count = resultService.count(accountId,1, new Date());
		if(count>0){
			//如果小于结束时间还存在有，便是在考试，不能新增
			throw new CommonRollbackException("已经有考试在进行");
		}

		Result result = resultService.load(resultId);

		if(result==null){
			throw new CommonRollbackException("考试不存在");
		}
		result.setScore(0.0);
		Date newdate = new Date();
		result.setStartDate(newdate);
		//60分钟
		result.setEndDate(new Date(newdate.getTime()+60*60*1000));
		result.setUpdateDate(newdate);
		result.setStatus(1);
		boolean um = resultService.update(result);
		if(um){
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
			@ApiImplicitParam(name="status",value="状态，默认1考试中，2完成",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="endDate",value="小于结束时间",dataType="datetime", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="accountId",required=false)Integer accountId,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="endDate",required=false)Date endDate,
			HttpSession sessiorn)  {
		Integer count = resultService.count(accountId,status,endDate);
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
	public String exportExcel(
			@RequestParam("accountId") Integer accountId,
			@RequestParam("resultId") Integer resultId,
			HttpServletRequest request,
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
		List<Result> list = new ArrayList<>();
		Result result = resultService.load(resultId);
		if(result.getStatus().equals(1)){
			throw new CommonRollbackException("次考试正在进行");
		}
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

		ArrayList<String> listname = new ArrayList<>();
		listname.add("考试成绩"+result.getResultId());
		List<List<List<String>>> list2 = new ArrayList<>();
		List<List<String>> list0 = new ArrayList<>();
		ArrayList<String> list00 = new ArrayList<>();
		list00.add("考试成绩："+result.getScore());
		list00.add("考试时间：60分钟");
		list00.add("开始时间："+ DateUtil.getDateString(result.getStartDate()));
		list00.add("结束时间："+DateUtil.getDateString(result.getEndDate()));
		list0.add(list00);

		list0.add(new ArrayList<>());//空格

		//选择题
		ArrayList<String> list01 = new ArrayList<>();
		list01.add("一.选择题（10题，每题5分）");
		list0.add(list01);
		for (int i = 0; i < resultChoiceList.size(); i++) {
			ResultChoice resultChoice = resultChoiceList.get(i);
			//一排问题
			ArrayList<String> list000 = new ArrayList<>();
			list000.add((i+1)+","+resultChoice.getQuestion());
			list0.add(list000);
			//一排答案
			ArrayList<String> list001 = new ArrayList<>();
			list001.add("a,"+resultChoice.getA());
			list001.add("b,"+resultChoice.getB());
			list001.add("c,"+resultChoice.getC());
			list001.add("d,"+resultChoice.getD());
			list0.add(list001);
			//选择的答案和正确答案
			ArrayList<String> list002 = new ArrayList<>();
			String target="a";
				if(resultChoice.getTarget().equals(1)){
					target="a";
				}else if(resultChoice.getTarget().equals(2)){
					target="b";
				}else if(resultChoice.getTarget().equals(3)){
					target="c";
				}else if(resultChoice.getTarget().equals(4)){
					target="d";
				}
				String correct="a";
				if(resultChoice.getCorrect().equals(1)){
					correct="a";
				}else if(resultChoice.getCorrect().equals(2)){
					correct="b";
				}else if(resultChoice.getCorrect().equals(3)){
					correct="c";
				}else if(resultChoice.getCorrect().equals(4)){
					correct="d";
				}

			list002.add("选择的答案："+target);
			list002.add("正确的答案："+correct);
			list0.add(list002);
		}
		list0.add(new ArrayList<>());//空格
		//阅读理解
		ArrayList<String> list02 = new ArrayList<>();
		list02.add("二.阅读理解（2个阅读理解，每题5个问题，每题5分）");
		list0.add(list02);
		for (int i = 0; i < resultReadingList.size(); i++) {
			ResultReading resultReading = resultReadingList.get(i);
			//内容
			ArrayList<String> list000 = new ArrayList<>();
			list000.add(resultReading.getContent());
			list0.add(list000);
			//问题
			for (int j = 0; j < resultReading.getResultChoiceList().size(); j++) {
				ResultChoice rc = resultReading.getResultChoiceList().get(j);
				ArrayList<String> list0000 = new ArrayList<>();
				list0000.add("a,"+rc.getA());
				list0000.add("b,"+rc.getB());
				list0000.add("c,"+rc.getC());
				list0000.add("d,"+rc.getD());
				list0.add(list0000);
				//选择的答案和正确答案
				ArrayList<String> list0001 = new ArrayList<>();
				String target="a";
				if(rc.getTarget().equals(1)){
					target="a";
				}else if(rc.getTarget().equals(2)){
					target="b";
				}else if(rc.getTarget().equals(3)){
					target="c";
				}else if(rc.getTarget().equals(4)){
					target="d";
				}
				String correct="a";
				if(rc.getCorrect().equals(1)){
					correct="a";
				}else if(rc.getCorrect().equals(2)){
					correct="b";
				}else if(rc.getCorrect().equals(3)){
					correct="c";
				}else if(rc.getCorrect().equals(4)){
					correct="d";
				}

				list0001.add("选择的答案："+target);
				list0001.add("正确的答案："+correct);
				list0.add(list0001);
			}

		}
		list2.add(list0);

		MyExcel.exportData(listname,list2,request.getServletContext().getRealPath("/"),"/","resultId"+resultId+".xls");
		return "/resultId"+resultId+".xls";
	}
}

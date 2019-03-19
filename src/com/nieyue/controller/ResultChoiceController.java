package com.nieyue.controller;

import com.nieyue.bean.ResultChoice;
import com.nieyue.exception.NotAnymoreException;
import com.nieyue.exception.NotIsNotExistException;
import com.nieyue.service.ResultChoiceService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResultList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * 选择题成绩控制类
 * @author yy
 *
 */
@Api(tags={"resultChoice"},value="选择题成绩",description="选择题成绩管理")
@RestController
@RequestMapping("/resultChoice")
public class ResultChoiceController {
	@Resource
	private ResultChoiceService resultChoiceService;

	/**
	 * 选择题成绩分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "选择题成绩列表", notes = "选择题成绩分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="resultReadingId",value="阅读理解成绩id外键",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="resultId",value="考试成绩id外键",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
			@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="update_date"),
			@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	})
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ResultChoice>> list(
			@RequestParam(value="resultReadingId",required=false)Integer resultReadingId,
			@RequestParam(value="resultId",required=false)Integer resultId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="update_date") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		List<ResultChoice> list = new ArrayList<ResultChoice>();
		list= resultChoiceService.list(resultReadingId,resultId,pageNum, pageSize, orderName, orderWay);
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			throw new NotAnymoreException();//没有更多
		}
	}
	/**
	 * 选择题成绩修改
	 * @return
	 */
	@ApiOperation(value = "选择题成绩修改", notes = "选择题成绩修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ResultChoice>> update(@ModelAttribute ResultChoice resultChoice,HttpSession session)  {
		boolean um = resultChoiceService.update(resultChoice);
		if(um){
			List<ResultChoice> list = new ArrayList<>();
			list.add(resultChoice);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 选择题成绩增加
	 * @return 
	 */
	@ApiOperation(value = "选择题成绩增加", notes = "选择题成绩增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ResultChoice>> add(@ModelAttribute ResultChoice resultChoice, HttpSession session) {
		boolean am = resultChoiceService.add(resultChoice);
		if(am){
			List<ResultChoice> list = new ArrayList<>();
			list.add(resultChoice);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 选择题成绩删除
	 * @return
	 */
	@ApiOperation(value = "选择题成绩删除", notes = "选择题成绩删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="resultChoiceId",value="选择题成绩ID",dataType="int", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ResultChoice>> delete(@RequestParam("resultChoiceId") Integer resultChoiceId,HttpSession session)  {
		ResultChoice resultChoice = resultChoiceService.load(resultChoiceId);
		boolean dm = resultChoiceService.delete(resultChoiceId);
		if(dm){
			List<ResultChoice> list = new ArrayList<>();
			list.add(resultChoice);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 选择题成绩浏览数量
	 * @return
	 */
	@ApiOperation(value = "选择题成绩数量", notes = "选择题成绩数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="resultReadingId",value="阅读理解成绩id外键",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="resultId",value="考试成绩id外键",dataType="int", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="resultReadingId",required=false)Integer resultReadingId,
			@RequestParam(value="resultId",required=false)Integer resultId,
			HttpSession session)  {
		Integer count = resultChoiceService.count(resultReadingId,resultId);
			List<Integer> list = new ArrayList<Integer>();
			list.add(count);
			return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 选择题成绩单个加载
	 * @return
	 */
	@ApiOperation(value = "选择题成绩单个加载", notes = "选择题成绩单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="resultChoiceId",value="选择题成绩ID",dataType="int", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<ResultChoice>> load(@RequestParam("resultChoiceId") Integer resultChoiceId,HttpSession session)  {
		List<ResultChoice> list = new ArrayList<>();
		ResultChoice resultChoice = resultChoiceService.load(resultChoiceId);
			if(resultChoice!=null &&!resultChoice.equals("")){
				list.add(resultChoice);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				throw new NotIsNotExistException("选择题成绩");//不存在
			}
	}
	
}

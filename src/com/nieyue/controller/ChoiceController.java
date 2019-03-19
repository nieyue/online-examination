package com.nieyue.controller;

import com.nieyue.bean.Choice;
import com.nieyue.exception.NotAnymoreException;
import com.nieyue.exception.NotIsNotExistException;
import com.nieyue.service.ChoiceService;
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
 * 选择题控制类
 * @author yy
 *
 */
@Api(tags={"choice"},value="选择题",description="选择题管理")
@RestController
@RequestMapping("/choice")
public class ChoiceController {
	@Resource
	private ChoiceService choiceService;

	/**
	 * 选择题分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "选择题列表", notes = "选择题分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="readingId",value="阅读理解id外键",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="correct",value="正确，1,2,3,4分别对应a,b,c,d",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
			@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="update_date"),
			@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	})
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Choice>> list(
			@RequestParam(value="readingId",required=false)Integer readingId,
			@RequestParam(value="correct",required=false)Integer correct,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="update_date") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		List<Choice> list = new ArrayList<Choice>();
		list= choiceService.list(readingId,correct,pageNum, pageSize, orderName, orderWay);
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			throw new NotAnymoreException();//没有更多
		}
	}
	/**
	 * 选择题修改
	 * @return
	 */
	@ApiOperation(value = "选择题修改", notes = "选择题修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Choice>> update(@ModelAttribute Choice choice,HttpSession session)  {
		boolean um = choiceService.update(choice);
		if(um){
			List<Choice> list = new ArrayList<>();
			list.add(choice);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 选择题增加
	 * @return 
	 */
	@ApiOperation(value = "选择题增加", notes = "选择题增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Choice>> add(@ModelAttribute Choice choice, HttpSession session) {
		if(choice.getReadingId()==null||choice.getReadingId().equals("")){
			choice.setReadingId(0);//0代表是独立的，非阅读理解的
		}
		boolean am = choiceService.add(choice);
		if(am){
			List<Choice> list = new ArrayList<>();
			list.add(choice);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 选择题删除
	 * @return
	 */
	@ApiOperation(value = "选择题删除", notes = "选择题删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="choiceId",value="选择题ID",dataType="int", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Choice>> delete(@RequestParam("choiceId") Integer choiceId,HttpSession session)  {
		Choice choice = choiceService.load(choiceId);
		boolean dm = choiceService.delete(choiceId);
		if(dm){
			List<Choice> list = new ArrayList<>();
			list.add(choice);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 选择题浏览数量
	 * @return
	 */
	@ApiOperation(value = "选择题数量", notes = "选择题数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="readingId",value="阅读理解id外键",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="correct",value="正确，1,2,3,4分别对应a,b,c,d",dataType="int", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="readingId",required=false)Integer readingId,
			@RequestParam(value="correct",required=false)Integer correct,
			HttpSession session)  {
		Integer count = choiceService.count(readingId,correct);
			List<Integer> list = new ArrayList<Integer>();
			list.add(count);
			return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 选择题单个加载
	 * @return
	 */
	@ApiOperation(value = "选择题单个加载", notes = "选择题单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="choiceId",value="选择题ID",dataType="int", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Choice>> load(@RequestParam("choiceId") Integer choiceId,HttpSession session)  {
		List<Choice> list = new ArrayList<>();
		Choice choice = choiceService.load(choiceId);
			if(choice!=null &&!choice.equals("")){
				list.add(choice);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				throw new NotIsNotExistException("选择题");//不存在
			}
	}
	
}

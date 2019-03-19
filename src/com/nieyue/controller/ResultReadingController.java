package com.nieyue.controller;

import com.nieyue.bean.ResultReading;
import com.nieyue.exception.NotAnymoreException;
import com.nieyue.exception.NotIsNotExistException;
import com.nieyue.service.ResultReadingService;
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
 * 阅读理解成绩控制类
 * @author yy
 *
 */
@Api(tags={"resultReading"},value="阅读理解成绩",description="阅读理解成绩管理")
@RestController
@RequestMapping("/resultReading")
public class ResultReadingController {
	@Resource
	private ResultReadingService resultReadingService;

	/**
	 * 阅读理解成绩分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "阅读理解成绩列表", notes = "阅读理解成绩分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="resultId",value="考试成绩id外键",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
			@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="update_date"),
			@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	})
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ResultReading>> list(
			@RequestParam(value="resultId",required=false)Integer resultId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="update_date") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		List<ResultReading> list = new ArrayList<ResultReading>();
		list= resultReadingService.list(resultId,pageNum, pageSize, orderName, orderWay);
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			throw new NotAnymoreException();//没有更多
		}
	}
	/**
	 * 阅读理解成绩修改
	 * @return
	 */
	@ApiOperation(value = "阅读理解成绩修改", notes = "阅读理解成绩修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ResultReading>> update(@ModelAttribute ResultReading resultReading,HttpSession session)  {
		boolean um = resultReadingService.update(resultReading);
		if(um){
			List<ResultReading> list = new ArrayList<>();
			list.add(resultReading);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 阅读理解成绩增加
	 * @return 
	 */
	@ApiOperation(value = "阅读理解成绩增加", notes = "阅读理解成绩增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ResultReading>> add(@ModelAttribute ResultReading resultReading, HttpSession session) {
		boolean am = resultReadingService.add(resultReading);
		if(am){
			List<ResultReading> list = new ArrayList<>();
			list.add(resultReading);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 阅读理解成绩删除
	 * @return
	 */
	@ApiOperation(value = "阅读理解成绩删除", notes = "阅读理解成绩删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="resultReadingId",value="阅读理解成绩ID",dataType="int", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ResultReading>> delete(@RequestParam("resultReadingId") Integer resultReadingId,HttpSession session)  {
		ResultReading resultReading = resultReadingService.load(resultReadingId);
		boolean dm = resultReadingService.delete(resultReadingId);
		if(dm){
			List<ResultReading> list = new ArrayList<>();
			list.add(resultReading);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 阅读理解成绩浏览数量
	 * @return
	 */
	@ApiOperation(value = "阅读理解成绩数量", notes = "阅读理解成绩数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="resultId",value="考试成绩id外键",dataType="int", paramType = "query",defaultValue="1"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="resultId",required=false)Integer resultId,
			HttpSession session)  {
		Integer count = resultReadingService.count(resultId);
			List<Integer> list = new ArrayList<Integer>();
			list.add(count);
			return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 阅读理解成绩单个加载
	 * @return
	 */
	@ApiOperation(value = "阅读理解成绩单个加载", notes = "阅读理解成绩单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="resultReadingId",value="阅读理解成绩ID",dataType="int", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<ResultReading>> load(@RequestParam("resultReadingId") Integer resultReadingId,HttpSession session)  {
		List<ResultReading> list = new ArrayList<>();
		ResultReading resultReading = resultReadingService.load(resultReadingId);
			if(resultReading!=null &&!resultReading.equals("")){
				list.add(resultReading);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				throw new NotIsNotExistException("阅读理解成绩");//不存在
			}
	}
	
}

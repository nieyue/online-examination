package com.nieyue.controller;

import com.nieyue.bean.Reading;
import com.nieyue.exception.NotAnymoreException;
import com.nieyue.exception.NotIsNotExistException;
import com.nieyue.service.ReadingService;
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
 * 阅读理解控制类
 * @author yy
 *
 */
@Api(tags={"reading"},value="阅读理解",description="阅读理解管理")
@RestController
@RequestMapping("/reading")
public class ReadingController {
	@Resource
	private ReadingService readingService;

	/**
	 * 阅读理解分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "阅读理解列表", notes = "阅读理解分页浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
			@ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
			@ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="update_date"),
			@ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	})
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Reading>> list(
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="update_date") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		List<Reading> list = new ArrayList<Reading>();
		list= readingService.list(pageNum, pageSize, orderName, orderWay);
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			throw new NotAnymoreException();//没有更多
		}
	}
	/**
	 * 阅读理解修改
	 * @return
	 */
	@ApiOperation(value = "阅读理解修改", notes = "阅读理解修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Reading>> update(@ModelAttribute Reading reading,HttpSession session)  {
		boolean um = readingService.update(reading);
		if(um){
			List<Reading> list = new ArrayList<>();
			list.add(reading);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 阅读理解增加
	 * @return 
	 */
	@ApiOperation(value = "阅读理解增加", notes = "阅读理解增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Reading>> add(@ModelAttribute Reading reading, HttpSession session) {
		boolean am = readingService.add(reading);
		if(am){
			List<Reading> list = new ArrayList<>();
			list.add(reading);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 阅读理解删除
	 * @return
	 */
	@ApiOperation(value = "阅读理解删除", notes = "阅读理解删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="readingId",value="阅读理解ID",dataType="int", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Reading>> delete(@RequestParam("readingId") Integer readingId,HttpSession session)  {
		Reading reading = readingService.load(readingId);
		boolean dm = readingService.delete(readingId);
		if(dm){
			List<Reading> list = new ArrayList<>();
			list.add(reading);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(null);
	}
	/**
	 * 阅读理解浏览数量
	 * @return
	 */
	@ApiOperation(value = "阅读理解数量", notes = "阅读理解数量查询")
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			HttpSession session)  {
		Integer count = readingService.count();
			List<Integer> list = new ArrayList<Integer>();
			list.add(count);
			return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 阅读理解单个加载
	 * @return
	 */
	@ApiOperation(value = "阅读理解单个加载", notes = "阅读理解单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="readingId",value="阅读理解ID",dataType="int", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Reading>> load(@RequestParam("readingId") Integer readingId,HttpSession session)  {
		List<Reading> list = new ArrayList<>();
		Reading reading = readingService.load(readingId);
			if(reading!=null &&!reading.equals("")){
				list.add(reading);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				throw new NotIsNotExistException("阅读理解");//不存在
			}
	}
	
}

package com.atguigu.bos.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.atguigu.bos.service.FunctionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;

@Controller
@RequestMapping("/functionController")
public class FunctionController {
	
	@Autowired
	FunctionService functionService;
	
	/**
	 * 根据登录人查询对应的菜单数据（从权限表中查询）
	 * @throws IOException 
	 */
	@RequestMapping("/findMenu")
	@ResponseBody
	public List<Function> findMenu(HttpServletRequest request) throws IOException{
		
		User user=(User) request.getSession().getAttribute("loginUser");
		
		List<Function> list =null;
		if(user!=null){
			list=functionService.findMenu(user);
		}else{
			list=null;
		}
		return list;
	}
	
	/**
	 * 父功能点
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listajax")
	public List<Function> listajax(){
		
		return functionService.getParament();
	}
	//添加权限
	@RequestMapping("/add")
	public String add(Function function){
		functionService.addfunction(function);
			
		return "admin/function";
	}
	@RequestMapping("/pageQuery")
	@ResponseBody
	public PageInfo pageQuery(HttpServletRequest request){
		
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		
		int pages=1;
		int rowss=5;
		
		try {
			pages=Integer.parseInt(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			rowss=Integer.parseInt(rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 在查询之前只需要调用，传入页码，以及每页的大小
		PageHelper.startPage(pages, rowss);
		// startPage后面紧跟的这个查询就是一个分页查询
		List<Function> emps = functionService.getAll();
		
		PageInfo pageInfo = new PageInfo(emps, rowss);
		return pageInfo;
	}
}

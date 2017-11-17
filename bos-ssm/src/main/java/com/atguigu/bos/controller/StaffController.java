package com.atguigu.bos.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bos.bean.Staff;
import com.atguigu.bos.service.StaffService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class StaffController {
	@Autowired
	StaffService staffService;

	/**
	 * 查询没有作废的取派员，返回json
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("staffListajax")
	public List<Staff> staffListajax(Staff staff){
		List<Staff> list = staffService.findListNotDelete();
		return list;
	}
	@RequestMapping("staffActionEdit")
	public String staffActionEdit(Staff staff){
		staffService.staffActionEdit(staff);
		return "base/staff";
	}
	/**
	 * 批量删除(逻辑删除)
	 * @return
	 */
	//@RequiresPermissions(value="staff")//执行当前方法需要具有staff权限
	@RequiresRoles(value="abc")
	@RequestMapping("staffDelete")
	public String staffDelete(@RequestParam(value="ids") String ids,Staff staff){
		
		staffService.staffDeleteBatch(staff,ids);
		return "base/staff";
	}
	
	/**
	 * 分页查询方法
	 * @throws IOException 
	 */
	@RequestMapping("/staffPageQuery")
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
		List<Staff> emps = staffService.getAll();
		// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
		// 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		/*PageInfo pageInfo = new PageInfo(emps, rowss);
		int tol = Integer.parseInt(String.valueOf(pageInfo.getTotal()));
		PageInfoSimple pageInfoSimple = new PageInfoSimple(emps, tol);*/
		PageInfo pageInfo = new PageInfo(emps, rowss);
		return pageInfo;
																			
	}
	/**
	 * 员工保存
	 * 1、支持JSR303校验
	 * 2、导入Hibernate-Validator
	 * @return
	 */
	@RequestMapping("staffAdd")
	public String staffAdd(@Valid Staff staff,BindingResult result,HttpServletRequest request){
		String path;
		if(result.hasErrors()){
			//校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			Map<String, Object> map = new HashMap<>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("错误的字段名："+fieldError.getField());
				System.out.println("错误信息："+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			request.setAttribute("errorFields", map);
			path="errorPage";
		}else{
			/*try {
				staffService.addStaff(staff);
				path="index";
			} catch (DataAccessException e) {
				path="errorPage";
			}*/
			
			staffService.addStaff(staff);
			path="base/staff";
		}
		return path;
	}
	
}

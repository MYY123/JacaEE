package com.atguigu.bos.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bos.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;

@Controller
@RequestMapping("/roleControll")
public class RoleControll {
	
	@Autowired
	RoleService roleService;
	        
	@RequestMapping("/pageQuery")
	@ResponseBody//无分页
	public List<Role> pageQuery( ){
		List<Role> emps = roleService.getAll();
		return emps;
	}
	@RequestMapping("/add")
	public String add(Role role,HttpServletRequest request){
		String ids=request.getParameter("ids");
		roleService.add(role,ids);
		return "admin/role";
	}
}

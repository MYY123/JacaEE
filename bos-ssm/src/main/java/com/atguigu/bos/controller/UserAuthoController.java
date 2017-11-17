package com.atguigu.bos.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bos.service.UserAuthoService;
import com.atguigu.bos.utils.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.bos.domain.User;

@RequestMapping("/userAuthoController")
@Controller
public class UserAuthoController {
	
	@Autowired
	UserAuthoService userAuthoService;
	
	//用户登录功能,有权限的登录
	   @RequestMapping("/userLogin")
		public String userLogin(User userPara,HttpServletRequest request,Locale locale){
		   
		 request.getParameter("checkcode");
		   request.getSession().getAttribute("key");
		   //暂时关闭验证码功能
		   //if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)){
				//验证码正确
		 //验证码正确
			//获得当前用户对象
			Subject subject = SecurityUtils.getSubject();//状态为“未认证”
			String password = userPara.getPassword();
			password = MD5Utils.md5(password);
			//构造一个用户名密码令牌
			AuthenticationToken token = new UsernamePasswordToken(userPara.getUsername(), password);
			try{
				subject.login(token);//这条语句之后开始执行BosRealm
			}catch (UnknownAccountException e) {
				e.printStackTrace();
				//设置错误信息
				//this.addActionError(this.getText("usernamenotfound"));
				return "forward:/login.jsp";
			}catch (Exception e) {
				e.printStackTrace();
				//设置错误信息
				//this.addActionError(this.getText("loginError"));
				return "forward:/login.jsp";
			}
			//获取认证信息对象中存储的User对象
			User user = (User) subject.getPrincipal();
			
			if(request.getSession().getAttribute("loginUser")==null){
				
				request.getSession().setAttribute("loginUser", user);
				
				   String id =user.getId();
				   String username =user.getUsername();
				   String passwords =user.getPassword();
				   Double salary =user.getSalary();
				   Date birthday =user.getBirthday();
				   String gender =user.getGender();
				   String station =user.getStation();
				   String telephone =user.getTelephone();
				   String remark =user.getRemark();
				   
				   Map userMap=new HashMap();
				   
				   userMap.put("id", id);
				   userMap.put("username", username);
				   userMap.put("passwords", passwords);
				   userMap.put("salary", salary);
				   userMap.put("birthday", birthday);
				   userMap.put("gender", gender);
				   userMap.put("station", station);
				   userMap.put("telephone", telephone);
				   userMap.put("remark", remark);
				   request.getSession().setAttribute("loginUserMap", userMap);
			}
			
			return "common/index";
				
			/*}else{暂时关闭验证码功能
				//验证码错误,设置错误提示信息，跳转到登录页面
				//this.addActionError(this.getText("validateCodeError"));
				return "redirect:/login.jsp"; 
			}*/
		   
		  
		}
	//增加用户
	@RequestMapping("/add")
	public String add(User user,HttpServletRequest request){
		String[] roleIds=request.getParameterValues("roleIds");
		userAuthoService.add(user,roleIds);
		
		return  "admin/userlist";
	}
	
	@ResponseBody
	@RequestMapping("/pageQuery")
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
		List<User> emps = userAuthoService.getAll();
		PageInfo pageInfo = new PageInfo(emps, rowss);
		return pageInfo;
	}
}

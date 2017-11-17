package com.atguigu.bos.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bos.bean.User;
import com.atguigu.bos.service.UserService;
@Controller
public class UserController {
	@Autowired
    UserService userService;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	
	//用户登录功能,没有权限的
   @RequestMapping("/userLoginBak")
	public String userLoginBak(User userPara,HttpServletRequest request,Locale locale){
	 request.getParameter("checkcode");
	   request.getSession().getAttribute("key");
	   //暂时关闭验证码功能
	   //if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)){
			//验证码正确
		   User user = userService.queryUser(userPara);
			if(user != null){
				//登录成功,将User放入session域，跳转到系统首页
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
			}else{
				//登录失败，设置错误提示信息，跳转到登录页面
				//this.addActionError(this.getText("loginError"));
				String val = messageSource.getMessage("i18n.user", null, locale);
				System.out.print(val);
				request.setAttribute("errorMessage", val);
				//ModelAndView mv = new ModelAndView("redirect:/login.jsp");
			    //return "redirect:/login.jsp";  
				//return "forward:/login.jsp";
				                
				return "errorPage";
			}
		/*}else{暂时关闭验证码功能
			//验证码错误,设置错误提示信息，跳转到登录页面
			//this.addActionError(this.getText("validateCodeError"));
			return "redirect:/login.jsp"; 
		}*/
	   
	  
	}
   
   /**
	 * 用户退出
	 */
   @RequestMapping("/logout")
	public String logout(HttpServletRequest request){
		//销毁session
	   request.getSession().invalidate();
	   return "forward:/login.jsp";
	}
   //密码修改功能
   @ResponseBody
   @RequestMapping("/editPassword")
   public String editPassword(HttpServletRequest request,String password){
	   //String newPassword=request.getParameter("password");也能得到
	   String newPassword=password;
	   //String path;
	   String data;
	   User user=(User) request.getSession().getAttribute("loginUser");
	   if(newPassword!=null&&user!=null){
		   userService.setPassword(user,newPassword);
		   //path="common/index";
		   data="1";
	   }else{
		   data="-1";
	   }
	   
	   
	   //return path;
	   return data;
	   
   }
}

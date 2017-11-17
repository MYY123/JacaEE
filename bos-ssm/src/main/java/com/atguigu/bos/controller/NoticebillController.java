package com.atguigu.bos.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bos.service.NoticebillService;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.User;

import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
/**
 * 
 * @author 马建超
 *2017年11月13日
 * 上午11:20:53
 */
@Controller
@RequestMapping("/noticebillController")
public class NoticebillController {
	@Autowired
	CustomerService customerService;
	@Autowired
	NoticebillService noticebillService;
	
	/**
	 * 调用代理对象，根据手机号查询客户信息
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/findCustomerByTelephone")
	public Customer findCustomerByTelephone(Noticebill noticebill) throws IOException{
		Customer customer = customerService.findCustomerByPhonenumber(noticebill.getTelephone());
		
		return customer;
	}
	
	/**
	 * 保存业务通知单，尝试自动分单
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Noticebill noticebill,HttpServletRequest request){
		//User user = (User)request.getSession().getAttribute("loginUser");
		Map userMap=new HashMap(16);
		userMap=(Map) request.getSession().getAttribute("loginUserMap");
		User user=new User();
		
		String id =(String) userMap.get("id");
		String username =(String) userMap.get("username");
		String passwords = (String) userMap.get("passwords");
		Double salary =  (Double) userMap.get("salary");
		Date birthday =  (Date) userMap.get("birthday");
		String gender =  (String) userMap.get("gender");
		String station =  (String) userMap.get("station");
		String telephone =  (String) userMap.get("telephone");
		String remark = (String) userMap.get("remark");
		   
			   user.setId(id);
			   user.setUsername(username);
			   user.setPassword(passwords);
			   user.setSalary(salary);
			   user.setBirthday(birthday);
			   user.setGender(gender);
			   user.setStation(station);
			   user.setTelephone(telephone);
			   user.setRemark(remark);
		   
		noticebill.setUser(user);
		noticebillService.save(noticebill);
		return "qupai/noticebill_add";
	}

}

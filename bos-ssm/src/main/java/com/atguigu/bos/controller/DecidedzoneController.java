package com.atguigu.bos.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bos.bean.Decidedzone;
import com.atguigu.bos.bean.Staff;
import com.atguigu.bos.service.DecidedzoneService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
/**
 * 
 * @author jljd
 * @date
 */
@Controller
@RequestMapping("/decidedzoneController")
public class DecidedzoneController {
	@Autowired
	DecidedzoneService decidedzoneService;
	@Autowired
	CustomerService customerService;
	
	/**
	 * 查询没有关联到定区的客户
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/findnoassociCustomers")
	public List<Customer> findnoassociationCustomers() throws IOException{
		List<Customer> list = customerService.findnoassociationCustomers();
		return list;
	}
	
	/**
	 * 查询已经关联到指定定区的客户
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/findhasassociCustomers")
	public List<Customer> findhasassociationCustomers(Decidedzone decidedzone) throws IOException{
		List<Customer> list = customerService.findhasassociationCustomers(decidedzone.getId());
		return list;
	}
	
	/**
	 * 定区关联客户
	 * @return
	 */
	@RequestMapping("/assignCusToDecizone")
	public String assigncustomerstodecidedzone(HttpServletRequest request,Decidedzone decidedzone){
		
		String[] customerIds=request.getParameterValues("customerIds");
		customerService.assignCustomersToDecidedZone(customerIds, decidedzone.getId());
		return "base/decidedzone";
	}
	
	
	@RequestMapping("/add")
	public String add(Decidedzone decidedzone,HttpServletRequest request) {
		String[] subareaid=request.getParameterValues("subareaid");
		decidedzoneService.save(decidedzone, subareaid);
		return "base/decidedzone";
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
		List<Decidedzone> decides = decidedzoneService.getAll();
		
		PageInfo pageInfo = new PageInfo(decides, rowss);
		return pageInfo;
		
	}
}

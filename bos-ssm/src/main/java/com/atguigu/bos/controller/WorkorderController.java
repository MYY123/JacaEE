package com.atguigu.bos.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.atguigu.bos.service.WorkorderService;
import com.itheima.bos.domain.Workordermanage;

@Controller
@RequestMapping("/workorderController")
public class WorkorderController {
	
	@Autowired
	WorkorderService workorderService; 
	
	
	@ResponseBody
	@RequestMapping("/add")
	public String add(Workordermanage Workordermanage,HttpServletResponse response) throws IOException{
		String flag = "1";
		try{
			workorderService.save(Workordermanage);
		}catch (Exception e) {
			flag = "0";
		}
		/*response.setContentType(
				"text/html;charset=UTF-8");
		response.getWriter().print(flag);*/
		return flag;
	}
	@RequestMapping("/list")
	public ModelAndView list(){
		ModelAndView mov=new ModelAndView();
		List<Workordermanage> workManager=workorderService.findListNoStart();
		mov.addObject("workManager", workManager);
		mov.setViewName("/workflow/startransfer");
		return mov;
		
	}
	//启动流程实例，根据key
	@RequestMapping("/start")
	public String start(@RequestParam(value="id") String workOrderManageId){
		
		workorderService.start(workOrderManageId);
		return "redirect:/workorderController/list";
	}
}

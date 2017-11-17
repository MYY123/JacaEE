package com.atguigu.bos.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.bos.service.WorkorderService;
import com.itheima.bos.domain.User;
import com.itheima.bos.domain.Workordermanage;


/**
 * @author MJC
 *2017年11月16日
 * 下午3:58:47
 */
@Controller
@RequestMapping("/taskController")
public class TaskController {
	
	@Autowired TaskService taskService;
	@Autowired RuntimeService runtimeService;
	@Autowired WorkorderService workorderService;
	 @RequestMapping("/findGroupTask")
	 public String findGroupTask(HttpServletRequest request){
		 TaskQuery taskQuery=taskService.createTaskQuery();
		 User user= (User) request.getSession().getAttribute("loginUser");
		 String candidateUser=user.getId();
		 taskQuery.taskCandidateUser(candidateUser);
		 List<Task> list= taskQuery.list();
		 request.setAttribute("list", list);
		 return "/workflow/grouptask";
		
	}
	 
	@RequestMapping("/takeTask")
	 public String takeTask(@RequestParam(value="taskId") String taskId, HttpServletRequest request) {
		 User user= (User) request.getSession().getAttribute("loginUser");
		 taskService.claim(taskId, user.getId());
		return "redirect:/taskController/findGroupTask";
	}
	
	@RequestMapping("findPersonalTask")
	public String findPersonalTask(HttpServletRequest request){
		
		 TaskQuery taskQuery=taskService.createTaskQuery();
		 User user= (User) request.getSession().getAttribute("loginUser");
		 String assignee=user.getId();
		 taskQuery.taskAssignee(assignee);
		 List<Task> list= taskQuery.list();
		 request.setAttribute("list", list);
		 return "/workflow/personaltask";
	}
	@RequestMapping("/checkWorkOrderManage")
	public String  checkWorkOrderManage(@RequestParam(value="taskId") String taskId
			,@RequestParam(value="check",required =false) String check,HttpServletRequest request){
		// 根据任务id查询任务对象
				Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
				// 根据任务对象查询流程实例id
				String processInstanceId = task.getProcessInstanceId();
				// 根据流程实例id查询流程实例对象
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
				String workordermanageId = processInstance.getBusinessKey();
				
				Workordermanage workordermanage = workorderService.findById(workordermanageId);
				if(check == null){
					//跳转到审核页面
					// 跳转到一个审核工作单页面，展示当前对应的工作单信息
					
					//ActionContext.getContext().getValueStack().set("map", workordermanage);
					request.setAttribute("map", workordermanage);
					request.setAttribute("taskId", taskId);
					return "/workflow/check";
				}else{
					int checkk=Integer.parseInt(check);
					workorderService.checkWorkordermanage(taskId,checkk,workordermanageId);
					return "redirect:/taskController/findPersonalTask";
				}
	}
	@RequestMapping("/outStore")
	public String outStore(@RequestParam(value="taskId") String taskId){
		taskService.complete(taskId);
		return "redirect:/taskController/findPersonalTask";
	}
	
	@RequestMapping("/transferGoods")
	public String transferGoods(@RequestParam(value="taskId") String taskId){
		taskService.complete(taskId);
		return "redirect:/taskController/findPersonalTask";
	}
	
	@RequestMapping("/receive")
	public String receive(@RequestParam(value="taskId") String taskId){
		taskService.complete(taskId);
		return "redirect:/taskController/findPersonalTask";
	}
}

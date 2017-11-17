package com.atguigu.bos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author MJC
 *2017年11月15日
 * 下午4:36:02
 */
@Controller
@RequestMapping("/processInstanceContro")
public class ProcessInstanceContro {
	
	@Autowired RuntimeService runtimeService;
	@Autowired RepositoryService repositoryService;
	
	@RequestMapping("/list")
	public ModelAndView list(){
		ProcessInstanceQuery query=runtimeService.createProcessInstanceQuery();
		query.orderByProcessInstanceId().asc();
		List<ProcessInstance> list=query.list();
		ModelAndView mov=new ModelAndView();
		mov.addObject("list", list);
		mov.setViewName("/workflow/processinstance");
		return mov;
	}
	
	//需要跳页面
	@RequestMapping("/showPng")
	public ModelAndView showPng(@RequestParam(value="id") String id){
		//根据流程定义id 查询部署id 流程图名字
		ProcessInstanceQuery query=runtimeService.createProcessInstanceQuery();
		ProcessInstance processInstance =query.processInstanceId(id).singleResult();
		String processDefinitionId=processInstance.getProcessDefinitionId();
		ProcessDefinition ProcessDefinition=repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		String imageName=ProcessDefinition.getDiagramResourceName();
		
		//查询坐标
				//1、获得当前流程实例执行到哪个节点
				String activityId = processInstance.getActivityId();//usertask1
				//2、加载bpmn（xml）文件，获得一个流程定义对象
				ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);//查询act_ge_bytearray
				//3、根据activitiId获取含有坐标信息的对象
				ActivityImpl findActivity = pd.findActivity(activityId);
				int x = findActivity.getX();
				int y = findActivity.getY();
				int width = findActivity.getWidth();
				int height = findActivity.getHeight();
				
		ModelAndView mov=new ModelAndView();
		String deploymentId=ProcessDefinition.getDeploymentId();
		mov.addObject("deploymentId", deploymentId);
		mov.addObject("imageName", imageName);
		mov.addObject("x", x);
		mov.addObject("y", y);
		mov.addObject("width", width);
		mov.addObject("height", height);
		mov.setViewName("/workflow/image");
		return mov;
		
	}
	@RequestMapping("/viewImage")
	public void viewImage(@RequestParam(value="deploymentId")String deploymentId
			,@RequestParam(value="imageName")String imageName,HttpServletResponse response){
		
		//获取png图片对应的输入流
				response.setContentType("image/png");
				InputStream pngStream = null;
				OutputStream os = null; 
				
				try {
					pngStream = repositoryService.getResourceAsStream(deploymentId, imageName);
						os = response.getOutputStream();  
						int len;  
						while ((len = pngStream.read()) != -1) {  
						 os.write(len);  
							}
					} catch (IOException e) {
					e.printStackTrace();
					}  
						finally{
								try {
									pngStream.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
						}

			}
	}

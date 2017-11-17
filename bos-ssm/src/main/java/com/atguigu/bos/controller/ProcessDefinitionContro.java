package com.atguigu.bos.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
/**
 * @author MJC
 *2017年11月14日
 * 下午6:47:04
 */
@Controller
@RequestMapping("/processDefinitionContro")
public class ProcessDefinitionContro {
	@Autowired RepositoryService repositoryService;
	//遍历流程定义表
	@RequestMapping("/list")
	public ModelAndView list(){
		ProcessDefinitionQuery query=repositoryService.createProcessDefinitionQuery();
		query.orderByProcessDefinitionVersion().latestVersion();
		query.orderByProcessDefinitionName().desc();
		List<ProcessDefinition> list=query.list();
		ModelAndView mov=new ModelAndView();
		mov.addObject("list", list);
		mov.setViewName("/workflow/processdefinition_list");
		return mov;
	}
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="id") String id,HttpServletRequest request ){
		String deltag = "0";
		//根据流程定义id查询部署id
				ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
				query.processDefinitionId(id);//根据id过滤
				ProcessDefinition processDefinition = query.singleResult();
				String deploymentId = processDefinition.getDeploymentId();
		try{
			repositoryService.deleteDeployment(deploymentId);
		}catch (Exception e) {
			//当前要删除的流程定义正在使用
			deltag = "1";
			request.setAttribute("deltag", deltag);
			 return "redirect:/processDefinitionContro/list";
		}
		 return "redirect:/processDefinitionContro/list";
	}
	
	
	/**
	 * 文件上传
	 * @param desc
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/deploy")
	public String deploy(@RequestParam("zipFile") MultipartFile zipFile) throws IOException{
		
		DeploymentBuilder deploymentBuilder = repositoryService
				.createDeployment();
		/*deploymentBuilder.addZipInputStream(new ZipInputStream(
				new FileInputStream(zipFile)));*/
		
		deploymentBuilder.addZipInputStream(new ZipInputStream(zipFile.getInputStream()));
		deploymentBuilder.deploy();
		 return "redirect:/processDefinitionContro/list";
	}
	
	//文件下载
	@RequestMapping("/showpng")
	private void  showpng(@RequestParam(value="id") String id
			,HttpServletResponse response) {
		//获取png图片对应的输入流
		response.setContentType("image/png");
		InputStream pngStream = null;
		OutputStream os = null; 
		
		try {
				pngStream = repositoryService.getProcessDiagram(id);
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

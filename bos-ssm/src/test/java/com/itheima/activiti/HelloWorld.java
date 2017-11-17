package com.itheima.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

public class HelloWorld {
	/**
	 * 使用框架提供的自动建表方式创建25张表
	 */
	
	/*public static void main(String[] args) {
		ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
		        .setJdbcUrl("jdbc:mysql:///activiti")
		        .setJdbcUsername("root")
		        .setJdbcPassword("root")
		        .setJdbcDriver("com.mysql.jdbc.Driver")
		        .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		    ProcessEngine processEngine = cfg.buildProcessEngine();
	}*/
	
	/*@Test
	public void test1() {
		String resource = "activiti-context.xml";// 配置文件
		String beanName = "processEngineConfiguration";
		// 读取配置文件，获得配置对象
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource(resource,
						beanName);
		ProcessEngine processEngine = config.buildProcessEngine();
	}*/

	/**
	 * 使用默认配置 1、配置文件必须在classpath根目录下
	 * 2、配置文件名称必须为activiti-context.xml或者activiti.cfg.xml
	 * 3、配置文件中配置对象的id必须为processEngineConfiguration 4、工厂对象的id必须为processEngine
	 */
	/*@Test
	public void test2() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	}

	

	*/
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**
	 * 部署流程定义,操作的数据表：act_re_deployment(部署表)、act_re_procdef(流程定义表)、
	 * act_ge_bytearray(二进制表)
	 */
	@Test
	public void test3() {
		// 创建一个部署构建器对象
		DeploymentBuilder deploymentBuilder = processEngine
				.getRepositoryService().createDeployment();
		// 读取流程定义文件（bpmn、png）
		deploymentBuilder.addClasspathResource("transfer2.bpmn");
		deploymentBuilder.addClasspathResource("transfer2.png");
		// 部署流程定义
		Deployment deployment = deploymentBuilder.deploy();
		System.out.println(deployment.getId());
	}

	/**
	 * 查询流程定义数据,操作数据表：act_re_procdef
	 */
	@Test
	public void test4() {
		// 流程定义查询对象
		ProcessDefinitionQuery query = processEngine.getRepositoryService()
				.createProcessDefinitionQuery();
		List<ProcessDefinition> list = query.list();
		for (ProcessDefinition pd : list) {
			System.out
					.println("id = " + pd.getId() + " name = " + pd.getName()
							+ " version = " + pd.getVersion() + " key = "
							+ pd.getKey());

		}
	}

	/**
	 * 根据流程定义启动流程实例,操作的数据表：act_ru_execution(流程实例表)、act_ru_task(任务表)
	 */
	@Test
	public void test5() {
		String processDefinitionId = "qjlc:8:20008";// 流程定义id
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceById(processDefinitionId);
		System.out.println(processInstance.getId());
	}
	/**
	 * 查询个人任务,操作的数据表：act_ru_task(任务表)
	 */
	@Test
	public void test6() {
		// 任务查询对象
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		query.taskAssignee("李四");// 根据办理人过滤
		query.orderByTaskCreateTime().desc();
		List<Task> list = query.listPage(0, 10);
		for (Task task : list) {
			System.out.println("id = " + task.getId() + " name = "
					+ task.getName() + " processInstanceId = "
					+ task.getProcessInstanceId());
		}
	}
	
	/**
	 * 办理个人任务,操作的数据表：act_ru_execution(流程实例表)、act_ru_task(任务表)
	 */
	@Test
	public void test7() {
		String taskId = "602";//任务id
		processEngine.getTaskService().complete(taskId);
	}
}

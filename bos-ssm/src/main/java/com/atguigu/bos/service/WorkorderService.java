package com.atguigu.bos.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itheima.bos.dao.IWorkordermanageDao;
import com.itheima.bos.domain.Workordermanage;

@Service
@Transactional
public class WorkorderService {
	@Autowired
	private IWorkordermanageDao workordermanageDao;
	
	@Autowired
	RuntimeService runtimeService; 
	
	@Autowired
	TaskService taskService; 
	
	@Autowired
	HistoryService historyService; 
	
	public void save(Workordermanage workordermanage) {
		workordermanage.setUpdatetime(new Date());
		workordermanageDao.save(workordermanage);
		
	}
	/**
	 * @return
	 */
	public List<Workordermanage> findListNoStart() {
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Workordermanage.class);
		detachedCriteria.add(Restrictions.eq("start","0"));
		return workordermanageDao.findByCriteria(detachedCriteria);
	}
	/**
	 * @param workOrderManageId
	 */
	public void start(@RequestParam(value="id") String workOrderManageId) {
		// TODO Auto-generated method stub
		Workordermanage workordermanage=workordermanageDao.findById(workOrderManageId);
		workordermanage.setStart("1");
		String ProcessDedifiKey="transfer";
		String bussnessKey=workOrderManageId;//让业务数据关联到activiti
		Map<String,Object> veriable=new HashMap<String,Object>();//流程变量
		veriable.put("业务数据", workordermanage);
		runtimeService.startProcessInstanceByKey(ProcessDedifiKey, bussnessKey, veriable);
		
	}
	/**
	 * @param workordermanageId
	 * @return
	 */
	public Workordermanage findById(String workordermanageId) {
		return workordermanageDao.findById(workordermanageId);
	}
	/**
	 * @param taskId
	 * @param check
	 * @param workordermanageId
	 */
	public void checkWorkordermanage(String taskId, Integer check, String workordermanageId) {
		// TODO Auto-generated method stub
		Workordermanage workordermanage=workordermanageDao.findById(workordermanageId);
		
		Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
		Map<String,Object> veriable=new HashMap<String,Object>();//流程变量
		veriable.put("check", check);
		
		String processInstanceId=task.getProcessInstanceId();
		taskService.complete(taskId, veriable);
		if(check==0){
			workordermanage.setStart("0");
			historyService.deleteHistoricProcessInstance(processInstanceId);
		}
		
	}
	
}

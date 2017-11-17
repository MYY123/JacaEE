package com.atguigu.bos.service;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;

@Service
public class RoleService {

	@Autowired
	IRoleDao iRoleDao;
	@Autowired
	IdentityService identityService;
	public void add(Role role, String ids) {

		iRoleDao.save(role);
		Group group =new GroupEntity(role.getName());
		identityService.saveGroup(group);
		String[] iDs=ids.split(",");
		for(String id:iDs){
			Function function=new Function(id);
			role.getFunctions().add(function);
			
		}
 	}
	public List<Role> getAll() {
		return iRoleDao.findAll();
	}

}

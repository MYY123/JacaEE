package com.atguigu.bos.service;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bos.utils.MD5Utils;
import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;

@Service
public class UserAuthoService {

	@Autowired IUserDao iUserDao;   
	@Autowired IRoleDao iRoleDao;
	
	@Autowired IdentityService identityService;
	public List<User> getAll() {
		return iUserDao.findAll();
	}
	public void add(User user, String[] roleIds) {

		String password = user.getPassword();
		password = MD5Utils.md5(password);
		user.setPassword(password);
		iUserDao.save(user);//持久对象 
		
		org.activiti.engine.identity.User actUser=new UserEntity(user.getId());
		identityService.saveUser(actUser);
		for (String roleId : roleIds) {
			Role role = iRoleDao.findById(roleId);
			//用户关联角色
			user.getRoles().add(role);
			
			identityService.createMembership(actUser.getId(), role.getName());
		}
	}

}

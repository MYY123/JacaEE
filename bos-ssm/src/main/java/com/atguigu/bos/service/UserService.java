package com.atguigu.bos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bos.bean.User;
import com.atguigu.bos.bean.UserExample;
import com.atguigu.bos.bean.UserExample.Criteria;
import com.atguigu.bos.dao.UserMapper;
import com.atguigu.bos.utils.MD5Utils;
import com.itheima.bos.dao.IUserDao;
@Service
public class UserService {
	@Autowired UserMapper userMapper;
	
	public User queryUser(User user) {
		User returnUser;
		String username = user.getUsername();
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		
		UserExample  example= new UserExample();
		Criteria criteria =example.createCriteria();
		criteria.andUsernameEqualTo(username);
		criteria.andPasswordEqualTo(password);
		try {
			returnUser=userMapper.selectByExample(example).get(0);
		} catch (Exception e) {
			returnUser=null;
		}
		return returnUser;
	}

	public int setPassword(User user,String newPassword) {
		// TODO Auto-generated method stub
		String password;
		password = MD5Utils.md5(newPassword);
		UserExample  example= new UserExample();
		Criteria criteria =example.createCriteria();
		criteria.andPasswordEqualTo(password);
		return userMapper.updateByExampleSelective(user, example);
		
	}

}

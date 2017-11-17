package com.atguigu.bos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bos.bean.Staff;
import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;

@Service
public class FunctionService {
	@Autowired
	IFunctionDao iFunctionDao;
	public List<Function> getAll() {
		return iFunctionDao.findAllMenu();
	}
	//增加权限
	public void addfunction(Function function) {
		if(function.getFunction()!=null&&function.getFunction().getId().equals("")){
			function.setFunction(null);
		}
		 iFunctionDao.save(function);
	}
	
	public  List<Function> getParament() {
		return iFunctionDao.findAll();
	}
	public List<Function> findMenu(User user) {
		//User user = BOSContext.getLoginUser();
		List<Function> list = null;
		if (user.getUsername().equals("admin")) {
			//查询所有菜单
			list = iFunctionDao.findAllMenu();
		}else{
			//普通用户，查询对应的菜单
			list = iFunctionDao.findMenuByUserid(user.getId());
		}
		return list;
	}

}

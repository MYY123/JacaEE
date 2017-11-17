package com.itheima.bos.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;

/**
 * 自定义realm
 * 
 * @author zhaoqx
 * 
 */							  
public class BOSRealm extends AuthorizingRealm {
	@Resource
	private IUserDao userDao;
	@Resource
	private IFunctionDao functionDao;

	/**
	 * 认证方法
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证方法开始...");
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();// 从令牌中获得用户名
							
		User user = userDao.findUserByUsername(username);
		if (user == null) {
			// 用户名不存在
			return null;
		} else {
			// 用户名存在
			String password = user.getPassword();// 获得数据库中存储的密码
			// 创建简单认证信息对象
			/***
			 * 参数一：签名，程序可以在任意位置获取当前放入的对象
			 * 参数二：从数据库中查询出的密码
			 * 参数三：当前realm的名称
			 */
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
					password, this.getClass().getSimpleName());
			return info;//返回给安全管理器，由安全管理器负责比对数据库中查询出的密码和页面提交的密码
		}
	}

	/**
	 * 授权方法
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		System.out.println("授权方法开始...");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		info.addStringPermission("staff");//为当前用户授予staff权限
		//TODO 根据当前登录用户查询数据库，获取其对应的权限数据
		info.addRole("staff");//为当前用户授予staff角色
		
		//根据当前登录用户查询其对应的权限，授权
		User user = (User) principals.getPrimaryPrincipal();
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			//当前用户是超级管理员，查询所有权限，为用户授权
			list = functionDao.findAll();
		}else{
			//普通用户，根据用户id查询对应的权限
			list = functionDao.findListByUserid(user.getId());
		}
		for (Function function : list) {
			info.addStringPermission(function.getCode());
		}
		return info;
	}

}

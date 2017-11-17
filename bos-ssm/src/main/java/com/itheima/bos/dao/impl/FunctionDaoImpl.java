package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Function;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements
		IFunctionDao {
	/**
	 * 根据用户id查询对应的权限
	 */
	public List<Function> findListByUserid(String userid) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r" +
				" LEFT OUTER JOIN r.users u WHERE u.id = ?";
		return (List<Function>) this.getHibernateTemplate().find(hql, userid);
	}

	/**
	 * 查询所有的菜单
	 */
	public List<Function> findAllMenu() {
		String hql = "FROM Function f WHERE f.generatemenu = '1' ORDER BY f.zindex DESC";
		return (List<Function>) this.getHibernateTemplate().find(hql);
	}

	/**
	 * 根据用户id查询对应的菜单
	 */
	public List<Function> findMenuByUserid(String id) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r" +
				" LEFT OUTER JOIN r.users u WHERE u.id = ? AND f.generatemenu = '1' ORDER BY f.zindex DESC ";
		return (List<Function>) this.getHibernateTemplate().find(hql, id);
	}}

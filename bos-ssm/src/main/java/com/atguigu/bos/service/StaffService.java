package com.atguigu.bos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.bos.bean.Staff;
import com.atguigu.bos.bean.StaffExample;
import com.atguigu.bos.bean.StaffExample.Criteria;
import com.atguigu.bos.dao.StaffMapper;

@Service
@Transactional
public class StaffService {
	@Autowired
	StaffMapper staffMapper;
	
	public void addStaff(Staff staff)throws DataAccessException {
		
			staffMapper.insertSelective(staff);
	
	}

	public List<Staff> getAll() {
		return staffMapper.selectByExample(null);
	}

	public void staffDeleteBatch(Staff staff,String ids) {
		// TODO Auto-generated method stub
		String[] idss=ids.split(",");
		for(String id:idss){
			staff.setId(id);
			staff.setDeltag("1");
			staffMapper.updateByPrimaryKeySelective(staff);
		}
		
	}
	//更新前需要先查询 
	public void staffActionEdit(Staff staff) {
		// TODO Auto-generated method stub
		//1,查询数据库中原始数据
		Staff staf=staffMapper.selectByPrimaryKey(staff.getId());
		String deltag=staf.getDeltag();
		//再根据页面提交的数据进行覆盖
		staf=staff;
		staf.setDeltag(deltag);
		//2,更新
		staffMapper.updateByPrimaryKeySelective(staf);
		
	}

	public List<Staff> findListNotDelete() {
		// TODO Auto-generated method stub
		
		StaffExample example= new StaffExample();
		Criteria criteria=example.createCriteria();
		criteria.andDeltagEqualTo("0");
		return staffMapper.selectByExample(example);
	}

}

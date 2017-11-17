package com.atguigu.bos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bos.bean.Decidedzone;
import com.atguigu.bos.bean.Staff;
import com.atguigu.bos.bean.Subarea;
import com.atguigu.bos.dao.DecidedzoneMapper;
import com.atguigu.bos.dao.SubareaMapper;

@Service
public class DecidedzoneService {
    @Autowired
    DecidedzoneMapper decidedzoneMapper;
    @Autowired
	SubareaMapper subareaMapper;
	public void save(Decidedzone decidedzone, String[] subareaid) {
		
		String decidedzoneId=decidedzone.getId();
		String name=decidedzone.getName();
		String staffId=decidedzone.getStaff().getId();
		
		decidedzoneMapper.insertWithStaffId(decidedzoneId, name, staffId);
		for (String sid : subareaid) {
			subareaMapper.updateByPrimaryKeySelectiveWithDecideId(decidedzoneId, sid);
		}
		
	}
	public List<Decidedzone> getAll() {
		return decidedzoneMapper.selectByExampleWithStaff(null);
	}

}

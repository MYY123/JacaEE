package com.atguigu.bos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bos.bean.Region;
import com.atguigu.bos.bean.RegionExample;
import com.atguigu.bos.bean.RegionExample.Criteria;
import com.atguigu.bos.dao.RegionMapper;
@Service
public class RegionService {
    @Autowired
	RegionMapper regionMapper;
	public void saveBatch(List<Region> list) {
		for (Region region : list) {
			regionMapper.insertSelective(region);
		}
	}
	public List<Region> getAll() {
		return regionMapper.selectByExample(null);
	}
	public List<Region> findAll() {
		return regionMapper.selectByExample(null);
	}
	public List<Region> findByQ(String q) {
		RegionExample example =new RegionExample();
		
		Criteria criteria= example.createCriteria();
		criteria.andProvinceLike("%"+q+"%");
		
		Criteria criteria2= example.createCriteria();
		criteria2.andCityLike("%"+q+"%");
		
	    Criteria criteria3= example.createCriteria();
		criteria3.andDistrictLike("%"+q+"%");
		
		example.or(criteria2);
		example.or(criteria3);
		
		List<Region> list2=regionMapper.selectByExample(example);
		return list2;
	}

}

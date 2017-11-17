package com.atguigu.bos.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bos.bean.Region;
import com.atguigu.bos.bean.SubareaExample;
import com.atguigu.bos.bean.SubareaExample.Criteria;
import com.atguigu.bos.bean.Subarea;
import com.atguigu.bos.dao.SubareaMapper;

@Service
public class SubareaService {
	@Autowired
	SubareaMapper subareaMapper;
	public void add(Subarea subarea) {
		subareaMapper.insertSelective(subarea);
		
	}
	public List<Subarea> pageQuery(Subarea subarea) {
		String addresskey = subarea.getAddresskey();
		Region region = subarea.getRegion();
		
		SubareaExample example =new SubareaExample();
	
		if (StringUtils.isNotBlank(addresskey)) {
			// 按照地址关键字模糊查询
			Criteria criteria=example.createCriteria();
			criteria.andAddresskeyLike("%"+addresskey+"%");
		}

		/*if (region != null) {
			
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();

			if (StringUtils.isNotBlank(province)) {
				// 按照省进行模糊查询
				Criteria criteria2=example.createCriteria();
				criteria2.
				//andCityLike("%"+province+"%");
			}
			if (StringUtils.isNotBlank(city)) {
				// 按照市进行模糊查询
					criteria3.andDistrictLike("%"+city+"%");
			}
			if (StringUtils.isNotBlank(district)) {
				// 按照省进行模糊查询
					criteria4.andDistrictLike("%"+district+"%");
			}
		}*/
		
		
			return subareaMapper.selectByExampleWithRegion(example);
	}
	public List<Subarea> findAll() {
		// TODO Auto-generated method stub
		return subareaMapper.selectByExampleWithRegion(null);
	}
	
	
	
	public List<Subarea> findListNotAssociation() {
		SubareaExample example =new SubareaExample();
		Criteria criteria=example.createCriteria();
		criteria.andDecidedzoneIdIsNull();
		return subareaMapper.selectByExample(example);
	}

}

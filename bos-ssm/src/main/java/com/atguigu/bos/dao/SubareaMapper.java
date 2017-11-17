package com.atguigu.bos.dao;

import com.atguigu.bos.bean.Subarea;
import com.atguigu.bos.bean.SubareaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubareaMapper {
    long countByExample(SubareaExample example);

    int deleteByExample(SubareaExample example);

    int deleteByPrimaryKey(String id);

    int insert(Subarea record);

    int insertSelective(Subarea record);

    List<Subarea> selectByExample(SubareaExample example);

    Subarea selectByPrimaryKey(String id);
  //带有定区信息的查询
    List<Subarea> selectByExampleWithDecidezone(SubareaExample example);

    Subarea selectByPrimaryKeyWithDecidezone(String id);
    
    //带有区域信息的查询
    List<Subarea> selectByExampleWithRegion(SubareaExample example);

    Subarea selectByPrimaryKeyWithRegion(String id);

    int updateByExampleSelective(@Param("record") Subarea record, @Param("example") SubareaExample example);

    int updateByExample(@Param("record") Subarea record, @Param("example") SubareaExample example);

    int updateByPrimaryKeySelective(Subarea record);
    
    int updateByPrimaryKeySelectiveWithDecideId(@Param("decidedzoneId") String decidedzoneId, @Param("subareaid") String subareaid);

    int updateByPrimaryKey(Subarea record);
}
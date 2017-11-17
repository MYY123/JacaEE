package com.atguigu.bos.dao;

import com.atguigu.bos.bean.Region;
import com.atguigu.bos.bean.RegionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RegionMapper {
    long countByExample(RegionExample example);

    int deleteByExample(RegionExample example);

    int deleteByPrimaryKey(String id);

    int insert(Region record);

    int insertSelective(Region record);

    List<Region> selectByExample(RegionExample example);

    Region selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Region record, @Param("example") RegionExample example);

    int updateByExample(@Param("record") Region record, @Param("example") RegionExample example);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);
}
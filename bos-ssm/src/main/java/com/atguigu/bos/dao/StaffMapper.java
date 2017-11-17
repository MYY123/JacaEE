package com.atguigu.bos.dao;

import com.atguigu.bos.bean.Staff;
import com.atguigu.bos.bean.StaffExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

public interface StaffMapper {
    long countByExample(StaffExample example);

    int deleteByExample(StaffExample example);

    int deleteByPrimaryKey(String id);

    int insert(Staff record);

    int insertSelective(Staff record)throws DataAccessException;

    List<Staff> selectByExample(StaffExample example);

    Staff selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Staff record, @Param("example") StaffExample example);

    int updateByExample(@Param("record") Staff record, @Param("example") StaffExample example);

    int updateByPrimaryKeySelective(Staff record);

    int updateByPrimaryKey(Staff record);
}
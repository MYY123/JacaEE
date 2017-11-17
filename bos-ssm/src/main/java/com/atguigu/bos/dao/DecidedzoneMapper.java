package com.atguigu.bos.dao;

import com.atguigu.bos.bean.Decidedzone;
import com.atguigu.bos.bean.DecidedzoneExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DecidedzoneMapper {
    long countByExample(DecidedzoneExample example);

    int deleteByExample(DecidedzoneExample example);

    int deleteByPrimaryKey(String id);

    int insert(Decidedzone record);
    //插入时，插入员工id
    int insertWithStaffId(@Param("id") String id,@Param("name") String name,@Param("staffId") String staffId);

    int insertSelective(Decidedzone record);
    
    //插入式，插入员工id
    int insertSelectiveWithStaffId(Decidedzone record);

    List<Decidedzone> selectByExample(DecidedzoneExample example);
    //查询时，带有员工信息
    List<Decidedzone> selectByExampleWithStaff(DecidedzoneExample example);

    Decidedzone selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Decidedzone record, @Param("example") DecidedzoneExample example);

    int updateByExample(@Param("record") Decidedzone record, @Param("example") DecidedzoneExample example);

    int updateByPrimaryKeySelective(Decidedzone record);

    int updateByPrimaryKey(Decidedzone record);
}
package com.zlh.mybatis.bean;

import org.apache.ibatis.annotations.Select;

public interface EmployeeMapperAnnotation{

    @Select("select * from t_employee where id = #{id}")
    Employee getEmpById(Integer id);
}

package com.zlh.mybatis.dao;

import com.zlh.mybatis.bean.Employee;

public interface EmployeeMapper {

    Employee getEmpById(Integer id);
}

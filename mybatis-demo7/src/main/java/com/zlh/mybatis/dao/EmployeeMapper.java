package com.zlh.mybatis.dao;

import com.zlh.mybatis.bean.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper {

    Employee geEmpById(Integer id);

    List<Employee> getEmps();
}

package com.zlh.mybatis.dao;

import com.zlh.mybatis.bean.Employee;

import java.util.List;

public interface EmployeeMapper {

     Employee getEmpById(Integer id);

     List<Employee> getEmps();

     void addEmp(Employee employee);


}

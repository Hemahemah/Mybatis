package com.zlh.mybatis.dao;

import com.zlh.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapperDynamicSQL {

    List<Employee> getEmpsByConditionIf(Employee employee);

    List<Employee> getEmpsByConditionTrim(Employee employee);

    List<Employee> getEmpsByConditionChoose(Employee employee);

    void updateEmp(Employee employee);

    List<Employee> getEmpsByConditionForeach(@Param("ids") List<Integer> ids);

    void addEmps(@Param("emps") List<Employee> emps);

    List<Employee> getEmpsInnerParameter(Employee employee);
}

package com.zlh.mybatis.bean;


import java.util.List;

public interface EmployeePlusMapper {

    Employee getEmpById(Integer id);

    Employee getEmpAndDeptById(Integer id);

    Employee getEmpByIdStep(Integer id);

    List<Employee> getEmpsByDeptId(Integer deptId);
}

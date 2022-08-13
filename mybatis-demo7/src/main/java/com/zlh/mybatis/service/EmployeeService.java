package com.zlh.mybatis.service;

import com.zlh.mybatis.bean.Employee;
import com.zlh.mybatis.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> getEmps(){
       return  employeeMapper.getEmps();
    }
}

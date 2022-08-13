package com.zlh.test.mybatisTest;

import com.zlh.mybatis.bean.Department;
import com.zlh.mybatis.bean.DepartmentMapper;
import com.zlh.mybatis.bean.Employee;
import com.zlh.mybatis.bean.EmployeePlusMapper;
import junit.framework.TestCase;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EmployeePlusMapperTest {


    @Test
    public void getEmpById() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        EmployeePlusMapper plusMapper = openSession.getMapper(EmployeePlusMapper.class);
        Employee emp = plusMapper.getEmpById(3);
        System.out.println(emp);
        openSession.close();
    }

    @Test
    public void getEmpAndDeptById() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        EmployeePlusMapper plusMapper = openSession.getMapper(EmployeePlusMapper.class);
        Employee emp = plusMapper.getEmpAndDeptById(3);
        System.out.println(emp);
        System.out.println(emp.getDept());
        openSession.close();
    }

    @Test
    public void getEmpByIdStep() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        EmployeePlusMapper plusMapper = openSession.getMapper(EmployeePlusMapper.class);
        Employee employee = plusMapper.getEmpByIdStep(3);
        System.out.println(employee.getLastName());
      //  System.out.println(employee.getDept());
        openSession.close();
    }

    @Test
    public void getDeptByIdPlus() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            DepartmentMapper plusMapper = openSession.getMapper(DepartmentMapper.class);
            Department dept = plusMapper.getDeptByIdPlus(1);
            System.out.println(dept);
            dept.getEmployees().forEach(System.out::println);
        }
    }

    @Test
    public void getDeptByIdStep() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            DepartmentMapper plusMapper = openSession.getMapper(DepartmentMapper.class);
            Department dept = plusMapper.getDeptByIdStep(1);
            System.out.println(dept.getDepartmentName());
            dept.getEmployees().forEach(System.out::println);
        }
    }

    @Test
    public void discriminator() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeePlusMapper mapper = openSession.getMapper(EmployeePlusMapper.class);
            Employee employee = mapper.getEmpAndDeptById(3);
            System.out.println(employee);
            System.out.println(employee.getDept());
        }
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

}
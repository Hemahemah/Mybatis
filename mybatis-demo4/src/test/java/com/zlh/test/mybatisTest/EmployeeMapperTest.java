package com.zlh.test.mybatisTest;

import com.zlh.mybatis.bean.Employee;
import com.zlh.mybatis.bean.EmployeeMapper;
import junit.framework.TestCase;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class EmployeeMapperTest  {

    @Test
    public void testGetEmpByLastNameLike() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
        List<Employee> employees = employeeMapper.getEmpByLastNameLikeReturnList("%j%");
        employees.forEach(System.out::println);
        openSession.close();
    }

    @Test
    public void getEmpByIdReturnMap() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
        Map<String, Object> returnMap = employeeMapper.getEmpByIdReturnMap(3);
        System.out.println(returnMap);
        openSession.close();
        /*returnMap.forEach((k,v)-> System.out.println(k+"="+v));*/
    }

    @Test
    public void getEmpByLastNameLikeReturnMap() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
        Map<Integer, Employee> returnMap = employeeMapper.getEmpByLastNameLikeReturnMap("%j%");
        System.out.println(returnMap);
        openSession.close();
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}
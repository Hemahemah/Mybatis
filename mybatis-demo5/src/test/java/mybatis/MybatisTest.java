package mybatis;

import com.zlh.mybatis.bean.Department;
import com.zlh.mybatis.bean.Employee;
import com.zlh.mybatis.dao.EmployeeMapper;
import com.zlh.mybatis.dao.EmployeeMapperDynamicSQL;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MybatisTest {
    @Test
    public void connect() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.geEmpById(3);
            System.out.println(employee);
        }
    }

    @Test
    public void getEmpsByConditionIf() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> emps = mapper.getEmpsByConditionIf(new Employee(null, "%j%", null, null));
            emps.forEach(System.out::println);
        }
    }

    @Test
    public void getEmpsByConditionTrim() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> emps = mapper.getEmpsByConditionTrim(new Employee(null, "%j%", null, null));
            emps.forEach(System.out::println);
        }
    }

    @Test
    public void getEmpsByConditionChoose() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> emps = mapper.getEmpsByConditionChoose(new Employee(null, null, null, null));
            emps.forEach(System.out::println);
        }
    }

    @Test
    public void updateEmp() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            mapper.updateEmp(new Employee(3, "yo", null, null));
        }
    }

    @Test
    public void getEmpsByConditionForeach() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> employees = mapper.getEmpsByConditionForeach(Arrays.asList(1,2,3,4));
            employees.forEach(System.out::println);
        }
    }

    @Test
    public void addEmps() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> employees = new ArrayList<>();
            employees.add(new Employee(null,"smith","smith@123.com","1",new Department(1,null)));
            employees.add(new Employee(null,"allen","allen@123.com","0",new Department(2,null)));
            employees.add(new Employee(null,"user","user@123.com","1",new Department(2,null)));
            mapper.addEmps(employees);
            openSession.commit();
        }
    }

    @Test
    public void getEmpsInnerParameter() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> employees = mapper.getEmpsInnerParameter(new Employee(null,"j",null,null,null));
            employees.forEach(System.out::println);
        }
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}

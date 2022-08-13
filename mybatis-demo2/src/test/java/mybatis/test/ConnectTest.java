package mybatis.test;


import com.zlh.mybatis.bean.Employee;
import com.zlh.mybatis.bean.EmployeeMapper;
import com.zlh.mybatis.bean.EmployeeMapperAnnotation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ConnectTest {
    @Test
    public void connectByXmlTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(employee);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void connectByAnnotation() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession openSession = sqlSessionFactory.openSession();
        EmployeeMapperAnnotation employeeMapperAnnotation = openSession.getMapper(EmployeeMapperAnnotation.class);
        Employee employee = employeeMapperAnnotation.getEmpById(1);
        System.out.println(employee);

    }

}

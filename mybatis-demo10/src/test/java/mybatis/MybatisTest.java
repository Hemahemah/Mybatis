package mybatis;

import com.zlh.mybatis.bean.EmpStatus;
import com.zlh.mybatis.bean.Employee;
import com.zlh.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MybatisTest {



    @Test
    public void getEmpById() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            System.out.println(mapper);
            Employee employee = mapper.getEmpById(3);
            System.out.println(employee);
        }
    }

    @Test
    public void testEnumUse(){
        EmpStatus login = EmpStatus.LOGIN;
        System.out.println("枚举的索引:"+login.ordinal()+",枚举名字:"+login.name());
        System.out.println("状态码:"+login.getCode()+",信息:"+login.getMsg());
    }

    /**
     * Mybatis默认保存枚举类型的名字(EnumTypeHandler)
     * 改变使用:EnumOrdinalTypeHandler
     */
    @Test
    public void testEnum() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = new Employee(null, "test_enum", "enum@123.com", "1");
            /*mapper.addEmp(employee);*/
            Employee emp = mapper.getEmpById(129);
            System.out.println(emp.getEmpStatus());
            openSession.commit();
        }
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);

    }
}

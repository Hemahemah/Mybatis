package mybatis;

import com.zlh.mybatis.bean.Employee;
import com.zlh.mybatis.bean.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlTest {

    /**
     * 1.Mybatis允许增删改直接定义以下返回值类型
     *      Integer,Long,Boolean,void
     * 2.数据需要手动提交:sqlSessionFactory.openSession();或sqlSessionFactory.openSession(true)可自动提交
     */
    @Test
    public void addEmp() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();//默认为手动提交数据
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = new Employee(null, "jerry", "jerry@123.com", "1");
            mapper.addEmp(employee);
            System.out.println(employee.getId());
            openSession.commit();//提交
        } finally {
            openSession.close();
        }
    }
    /**
     * Mybatis参数处理
     * 1.单个参数,mybatis不会做特殊处理
     *      #{参数名},取出参数值
     * 2.多个参数,做特殊处理,多个参数会被封转为map,#{}从map中获取指定的key值
     *      1).多个参数使用命名参数:明确指定封装参数时的map的key
     *          使用@Param注解指定key的值
     *      2).参数正好时POJO的数据模型,可以直接传入POJO
     *      3).如果这个参数不是业务模型中的数据,可以直接传入map
     *          #{key}取出map对应的值
     *      4).如果这个参数不是业务模型中的数据,且经常使用,编写一个TO(Transfer Object)数据传输对象
     *          如分页处理Page对象
     *      5).Employee getEmp( @Param("id") Integer id,String lastName )
     *          取值:id ---> #{id/param1}  lastName ---> #{param2}
     *         Employee getEmp( Integer id,Employee employee )
     *          取值:id ---> #{param1}  lastName ---> #{param2.lastName}
     *         Employee getEmp( List<Integer> ids )
     *          取值:取出第一个id值: #{list[0]}
     *          如果是Collection类型或者数组类型也会特殊处理,将此类型封装为Map中
     *          key: Collection ---> collection List ---> list 数组 ---> array
     */
    @Test
    public void getEmpByMap() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();//默认为手动提交数据
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            /*Employee employee = mapper.getEmpByIdAndLastName(3,"jack");*/
            Map<String,Object> map = new HashMap<>();
            map.put("id",3);
            map.put("lastName","jack");
            Employee employee = mapper.getEmpByMap(map);
            System.out.println(employee);
            openSession.commit();//提交
        } finally {
            openSession.close();
        }
    }
    @Test
    public void getEmpByIdAndLastName() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();//默认为手动提交数据
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpByIdAndLastName(3,"jack");
            System.out.println(employee);
            openSession.commit();//提交
        } finally {
            openSession.close();
        }
    }


    @Test
    public void update() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {//默认为手动提交数据
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            boolean update = mapper.update(new Employee(3, "jack", "jack@123.com", "1"));
            System.out.println(update);
            openSession.commit();//提交
        }
    }

    @Test
    public void deleteEmpById() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);//默认为手动提交数据
            mapper.deleteEmpById(1);
            openSession.commit();//提交
        }
    }

    @Test
    public void selectEmp() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession session = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
            List<Integer> list = List.of(3,5,7,8);
            Employee employee = employeeMapper.selectEmp(list, new Employee(null, "%k%", null, null));
            System.out.println(employee);
        }
    }




    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}

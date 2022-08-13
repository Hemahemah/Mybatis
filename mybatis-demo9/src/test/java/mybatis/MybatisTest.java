package mybatis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlh.mybatis.bean.Employee;
import com.zlh.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class MybatisTest {

    /**
     * 插件原理
     *  在四大对象创建的时候
     *      1.每个创建出来的对象不是直接返回的,而是
     *          interceptorChain.pluginAll(...)
     *      2.获取到所有的Interceptor(拦截器)(插件需要的接口),调用interceptor.plugin(target),返回target包装对象
     *      3.插件机制,可以使用插件为目标对象创建一个代理对象:AOP(面向切面)
     *        插件可以为四大对象创建出代理对象
     *        代理对象可以拦截到四大对象的每一个执行
     *      public Object pluginAll(Object target) {
     *     for (Interceptor interceptor : interceptors) {
     *       target = interceptor.plugin(target);
     *     }
     *     return target;
     *   }
     *
     * 插件编写步骤
     *  1.编写Interceptor实现类
     *  2.使用@Intercepts注解完成插件签名
     *  3.将写好的插件注册到全局配置文件中
     */
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
    public void getEmps() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Page<Object> page = PageHelper.startPage(2, 5);
            List<Employee> emps = mapper.getEmps();
            PageInfo<Employee> info = new PageInfo<>(emps,4);
            emps.forEach(System.out::println);
           /* System.out.println("当前页码:"+page.getPageNum());
            System.out.println("总记录数:"+page.getTotal());
            System.out.println("每页记录数:"+page.getPageSize());
            System.out.println("总页码:"+page.getPages());*/
            System.out.println("当前页码:"+info.getPageNum());
            System.out.println("总记录数:"+info.getTotal());
            System.out.println("每页记录数:"+info.getPageSize());
            System.out.println("总页码:"+info.getPages());
            System.out.println("是否第一页:"+info.isIsFirstPage());
            System.out.println("是否最后一页:"+info.isIsLastPage());
            int[] navigatepageNums = info.getNavigatepageNums();
            for (int num : navigatepageNums) {
                System.out.println(num);
            }
        }

    }

    @Test
    public void addEmp() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //可以执行批量操作的sqlSession
        try (SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
                for (int i = 0;i<100;i++){
                    mapper.addEmp(new Employee(null,UUID.randomUUID().toString().substring(0,4),"b","1"));
                }
                openSession.commit();
        }
    }


    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);

    }

}

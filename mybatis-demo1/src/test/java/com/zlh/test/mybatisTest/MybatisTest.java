package com.zlh.test.mybatisTest;

import com.zlh.mybatis.bean.Employee;
import com.zlh.mybatis.bean.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {

    /**
     * 1.根据xml配置文件(全局配置文件)创建一个SqlSessionFactory对象
     * 2.sql映射文件,配置每一个sql,以及sql的封装规则等
     * 3.将sql映射文件注册在全局配置文件中
     * 4.代码:
     *      1).通过全局配置文件得到SqlSessionFactory对象
     *      2).使用SqlSessionFactory工厂获取到SqlSession对象来进行增删改查
     *      3).使用sql的唯一标识告诉Mybatis执行哪个sql语句(sql保存在映射文件中),唯一标识一般使用namespace+id的形式
     *      4).一个SqlSession对象代表和数据库的一次会话,使用完需进行关闭
     */
    @Test
    public void mybatisTest()  {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession session = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
             session = sqlSessionFactory.openSession();//获取session实例,能直接执行已经映射的sql语句
            Object selectEmp = session.selectOne("com.zlh.mybatis.EmployeeMapper.selectEmp", 1);//1:sql的唯一标识; 2:执行sql要用的参数
            Employee employee = (Employee) selectEmp;
            System.out.println(employee);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (session!=null){
                session.close();
            }
        }
    }

    /**
     * 1.接口式编程
     *      原生:      Dao ---> DaoImpl
     *      Mybatis:  Mapper ---> xxMapper.xml
     * 2.SqlSession代表和数据库的一次会话,用完进行关闭
     * 3.SqlSession和connection一样不是线程安全,每次使用都需要获取新的对象,不能作为成员变量
     * 4.mapper接口没有实现类,但是mybatis会为这个接口生成一个代理对象(将接口与xml进行绑定):
     *      EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
     * 5.两个重要的配置文件:
     *      1).mybatis的全局配置文件,包含数据库连接池信息,事务管理器信息,系统运行环境信息等
     *      2).sql映射文件,包含了每个sql语句的映射信息,将sql语句抽取出来
     * 6.步骤
     *      1.获取SqlSessionFactory对象
     *      2.获取SqlSession对象
     *      3.获取接口的实现类（Mybatis会为接口创建一个代理对象,代理对象去执行增啥改查）
     *      4.调用接口的方法
     */
    @Test
    public void mybatisTest2() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(3);
            System.out.println(employee);
        }
    }

    /**
     * 流程
     *  1.获取sqlSessionFactory对象
     *      解析文件的每一个信息保存在Configuration中,返回包含Configuration的DefaultSqlSessionFactory
     *      MappedStatement代表一个增删改查的详细信息
     *  2.获取sqlSession对象
     *      返回DefaultSqlSession对象,包含Executor和Configuration
     *      这一步会创建Executor对象
     *  3.获取接口的代理对象(MapperProxy)
     *      使用MapperProxyFactory创建一个MapperProxy的代理对象
     *      代理对象包含DefaultSqlSession(Executor)
     *  4.执行增删改查的方法
     *
     * 总结:
     *  1.根据配置文件(全局,sql映射)初始化Configuration对象
     *  2.创建一个DefaultSqlSession对象,里面包含Configuration以及Executor(根据配置文件中的defaultExecutorType创建)
     *  3.DefaultSqlSession.getMapper(),拿到Mapper接口对应的MapperProxy
     *  4.MapperProxy有DefaultSqlSession
     *  5.执行增删改查的方法:
     *      1).调用DefaultSqlSession的增删改查(Executor)
     *      2).创建一个StatementHandler对象,同时创建出ParameterHandler与ResultSetHandler
     *      3).使用ParameterHandler预编译参数以及设置参数值
     *      4).调用StatementHandler的增删改查方法
     *      5).使用ResultSetHandler封装结果
     *         四大对象创建的时候都有一个interceptor.pluginAll(parameterHandler)
     *
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


    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);

    }
}

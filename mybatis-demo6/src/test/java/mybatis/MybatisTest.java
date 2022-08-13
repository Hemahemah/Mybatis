package mybatis;

import com.zlh.mybatis.bean.Employee;
import com.zlh.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {

    /**
     * 缓存机制
     *  1.一级缓存(本地缓存):sqlSession级别的缓存(Map),是一直开启的无法关闭
     *      与数据库同一次会话期间查询到的数据放在本地缓存中
     *      以后如果需要获取相同的数据,直接从缓存里拿,不需要查询数据库
     *      一级缓存失效情况:没有使用到当前一级缓存的情况,即还需要向数据库发出查询
     *          1).sqlSession不同
     *          2).sqlSession相同但条件不同(当前一级缓存中没有这个数据)
     *          3).sqlSession相同,两次查询之间执行了增删改操作(操作可能对数据有影响)
     *          4).sqlSession相同,手动清除了一级缓存(clearCache())
     *
     *  2.二级缓存(全局缓存):基于namespace级别的缓存,一个namespace对应一个二级缓存
     *      工作机制:
     *          1).一个会话查询一条数据,这个数据就会被放在当前会话的一级缓存中;
     *          2).如果会话关闭,一级缓存中的数据会被保存在二级缓存中,新的会话查询信息,就可以参照二级缓存
     *          3).sqlSession==EmployeeMapper==>Employee
     *                         DepartmentMapper==>Department
     *             不同的namespace查出的数据会放在自己对应的缓存中(map)
     *          4).查出的数据都会被默认放在一级缓存中,只有当会话提交或者关闭后,一级缓存中的数据才会转移到二级缓存中
     *      使用:
     *          1).开启全局缓存配置 <setting name="cacheEnabled" value="true"/>
     *          2).去mapper.xml中配置使用二级缓存 <cache/>
     *          3).POJO需要实现序列化接口
     *
     *  3.和缓存有关的设置和属性
     *      1).cacheEnabled=true,false,关闭缓存(二级缓存关闭,一级缓存一直可用)
     *      2).每个select标签都有useCache=true,false关闭缓存(不使用二级缓存)
     *      3).增删改标签都有flushCache=true(查询标签默认为false),增删改执行完毕后清空缓存(一级缓存与二级缓存都被清空)
     *      4).sqlSession.clearCache():清除当前session一级缓存
     *      5).localCacheScope:本地缓存作用域(一级缓存SESSION:当前会话的所有数据保存在会话缓存中;STATEMENT:可以禁用一级缓存 )
     */

    @Test
    public void firstLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession session = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(3);
            System.out.println(employee);
            Employee employee02 = mapper.getEmpById(3);
            System.out.println(employee02);
            System.out.println(employee == employee02);
        }
    }

    @Test
    public void secondLevelCache() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession1 = sqlSessionFactory.openSession();
        SqlSession openSession2 = sqlSessionFactory.openSession();

            EmployeeMapper mapper1 = openSession1.getMapper(EmployeeMapper.class);
            EmployeeMapper mapper2 = openSession2.getMapper(EmployeeMapper.class);
            Employee employee1 = mapper1.getEmpById(3);
            System.out.println(employee1);
            openSession1.close();
            Employee employee2 = mapper2.getEmpById(3);
            System.out.println(employee2);
            openSession2.close();
        System.out.println(employee1==employee2);


    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}

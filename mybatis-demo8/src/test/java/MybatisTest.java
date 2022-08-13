import com.zlh.mybatis.bean.Employee;
import com.zlh.mybatis.bean.EmployeeExample;
import com.zlh.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MybatisTest {
    @Test
    public void testMbg() throws XMLParserException, IOException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        File configFile = new File("src/main/resources/mbg.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);
    }

    @Test
    public void testMybatis3() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try(SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
            List<Employee> employees = employeeMapper.selectByExample(null);
            employees.forEach(System.out::println);
            //查询员工名字带e字母和性别是1的
            System.out.println("=====================");
            EmployeeExample example = new EmployeeExample();
            EmployeeExample.Criteria criteria = example.createCriteria();
            criteria.andLastNameLike("%e%");
            criteria.andGenderEqualTo("1");

            EmployeeExample.Criteria criteria1 = example.createCriteria();
            criteria1.andEmailLike("%e%");
            example.or(criteria1);
            List<Employee> employeeList = employeeMapper.selectByExample(example);
            employeeList.forEach(System.out::println);
        }
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}

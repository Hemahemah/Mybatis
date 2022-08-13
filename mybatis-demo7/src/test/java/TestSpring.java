import com.zlh.mybatis.bean.Employee;
import com.zlh.mybatis.dao.EmployeeMapper;
import com.zlh.mybatis.service.EmployeeService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestSpring {
    @Test
    public void testSpring(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        EmployeeService service = (EmployeeService) context.getBean("employeeService");
        System.out.println(service);
    }

    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        EmployeeService service = (EmployeeService) context.getBean("employeeService");
        List<Employee> emps = service.getEmps();
        emps.forEach(System.out::println);
    }
}

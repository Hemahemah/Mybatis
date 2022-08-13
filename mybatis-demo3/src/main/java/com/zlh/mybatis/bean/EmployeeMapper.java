package com.zlh.mybatis.bean;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

     Employee getEmpById(Integer id);

     void addEmp(Employee employee);

     boolean update( Employee employee);

     void deleteEmpById(Integer id);

     Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

     Employee getEmpByMap(Map<String,Object> map);

     Employee selectEmp( @Param("ids") List<Integer> ids,@Param("emp")  Employee employee);

}

package com.zlh.mybatis.bean;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

     /**
      * 返回list集合
      */
     List<Employee> getEmpByLastNameLikeReturnList(String lastName);

     /**
      * 返回Map集合,key为列名,值为列所对应的值
      */
     Map<String,Object> getEmpByIdReturnMap(Integer id);

     /**
      *  多条记录封装为map,key为这条记录的主键,value为对象
      *  MapKey注解设置map中的key属性
      */

     @MapKey("id")
     Map<Integer,Employee> getEmpByLastNameLikeReturnMap(String lastName);

}

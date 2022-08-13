package com.zlh.mybatis.bean;

public interface DepartmentMapper {

    Department getDeptById(Integer id);

    Department getDeptByIdPlus(Integer id);

    Department getDeptByIdStep(Integer id);

}

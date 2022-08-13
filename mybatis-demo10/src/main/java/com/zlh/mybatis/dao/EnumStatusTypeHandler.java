package com.zlh.mybatis.dao;

import com.zlh.mybatis.bean.EmpStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现TypeHandler接口或继承BaseTypeHandler
 */
public class EnumStatusTypeHandler implements TypeHandler<EmpStatus> {
    /**
     * 定义当前数据如何保存进数据库中
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, EmpStatus parameter, JdbcType jdbcType) throws SQLException {
        System.out.println("要保存的状态码:"+parameter.getCode());
        ps.setString(i,parameter.getCode().toString());
    }

    @Override
    public EmpStatus getResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        //从数据库中拿到状态码返回枚举对象
        System.out.println("从数据库中获取的状态码:"+code);
        EmpStatus status = EmpStatus.getEmpStatusByCode(code);
        return status;
    }

    @Override
    public EmpStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        //从数据库中拿到状态码返回枚举对象
        System.out.println("从数据库中获取的状态码:"+code);
        EmpStatus status = EmpStatus.getEmpStatusByCode(code);
        return status;
    }

    @Override
    public EmpStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        //从数据库中拿到状态码返回枚举对象
        System.out.println("从数据库中获取的状态码:"+code);
        EmpStatus status = EmpStatus.getEmpStatusByCode(code);
        return status;
    }
}

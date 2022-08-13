package com.zlh.mybatis.dao;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.Properties;

@Intercepts(
        {
                @Signature(type = StatementHandler.class,method = "parameterize",args = {Statement.class})
        }
)
public class MySecondPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MySecondPlugin的intercept方法:"+invocation.getMethod());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("MySecondPlugin的plugin方法,将要包装的对象:"+target);
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}

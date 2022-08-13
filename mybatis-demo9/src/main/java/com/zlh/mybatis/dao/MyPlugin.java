package com.zlh.mybatis.dao;


import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Statement;
import java.util.Properties;

/**
 * 完成插件签名:告诉Mybatis插件用于拦截哪个对象的哪个方法
 */
@Intercepts(
        {
                @Signature(type = StatementHandler.class,method = "parameterize",args = {Statement.class})
        }
)
public class MyPlugin implements Interceptor {

    /**
     * 拦截目标对象方法的执行
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println("MyPlugin的intercept方法:"+invocation.getMethod());
        System.out.println("当前拦截的对象:"+invocation.getTarget());
        Object target = invocation.getTarget();
        //StatementHandler-->ParameterHandler-->paramObject
        //拿到target的源数据
        MetaObject metaObject = SystemMetaObject.forObject(target);
        //获取属性值
        Object value = metaObject.getValue("parameterHandler.parameterObject");
        System.out.println("sql语句使用的参数为:"+value);
        metaObject.setValue("parameterHandler.parameterObject",11);
        Object proceed = invocation.proceed();//执行目标方法
        return proceed;//返回执行后的返回值
    }

    /**
     * 包装目标对象,为目标对象创建代理对象
     */
    @Override
    public Object plugin(Object target) {
        //使用Plugin类的wrap方法来包装目标对象
        Object wrap = Plugin.wrap(target, this);
        System.out.println("MyPlugin的plugin方法,将要包装的对象:"+target);
        return wrap;
    }

    /**
     * 将插件注册时的property属性设置进来
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("MyPlugin的setProperties方法"+properties);

    }
}

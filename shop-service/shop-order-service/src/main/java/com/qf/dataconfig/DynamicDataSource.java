package com.qf.dataconfig;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/24 20:58
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static ThreadLocal<String> threadLocal = new ThreadLocal();

    public static void set(String dataSourceKeyword){
        threadLocal.set(dataSourceKeyword);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("获得动态数据源的关键字："+threadLocal.get());
        return threadLocal.get();
    }
}

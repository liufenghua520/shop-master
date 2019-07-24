package com.qf.datasources_demo.datasource;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/23 17:08
 */
public class DataSourceSelector {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setLocal(String local) {
        threadLocal.set(local);
    }

    public static String getLocal(){
        return threadLocal.get();
    }
}

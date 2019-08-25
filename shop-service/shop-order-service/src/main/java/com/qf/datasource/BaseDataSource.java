package com.qf.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;

import javax.sql.DataSource;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/24 20:49
 */
@Data
public class BaseDataSource {

    protected String url;
    protected String username;
    protected String password;
    protected String driverClassName;
    protected String keyword;

    public DataSource getDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName(driverClassName);
        return hikariDataSource;
    }
}

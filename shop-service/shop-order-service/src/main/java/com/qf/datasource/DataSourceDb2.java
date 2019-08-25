package com.qf.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/24 20:54
 */
@Component
@ConfigurationProperties(prefix = "spring.orderdb2.datasource")
public class DataSourceDb2 extends BaseDataSource {
}

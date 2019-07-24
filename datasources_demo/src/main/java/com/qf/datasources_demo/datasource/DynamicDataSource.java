package com.qf.datasources_demo.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/23 11:57
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceSelector.getLocal();
    }
}

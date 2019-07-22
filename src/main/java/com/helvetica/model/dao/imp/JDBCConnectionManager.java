package com.helvetica.model.dao.imp;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

class JDBCConnectionManager {

    private static volatile DataSource dataSource;

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        URL = resourceBundle.getString("database.url");
        USER = resourceBundle.getString("database.user");
        PASSWORD = resourceBundle.getString("database.password");
    }

    static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (JDBCConnectionManager.class) {
                if (dataSource == null) {
                    PGSimpleDataSource ds = new PGSimpleDataSource();
                    ds.setUrl(URL);
                    ds.setUser(USER);
                    ds.setPassword(PASSWORD);
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }
}

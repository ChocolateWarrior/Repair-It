package com.helvetica.model.dao.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class TransactionalFactory extends JDBCDaoFactory {

    private Connection connection;

    @Override
    public Connection getConnection(){
        if(Objects.isNull(connection)) {
            try {
                connection = JDBCConnectionManager.getDataSource().getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return connection;
    }


    public void begin() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollback() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

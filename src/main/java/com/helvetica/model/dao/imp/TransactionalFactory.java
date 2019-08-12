package com.helvetica.model.dao.imp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class TransactionalFactory extends JDBCDaoFactory {

    private Connection transactionalConnection;

    public Connection getTrancationalConnection(){
        if(Objects.isNull(transactionalConnection)) {
            try {
                transactionalConnection = JDBCConnectionManager.getDataSource().getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return transactionalConnection;
    }

    public void begin() throws SQLException {
        transactionalConnection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        transactionalConnection.commit();
        transactionalConnection.setAutoCommit(true);
    }

    public void rollback() throws SQLException {
        transactionalConnection.rollback();
        transactionalConnection.setAutoCommit(true);
    }

    public void close() throws SQLException {
        transactionalConnection.close();
    }

}

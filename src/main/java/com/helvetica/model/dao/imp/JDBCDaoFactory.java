package com.helvetica.model.dao.imp;

import com.helvetica.model.dao.DaoFactory;
import com.helvetica.model.dao.UserDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    private Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/repair_it_jsp_db",
                    "helvetica" ,
                    "marchik310500" );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

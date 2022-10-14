package com.htn.mmt.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection conn = null;

    private Database() {

    }

    public static void connect(String url, String username, String password) throws SQLException {
        conn = DriverManager.getConnection(url, username, password);
    }

    public static Connection getConnection() {
        return conn;
    }

}

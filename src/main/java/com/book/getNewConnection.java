package com.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class getNewConnection {
    public static Connection return_connection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticketBookingMain", "root", "");
            return con;
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return con;
    }
}

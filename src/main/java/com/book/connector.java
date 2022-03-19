package com.book;

import java.sql.Connection;
import java.sql.DriverManager;

public class connector {

    public static Connection con;

    public static Connection return_connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticketBookingApp", "root", "");
            return con;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

}

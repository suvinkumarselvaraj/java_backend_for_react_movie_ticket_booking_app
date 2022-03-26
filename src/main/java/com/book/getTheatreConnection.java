package com.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class getTheatreConnection {
    public static Connection con;
    public static Connection return_theatre_connection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticketBookingMain","root","");
        return con;
    }
    
}

package com.book;


import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class testingTicket extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        res.addHeader("Access-Control-Allow-Headers", "*");
        res.addHeader("Access-Control-Allow-Origin", "*");
        String user = req.getParameter("username");
        Integer count = 0;

        String query = "SELECT COUNT(*) FROM tickets WHERE username='" + user;
        try {
            Statement st;
            Connection con = connector.return_connection();
            st = con.createStatement();
            ResultSet rst = st.executeQuery(query);
            count = rst.getInt(1);
            System.out.print(count);
        } catch (Exception e) {
                    
        }
    }
}

package com.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class checkAvailability extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Allow-Origin", "*");

        Statement stmt;
        Connection con = connector.return_connection();
        try {

            stmt = con.createStatement();
            String status = "notbooked";
            String query = "SELECT id from seats where seat_status = '" + status + "'";
            ResultSet rs = stmt.executeQuery(query);
            PrintWriter out = res.getWriter();
            JSONObject jsons = new JSONObject();
            jsons.put("status", "success");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

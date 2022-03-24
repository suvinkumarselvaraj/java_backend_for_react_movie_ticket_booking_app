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

public class markseats extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Allow-Origin", "*");
        String id = req.getParameter("id");
        Integer seat_id = Integer.parseInt(id);
        // go to the seats
        Statement stmt;
        Connection con = connector.return_connection();
        try {
            stmt = con.createStatement();
            String status = "notbooked";
            String query = "SELECT seat_status from seats where id = " + seat_id;
            ResultSet rs = stmt.executeQuery(query);
            PrintWriter out = res.getWriter();
            JSONObject jsons = new JSONObject();
            jsons.put("status", "success");
            rs.next();
            System.out.print(rs.getString("seat_status"));
            String currentStatus = rs.getString("seat_status");
            jsons.put("status", "success");
            jsons.put("seatStatus", "notbooked");
            out.print(jsons.toString());

        } catch (

        SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

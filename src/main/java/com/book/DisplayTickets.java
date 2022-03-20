package com.book;

import java.io.PrintWriter;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONObject;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DisplayTickets extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Allow-Origin", "*");

        String name = req.getParameter("username");
        try {
            Statement stmt;
            Connection con = connector.return_connection();
            stmt = con.createStatement();
            String query = "SELECT * FROM tickets WHERE username ='" + name + "' ORDER BY booked_on DESC";
            ResultSet rs = stmt.executeQuery(query);
            String user = rs.getString("username");
            JSONObject results = new JSONObject();
            PrintWriter out = res.getWriter();
            int i = 0;
            ArrayList<JSONObject> arr = new ArrayList<JSONObject>();
            JSONObject jsons = new JSONObject();
            boolean isExisting = false;
            if (rs.isBeforeFirst()) {

                isExisting = true;
                jsons.put("status", "success");
                while (rs.next()) {

                    // out.print(rs.getString("movie_name"));
                    // out.print(rs.getString("theatre_name"));
                    // out.print(rs.getString("seat_no"));
                    // out.print(rs.getString("cost"));
                    // out.print(rs.getString("booked_on"));

                    String movie = rs.getString("movie_name");
                    String theatre = rs.getString("theatre_name");
                    String seats = rs.getString("seat_no");
                    String cost = rs.getString("cost");
                    String booked = rs.getString("booked_on");
                    jsons.put("status", "success");
                    jsons.put("movie_name", movie);
                    jsons.put("theatre", theatre);
                    jsons.put("movie_name", movie);
                    jsons.put("seats", seats);
                    jsons.put("cost", cost);
                    jsons.put("booked", booked);
                    out.print(jsons.toString());

                }
                // out.print(arr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

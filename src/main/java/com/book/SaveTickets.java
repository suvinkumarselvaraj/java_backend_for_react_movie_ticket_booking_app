package com.book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.stream.Collectors;

import org.json.JSONObject;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SaveTickets extends HttpServlet {
    static Connection con;

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.addHeader("Access-Control-Allow-Headers", "*");
        res.addHeader("Access-Control-Allow-origin", "*");
        String jsonBody = new BufferedReader(new InputStreamReader(req.getInputStream())).lines().collect(
                Collectors.joining("\n"));

        System.out.print("im here");
        if (jsonBody == null || jsonBody.trim().length() == 0) {
        }
        JSONObject jObj = new JSONObject(jsonBody);
        System.out.println(jObj);
        Object o = jObj.get("seat__array");
        System.out.println(o);
        PrintWriter pwt = res.getWriter();

        Object ticketCost = jObj.get("cost");
        Object booked_for = jObj.get("movie__date");
        String date = booked_for.toString();

        java.util.Date today = new java.util.Date();
        java.sql.Date currentDate = new java.sql.Date(today.getTime());
        pwt.print(o);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/theatreusers", "root", "");
            PreparedStatement pst = con.prepareStatement("INSERT INTO tickets VALUES(?,?,?,?,?,?,?)");
            pst.setString(1, jObj.getString("movie__name"));
            pst.setString(2, jObj.getString("movie__theatre"));
            pst.setString(3, jObj.getString("venue"));
            pst.setString(4, o.toString());

            pst.setString(5, ticketCost.toString());
            pst.setString(6, jObj.getString("user"));
            pst.setString(7, jObj.getString("movie__date").substring(0, 16));
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

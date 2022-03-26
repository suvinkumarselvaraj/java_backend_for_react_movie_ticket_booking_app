package com.book;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class sendTheatreList extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Allow-Origin", "*");
        String movie_id = req.getParameter("movie_id");
        Integer id = Integer.parseInt(movie_id);
        String query = "SELECT theatre_name FROM theatres " +
                "INNER JOIN movie_theatre_info ON " +
                "theatres.theatre_id = movie_theatre_info.theatre_id " +
                "WHERE movie_theatre_info.movie_id =" + id +
                " AND ABS(TIMESTAMPDIFF(MINUTE,CURRENT_TIME,movie_theatre_info.TIME))>30" +
                " AND DATE >= CURRENT_DATE" +
                " GROUP BY movie_theatre_info.theatre_id";

        System.out.print(query);

        try {
            Statement stmt;
            Connection con = getTheatreConnection.return_theatre_connection();
            stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(query);
            JSONArray array = new JSONArray();
            while (rst.next()) {
                JSONObject json = new JSONObject();
                json.put("movie", rst.getString("theatre_name"));
                array.put(json);
            }
            PrintWriter out = res.getWriter();
            out.write(array.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

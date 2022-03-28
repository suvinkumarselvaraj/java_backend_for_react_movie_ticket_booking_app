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
        String query = "SELECT theatre_name, date, time " +
                "FROM movie_theatre_info INNER JOIN theatres " +
                "ON movie_theatre_info.theatre_id = theatres.theatre_id " +
                "WHERE movie_id = " + id
                + " AND (date = CURRENT_DATE AND TIMESTAMPDIFF(MINUTE, CURRENT_TIME, time) > 30 " +
                "|| date> CURRENT_DATE ) GROUP BY movie_theatre_info.theatre_id";

        System.out.print(query);

        try {
            Statement stmt;
            Connection con = getTheatreConnection.return_theatre_connection();
            stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(query);
            JSONArray array = new JSONArray();
            while (rst.next()) {

                JSONObject json = new JSONObject();

                json.put("theatre_name", rst.getString("theatre_name"));
                json.put("date", rst.getDate("date"));
                json.put("time", rst.getTime("time"));
                array.put(json);

            }
            PrintWriter out = res.getWriter();
            out.write(array.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

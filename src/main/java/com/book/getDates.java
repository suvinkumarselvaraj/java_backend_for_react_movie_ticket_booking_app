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

public class getDates extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Allow-Origin", "*");
        String movie_id = req.getParameter("movie_id");
        String theatre_name = req.getParameter("theatre_name");
        String id_query = "SELECT theatre_id FROM theatres WHERE theatre_name ='" + theatre_name + "'";
        try {
            Statement stmt;
            Connection con = getTheatreConnection.return_theatre_connection();
            stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(id_query);
            rst.next();
            int id = rst.getInt("theatre_id");
            String date_query = "SELECT date,time FROM movie_theatre_info WHERE movie_id =" + movie_id +
                    " AND theatre_id = " + id
                    + " AND (date =CURRENT_DATE AND TIMESTAMPDIFF(MINUTE, CURRENT_TIME, time)>30 || date > CURRENT_DATE) ORDER BY date";
            rst = stmt.executeQuery(date_query);
            JSONArray array = new JSONArray();
            while (rst.next()) {
                JSONObject json = new JSONObject();
                json.put("date", rst.getDate("date"));
                array.put(json);
            }
            PrintWriter out = res.getWriter();
            out.write(array.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

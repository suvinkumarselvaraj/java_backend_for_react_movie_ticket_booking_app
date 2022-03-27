package com.book;

import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class getTimings extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Allow-Origin", "*");
        String m_id = req.getParameter("movie_id");
        Integer movies_id = Integer.parseInt(m_id);
        String date = req.getParameter("date");

        String id_query = "SELECT theatre_id FROM theatres WHERE theatre_name='" + req.getParameter("theatre_name")
                + "'";
        LocalDate d;

        try {
            Statement stmt;
            Connection con = getTheatreConnection.return_theatre_connection();
            stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(id_query);
            d = LocalDate.parse(date);
            System.out.println(d);
            rst.next();
            int theatre_id = rst.getInt("theatre_id");
            String timing_query = "SELECT time FROM movie_theatre_info WHERE movie_id = "
                    + movies_id + " AND theatre_id = " + theatre_id + " AND date = '" + d
                    + "' AND TIMESTAMPDIFF(MINUTE, CURRENT_TIME,time)>30";
            rst = stmt.executeQuery(timing_query);
            JSONArray array = new JSONArray();
            while (rst.next()) {
                JSONObject json = new JSONObject();
                json.put("time", rst.getTime("time"));
                array.put(json);
            }
            PrintWriter out = res.getWriter();
            out.write(array.toString());
            System.out.print(array);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

package com.book;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class getBookedSeats extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Allow-Origin", "*");

        String theatre_name = req.getParameter("theatre_name");
        String movie_id = req.getParameter("movie_name");
        String date = req.getParameter("date");
        String time = req.getParameter("time");
        Integer movie_name = Integer.parseInt(movie_id);
        String theatre_id = "SELECT theatre_id FROM theatres WHERE theatre_name='" + theatre_name +"'";
        LocalTime t;
        LocalDate d;
        try {
            Connection con = getNewConnection.return_connection();
            Statement stmt = con.createStatement();

            ResultSet rst;
            rst = stmt.executeQuery(theatre_id);
            rst.next();
            Integer t_id = rst.getInt("theatre_id");
            System.out.print(t_id);

            d = LocalDate.parse(date);

            t = LocalTime.parse(time);
            String seats = "SELECT seat_no FROM bookings WHERE movie_id = " + movie_name + " AND theatre_id = " + t_id
                    + " AND date = '" + d + "' AND time = '" + t + "'";
            rst = stmt.executeQuery(seats);
            JSONArray array = new JSONArray();
            while (rst.next()) {
                JSONObject json = new JSONObject();
                json.put("seat_no", rst.getInt("seat_no"));
                array.put(json);
            }
            PrintWriter out = res.getWriter();
            out.write(array.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

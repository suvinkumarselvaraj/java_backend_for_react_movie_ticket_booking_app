package com.book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import java.sql.Statement;
import java.sql.Time;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class bookSeats extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Allow-Origin", "*");
        String jsonBody = new BufferedReader(new InputStreamReader(req.getInputStream())).lines().collect(
                Collectors.joining("\n"));
        JSONObject jObj = new JSONObject(jsonBody);
        System.out.println(jObj);

        System.out.println(jObj.getString("date"));

        // received date and time in their own form
        LocalDate d = LocalDate.parse(jObj.getString("date"));
        LocalTime t = LocalTime.parse(jObj.getString("time"));

        // get theatre id and movie id

        Statement stmt;
        Connection con = getNewConnection.return_connection();

        // const data =
        // {'username':user,'movie_name':movie__clicked,'theatre_name':movie__theatre,'seat_no':seat__array,'date':movie__date,'time':movie__time}

        String username = jObj.getString("username");
        String movie_name = jObj.getString("movie_name");
        String theatre_name = jObj.getString("movie_theatre");
        try {

            String bid = "SELECT COUNT(*) FROM bookings";
            String uid = "SELECT user_id FROM users WHERE user_name = '" + username + "'";

            String tid = "SELECT theatre_id FROM theatres WHERE theatre_name = '" + theatre_name + "'";
            stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(bid);
            rst.next();
            Integer booking_id = rst.getInt(1) + 1;
            System.out.print(booking_id);
            rst = stmt.executeQuery(uid);
            rst.next();
            Integer user_id = rst.getInt(1);
            System.out.print(user_id);

            Integer movie_id = Integer.parseInt(movie_name);
            System.out.print(movie_id);
            rst = stmt.executeQuery(tid);
            rst.next();
            Integer theatre_id = rst.getInt(1);
            System.out.print(theatre_id);
            JSONArray array = new JSONArray(jObj.getJSONArray("seat_no"));

            ArrayList<Integer> seats_array = new ArrayList<Integer>();
            for (int i = 0; i < array.length(); i++) {
                seats_array.add(Integer.parseInt(array.getJSONObject(i).getString("id")));
            }

            for (int i = 0; i < seats_array.size(); i++) {
                int seat_no = seats_array.get(i);
                String updateBookings = "INSERT INTO bookings(booking_id,user_id, movie_id, theatre_id,seat_no,date, time,cost) VALUES(?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(updateBookings);
                pst.setInt(1, booking_id);
                pst.setInt(2, user_id);
                pst.setInt(3, movie_id);
                pst.setInt(4, theatre_id);
                pst.setInt(5, seats_array.get(i));
                pst.setDate(6, java.sql.Date.valueOf(d));
                pst.setTime(7, java.sql.Time.valueOf(t));
                pst.setInt(8, 100);
                pst.executeUpdate();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jObj.getJSONArray("seat_no"));

        JSONObject obj = new JSONObject();
        obj.put("status", "success");
        PrintWriter out = res.getWriter();
        out.write(obj.toString());
    }

}

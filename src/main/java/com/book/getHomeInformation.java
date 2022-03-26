package com.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class getHomeInformation extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = res.getWriter();
        try {

            Statement stmt;
            Connection con = getNewConnection.return_connection();
            String countQuery = "SELECT COUNT(*) AS total FROM movies";
            String query = "SELECT movie_id, movie_image, movie_name, movie_genre from movies ";
            stmt = con.createStatement();

            JSONArray array = new JSONArray();
            JSONObject json1 = new JSONObject();
            ResultSet countSet = stmt.executeQuery(countQuery);
            countSet.next();
            int count = countSet.getInt("total");

            ResultSet rs = stmt.executeQuery(query);

            // json.put("status", "success");
            // out.write(json1.toString());

            while (rs.next()) {
                // System.out.print(rs.getInt(1));
                JSONObject json = new JSONObject();
                json.put("index", rs.getInt("movie_id"));
                json.put("name", rs.getString("movie_name"));
                json.put("image", rs.getString("movie_image"));
                json.put("genre", rs.getString("movie_genre"));
                array.put(json);
            }
            out.write(array.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

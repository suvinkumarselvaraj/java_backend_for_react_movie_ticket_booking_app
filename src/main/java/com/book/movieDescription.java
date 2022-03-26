package com.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONObject;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class movieDescription extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setHeader("Access-Control-Allow-Headers", "*");
        res.setHeader("Access-Control-Allow-Origin", "*");
        String id = req.getParameter("id");
        System.out.println(id);
        Integer index = Integer.parseInt(id);
        String query = "SELECT movie_name,movie_image,movie_genre, movie_rating, movie_outline , movie_runtime , movie_description FROM movies WHERE movie_id ="
                + index;
        Statement stmt;
        PrintWriter out = res.getWriter();
        JSONObject json = new JSONObject();
        try {
            Connection con = getNewConnection.return_connection();
            stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(query);
            System.out.println("hello");
            while (rst.next()) {
                json.put("index", index);
                json.put("title", rst.getString("movie_name"));
                json.put("image", rst.getString("movie_image"));
                json.put("genre", rst.getString("movie_genre"));
                json.put("rating", rst.getString("movie_rating"));
                json.put("outline", rst.getString("movie_outline"));
                json.put("runtime", rst.getString("movie_runtime"));
                json.put("description", rst.getString("movie_description"));

            }
            out.println(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

package com.book;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.JSONObject;
import jakarta.servlet.http.*;

public class SendBookings extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        res.addHeader("Access-Control-Allow-Headers", "*");
        res.addHeader("Access-Control-Allow-Origin", "*");
        String name = req.getParameter("username");
        Integer counter = Integer.parseInt(req.getParameter("count"));

        try {
            Statement stmt;
            Connection con = connector.return_connection();
            stmt = con.createStatement();
            String query = "SELECT * FROM tickets WHERE username ='" + name + "' ORDER BY booked_on DESC";
            ResultSet rs = stmt.executeQuery(query);

            PrintWriter out = res.getWriter();
            System.out.print("im am here");
            while (rs.next() && counter > 0) {
                out.println(rs.getString("theatre_name"));
                counter--;
            }
            JSONObject jsons = new JSONObject();
            while (rs.next()) {
                // out.println(rs.getString("theatre_name"));
                // out.println(rs.getString("cost"));
                jsons.put("status", "success");

                String movie = rs.getString("movie_name");
                String theatre = rs.getString("theatre_name");
                String seats = rs.getString("seat_no");
                String cost = rs.getString("cost");
                String booked = rs.getString("booked_on");
                String venue = rs.getString("venue");
                jsons.put("status", "success");
                jsons.put("movie_name", movie);
                jsons.put("theatre", theatre);
                jsons.put("seats", seats);
                jsons.put("cost", cost);
                jsons.put("booked", booked);
                jsons.put("venue", venue);
                out.print(jsons.toString());
                break;
            }

        } catch (Exception e) {

        }
    }

}

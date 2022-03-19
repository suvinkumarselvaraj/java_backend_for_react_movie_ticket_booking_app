package com.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Statement;

import javax.imageio.spi.ImageWriterSpi;

import org.json.JSONObject;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignIn extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.addHeader("Access-Control-Allow-Origin", "*");
        String username = req.getParameter("name");
        String pword = req.getParameter("password");
        PrintWriter out = res.getWriter();
        Statement stmt = null;
        try {

            Connection con = connector.return_connection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select name,password from users");

            Boolean isExisting = false;
            while (rs.next()) {
                String uname = rs.getString("name");
                String pwd = rs.getString("password");

                if (uname.equals(username) && pwd.equals(pword)) {
                    JSONObject jo = new JSONObject();
                    isExisting = true;
                    jo.put("status", "success");

                    jo.put("uname", uname);
                    out.print(jo.toString());
                    break;
                }
            }
            if (isExisting == false) {
                JSONObject jo = new JSONObject();
                jo.put("status", "failure");
                out.print(jo.toString());
            }

        } catch (Exception e) {
            out.print(e);
        }
    }

}

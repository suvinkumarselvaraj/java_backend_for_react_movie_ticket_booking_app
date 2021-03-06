package com.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import org.json.JSONObject;

import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SignUp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
        res.addHeader("Access-Control-Allow-Headers", "*");
        res.addHeader("Access-Control-Allow-Origin", "*");
        String uid = req.getParameter("id");
        String name = req.getParameter("username");
        String pwd = req.getParameter("password");
        System.out.println(name);
        HttpSession session = req.getSession();
        PrintWriter out = res.getWriter();
        Boolean isExisiting = false;
        Statement stmt = null;
        try {
            Connection con = connector.return_connection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select username from users");
            while (rs.next()) {
                String uname = rs.getString("username");
                if (uname.equals(name)) {

                    JSONObject jo = new JSONObject();
                    jo.put("status", "user already exists");
                    jo.put("uname", uname);
                    isExisiting = true;
                    out.print(jo.toString());
                    break;
                }
            }
            if (isExisiting == false) {

                PreparedStatement pst = con.prepareStatement("INSERT INTO users VALUES(?,?,?)");
                pst.setString(1, uid);
                pst.setString(2, name);
                pst.setString(3, pwd);

                pst.executeUpdate();

                session.setAttribute("uname", name);

                JSONObject jo = new JSONObject();
                jo.put("status", "success");
                jo.put("user", name);
                out.print(jo.toString());
            }

        } catch (Exception e) {
            JSONObject jo = new JSONObject();
            jo.put("status", "failed");
            jo.put("Exception", e);
            out.print(jo.toString());
        }

    }

}

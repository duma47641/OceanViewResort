package com.oceanviewresort.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.oceanviewresort.DAO.AdminDAO;
import com.oceanviewresort.Models.Admin;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("Email");
        String password = request.getParameter("Password");

        // -------- VALIDATION --------

        if (email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

            out.println("<script>");
            out.println("alert('Please enter email and password.');");
            out.println("location='admin_login.jsp';");
            out.println("</script>");
            return;
        }

        // -------- DATABASE CHECK --------

        AdminDAO dao = new AdminDAO();
        Admin admin = dao.login(email, password);

        // -------- LOGIN RESULT --------

        if (admin != null) {

            HttpSession session = request.getSession(true);

            // store admin object
            session.setAttribute("admin", admin);

            // session persists until logout (no timeout set)
            // session.setMaxInactiveInterval(-1); // optional: infinite session

            out.println("<script>");
            out.println("alert('Login Successful! Welcome " + admin.getFullName() + "');");
            out.println("location='admin_dashboard.jsp';");
            out.println("</script>");

        } else {

            out.println("<script>");
            out.println("alert('Invalid Email or Password!');");
            out.println("location='admin_login.jsp';");
            out.println("</script>");
        }
    }
}
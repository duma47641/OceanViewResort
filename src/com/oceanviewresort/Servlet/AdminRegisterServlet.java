package com.oceanviewresort.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.oceanviewresort.DAO.AdminDAO;
import com.oceanviewresort.Models.Admin;

@WebServlet("/adminRegister")
public class AdminRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("FullName");
        String email = request.getParameter("Email");
        String password = request.getParameter("Password");
        String confirmPassword = request.getParameter("Confirm_Password");

        // -------- VALIDATIONS --------

        // Full name letters only
        if (!name.matches("[a-zA-Z ]+")) {
            out.println("<script>");
            out.println("alert('Full name must contain letters only.');");
            out.println("location='admin_register.jsp';");
            out.println("</script>");
            return;
        }

        // Password match check
        if (!password.equals(confirmPassword)) {
            out.println("<script>");
            out.println("alert('Passwords do not match!');");
            out.println("location='admin_register.jsp';");
            out.println("</script>");
            return;
        }

        // -------- DATABASE INSERT --------

        Admin admin = new Admin();
        admin.setFullName(name);
        admin.setEmail(email);
        admin.setPassword(password);

        AdminDAO dao = new AdminDAO();
        boolean success = dao.register(admin);

        // -------- RESPONSE --------

        if (success) {
            out.println("<script>");
            out.println("alert('Registration Successful!');");
            out.println("location='admin_login.jsp';");
            out.println("</script>");
        } else {
            out.println("<script>");
            out.println("alert('Registration Failed. Email may already exist.');");
            out.println("location='admin_register.jsp';");
            out.println("</script>");
        }
    }
}
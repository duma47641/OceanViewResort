package com.oceanviewresort.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.oceanviewresort.DAO.StaffMemberDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.StaffMember;

@WebServlet("/staffMemberRegister")
public class StaffMemberRegisterServlet extends HttpServlet {

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
            out.println("location='staff_member_register.jsp';");
            out.println("</script>");
            return;
        }

        // Password match check
        if (!password.equals(confirmPassword)) {
            out.println("<script>");
            out.println("alert('Passwords do not match!');");
            out.println("location='staff_member_register.jsp';");
            out.println("</script>");
            return;
        }

        // -------- DATABASE INSERT --------

        StaffMember staffMember = new StaffMember();
        staffMember.setFullName(name);
        staffMember.setEmail(email);
        staffMember.setPassword(password);

        StaffMemberDAO dao = DAOFactory.getStaffMemberDAO();
        boolean success = dao.register(staffMember);

        // -------- RESPONSE --------

        if (success) {
            out.println("<script>");
            out.println("alert('Registration Successful!');");
            out.println("location='admin_dashboard.jsp';");
            out.println("</script>");
        } else {
            out.println("<script>");
            out.println("alert('Registration Failed. Email may already exist.');");
            out.println("location='staff_member_register.jsp';");
            out.println("</script>");
        }
    }
}
package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.StaffMemberDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.StaffMember;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateStaffMember")
public class UpdateStaffMemberServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("FullName");
        String email = request.getParameter("Email");
        String password = request.getParameter("Password");

        // ---------- EMPTY CHECK ----------
        if(email == null || email.trim().isEmpty()){
            out.println("<script>alert('Staff Member Email cannot be empty!'); history.back();</script>");
            return;
        }

        email = email.trim();

        StaffMemberDAO dao = DAOFactory.getStaffMemberDAO();

        // ---------- GET EXISTING ----------
        StaffMember existing = dao.getStaffMemberById(id);

        // ---------- UNIQUE CHECK ----------
        if(existing != null && !existing.getEmail().equalsIgnoreCase(email)
                && dao.isStaffMemberEmailExists(email)){

            out.println("<script>alert('Staff Member Email already exists!'); history.back();</script>");
            return;
        }

        // ---------- UPDATE ----------
        StaffMember staffMember = new StaffMember(id, name, email, password);
        boolean updated = dao.updateStaffMember(staffMember);

        if(updated){
            out.println("<script>alert('Staff Member Account Updated Successfully!'); location='staff_member_dashboard.jsp';</script>");
        }else{
            out.println("<script>alert('Update Failed!'); history.back();</script>");
        }
    }
}
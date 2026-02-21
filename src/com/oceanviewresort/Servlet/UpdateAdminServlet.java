package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.AdminDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateAdmin")
public class UpdateAdminServlet extends HttpServlet {

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
            out.println("<script>alert('Admin email cannot be empty!'); history.back();</script>");
            return;
        }

        email = email.trim();

        AdminDAO dao = DAOFactory.getAdminDAO();

        // ---------- GET EXISTING ----------
        Admin existing = dao.getAdminById(id);

        // ---------- UNIQUE CHECK ----------
        if(existing != null && !existing.getEmail().equalsIgnoreCase(email)
                && dao.isAdminEmailExists(email)){

            out.println("<script>alert('Admin Email already exists!'); history.back();</script>");
            return;
        }

        // ---------- UPDATE ----------
        Admin admin = new Admin(id, name, email, password);
        boolean updated = dao.updateAdmin(admin);

        if(updated){
            out.println("<script>alert('Admin Account Updated Successfully!'); location='adminList';</script>");
        }else{
            out.println("<script>alert('Update Failed!'); history.back();</script>");
        }
    }
}
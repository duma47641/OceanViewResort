package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.AdminDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/loadAdminForEdit")
public class LoadAdminForEditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("admin") == null){
            response.sendRedirect("admin_login.jsp");
            return;
        }

        Admin admin = (Admin) session.getAttribute("admin");

        // ⭐ THIS LINE IS WHAT YOU ARE MISSING
        request.setAttribute("admin", admin);

        request.getRequestDispatcher("edit_admin_account.jsp")
                .forward(request, response);
    }
}
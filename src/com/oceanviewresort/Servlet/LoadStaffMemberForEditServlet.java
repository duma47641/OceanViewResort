package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.StaffMemberDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.StaffMember;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/loadStaffMemberForEdit")
public class LoadStaffMemberForEditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("staffMember") == null){
            response.sendRedirect("staff_member_login.jsp");
            return;
        }

        StaffMember sessionStaffMember = (StaffMember) session.getAttribute("staffMember");

        StaffMemberDAO dao = DAOFactory.getStaffMemberDAO();
        StaffMember staffMember = dao.getStaffMemberById(sessionStaffMember.getId());

        // update session copy also
        session.setAttribute("staffMember", staffMember);

        request.setAttribute("staffMember", staffMember);

        // ⭐ THIS LINE IS WHAT YOU ARE MISSING
        request.setAttribute("staffMember", staffMember);

        request.getRequestDispatcher("edit_staff_member_account.jsp")
                .forward(request, response);
    }
}
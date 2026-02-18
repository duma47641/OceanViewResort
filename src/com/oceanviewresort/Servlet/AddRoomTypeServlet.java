package com.oceanviewresort.Servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.Models.RoomType;

@WebServlet("/addRoomType")
public class AddRoomTypeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("RoomTypeName");

        if (name == null || name.trim().isEmpty()) {

            out.println("<script>");
            out.println("alert('Room type name cannot be empty!');");
            out.println("location='add_room_type.jsp';");
            out.println("</script>");
            return;
        }
        
        name = name.trim();

        RoomTypeDAO dao = DAOFactory.getRoomTypeDAO();

        // ✅ UNIQUE CHECK
        if (dao.isRoomTypeExists(name)) {

            out.println("<script>");
            out.println("alert('Room type already exists!');");
            out.println("location='add_room_type.jsp';");
            out.println("</script>");
            return;
        }

        boolean success = dao.addRoomType(new RoomType(name));

        if (success) {

            out.println("<script>");
            out.println("alert('Room Type Added Successfully!');");
            out.println("location='add_room_type.jsp';");
            out.println("</script>");

        } else {

            out.println("<script>");
            out.println("alert('Error adding room type!');");
            out.println("location='add_room_type.jsp';");
            out.println("</script>");
        }
    }
}
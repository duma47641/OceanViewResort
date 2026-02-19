package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.RoomType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateRoomType")
public class UpdateRoomTypeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("RoomTypeName");

        RoomTypeDAO dao = DAOFactory.getRoomTypeDAO();
        RoomType roomType = new RoomType(id, name);

        boolean updated = dao.updateRoomType(roomType);

        if(updated){

            out.println("<script>");
            out.println("alert('Room Type Updated Successfully!');");
            out.println("location='roomTypeViewList';");
            out.println("</script>");

        } else {
            response.getWriter().println("Update Failed");
        }
    }
}
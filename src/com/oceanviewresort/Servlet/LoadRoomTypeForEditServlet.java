package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.RoomType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/loadRoomTypeForEdit")
public class LoadRoomTypeForEditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        RoomTypeDAO dao = DAOFactory.getRoomTypeDAO();
        RoomType roomType = dao.getRoomTypeById(id);

        request.setAttribute("roomType", roomType);

        request.getRequestDispatcher("edit_selected_room_type.jsp")
                .forward(request, response);
    }
}
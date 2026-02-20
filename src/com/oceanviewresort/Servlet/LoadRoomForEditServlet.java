package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.RoomDAO;
import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.Room;
import com.oceanviewresort.Models.RoomType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/loadRoomForEdit")
public class LoadRoomForEditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        RoomDAO roomDAO = DAOFactory.getRoomDAO();
        RoomTypeDAO typeDAO = DAOFactory.getRoomTypeDAO();

        Room room = roomDAO.getRoomById(id);
        List<RoomType> types = typeDAO.getAllRoomTypes();

        request.setAttribute("room", room);
        request.setAttribute("roomTypes", types);

        request.getRequestDispatcher("edit_selected_room.jsp")
                .forward(request, response);
    }
}
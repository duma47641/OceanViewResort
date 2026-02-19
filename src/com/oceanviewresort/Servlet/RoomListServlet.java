package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.RoomDAO;
import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.Room;
import com.oceanviewresort.Models.RoomType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/roomList")
public class RoomListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("search");
        String typeId = request.getParameter("type");

        RoomDAO roomDAO = DAOFactory.getRoomDAO();
        RoomTypeDAO typeDAO = DAOFactory.getRoomTypeDAO();

        List<Room> rooms = roomDAO.searchRooms(keyword, typeId);
        List<RoomType> types = typeDAO.getAllRoomTypes();

        request.setAttribute("roomList", rooms);
        request.setAttribute("typeList", types);

        request.getRequestDispatcher("edit_room.jsp")
                .forward(request,response);
    }
}
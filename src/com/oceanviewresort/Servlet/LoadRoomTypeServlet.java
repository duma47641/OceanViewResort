package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.Factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoadRoomTypeServlet")
public class LoadRoomTypeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RoomTypeDAO dao = DAOFactory.getRoomTypeDAO();
        request.setAttribute("roomList", dao.getAllRoomTypes());

        request.getRequestDispatcher("edit_room_type.jsp")
                .forward(request, response);
    }
}
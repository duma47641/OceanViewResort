package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.RoomType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/roomTypesMenu")
public class RoomTypeMenuServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RoomTypeDAO dao = DAOFactory.getRoomTypeDAO();
        List<RoomType> roomTypes = dao.getAllRoomTypes();

        request.setAttribute("roomTypes", roomTypes);
        request.getRequestDispatcher("my_page.jsp").forward(request, response);
    }
}
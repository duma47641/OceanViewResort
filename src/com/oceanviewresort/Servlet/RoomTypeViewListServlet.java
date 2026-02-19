package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.DAOImpl.RoomTypeDAOImpl;
import com.oceanviewresort.Models.RoomType;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/roomTypeViewList")
public class RoomTypeViewListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("search");

        RoomTypeDAO dao = new RoomTypeDAOImpl();
        List<RoomType> list = dao.getAllRoomTypes();

        if (keyword != null && !keyword.trim().isEmpty()) {

            String key = keyword.toLowerCase().trim();

            list = list.stream()
                    .filter(r ->
                            String.valueOf(r.getId()).contains(key) ||
                                    r.getName().toLowerCase().contains(key)
                    )
                    .collect(Collectors.toList());
        }

        request.setAttribute("roomList", list);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("view_room_type.jsp");
        dispatcher.forward(request, response);
    }
}
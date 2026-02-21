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

@WebServlet("/roomTypeList")
public class RoomTypeListServlet extends HttpServlet {

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

        String page = request.getParameter("page");

        if(page == null) page = "edit";

        switch(page) {

            case "viewRoomTypeListAdmin":
                request.setAttribute("roomTypeList", list);
                RequestDispatcher dispatcher = request.getRequestDispatcher("view_room_type.jsp");
                dispatcher.forward(request, response);
                break;

            case "viewRoomTypeListStaff":
                request.setAttribute("roomTypeList", list);
                RequestDispatcher dispatcher2 = request.getRequestDispatcher("view_room_type_staff.jsp");
                dispatcher2.forward(request, response);
                break;

            default:
                request.setAttribute("roomTypeList", list);
                RequestDispatcher dispatcher3 = request.getRequestDispatcher("edit_room_type.jsp");
                dispatcher3.forward(request, response);
                break;
        }
    }
}
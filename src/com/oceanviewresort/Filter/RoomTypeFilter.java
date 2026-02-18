package com.oceanviewresort.Filter;

import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.Models.RoomType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class RoomTypeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // runs once when server starts
        System.out.println("RoomTypeFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        RoomTypeDAO dao = DAOFactory.getRoomTypeDAO();
        List<RoomType> roomTypes = dao.getAllRoomTypes();

        request.setAttribute("roomTypes", roomTypes);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // runs when server stops
        System.out.println("RoomTypeFilter destroyed");
    }
}
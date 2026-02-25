package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.ReservationDAO;
import com.oceanviewresort.DAO.RoomDAO;
import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.Reservation;
import com.oceanviewresort.Models.Room;
import com.oceanviewresort.Models.RoomType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/reservationCheckedInList")
public class ReservationCIListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("search");
        String typeId = request.getParameter("type");
        String roomId = request.getParameter("roomId");

        ReservationDAO reservationDAO = DAOFactory.getReservationDAO();
        RoomDAO roomDAO = DAOFactory.getRoomDAO();
        RoomTypeDAO typeDAO = DAOFactory.getRoomTypeDAO();

        List<Reservation> reservations = reservationDAO.searchAllCheckedInReservations(keyword, typeId, roomId);
        List<Room> rooms = roomDAO.searchRooms(keyword, typeId);
        List<RoomType> types = typeDAO.getAllRoomTypes();

        request.setAttribute("reservationCheckedInList", reservations);
        request.setAttribute("roomList", rooms);
        request.setAttribute("typeList", types);

        String page = request.getParameter("page");

        if(page == null) page = "edit";

        switch(page) {

            case "addReservationList":
                request.getRequestDispatcher("room_list.jsp").forward(request,response);
                break;

            case "viewRoomAdmin":
                request.getRequestDispatcher("view_room.jsp").forward(request,response);
                break;

            default:
                request.getRequestDispatcher("edit_reservation.jsp").forward(request,response);
        }
    }
}
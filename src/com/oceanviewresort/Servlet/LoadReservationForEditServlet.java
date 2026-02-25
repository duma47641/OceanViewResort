package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.ReservationDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.Reservation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/loadReservationForEdit")
public class LoadReservationForEditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        ReservationDAO reservationDAO = DAOFactory.getReservationDAO();

        Reservation reservation = reservationDAO.getReservationById(id);


        request.setAttribute("reservation", reservation);
        request.setAttribute("room", reservation.getRoom());
        request.setAttribute("staffMember", reservation.getStaffMember());

        String page = request.getParameter("page");

        if(page == null) page = "edit";

        switch(page) {

            case "addReservationsList":
                request.getRequestDispatcher("room_list.jsp").forward(request,response);
                break;

            case "addTheReservation":
                request.getRequestDispatcher("reserve_selected_room.jsp").forward(request,response);
                break;

            default:
                request.getRequestDispatcher("edit_selected_reservation.jsp").forward(request,response);
        }
    }
}
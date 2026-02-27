package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.ReservationDAO;
import com.oceanviewresort.DAO.RoomDAO;
import com.oceanviewresort.Factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/checkOutReservation")
public class CheckOutReservationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        int reservationId = Integer.parseInt(request.getParameter("reservationId"));
        int roomId = Integer.parseInt(request.getParameter("roomId"));

        ReservationDAO reservationDAO = DAOFactory.getReservationDAO();
        RoomDAO roomDAO = DAOFactory.getRoomDAO();

        boolean updated = reservationDAO.updateReservationToCheckedOut(reservationId) && roomDAO.updateRoomToCheckedOut(roomId);

        if(updated){
            out.println("<script>alert('Reservation and Room Status Checked-Out Successfully!'); location='staff_member_dashboard.jsp';</script>");
        }else{
            response.getWriter().println("<script>alert('Check-out failed!'); history.back();</script>");
        }
    }
}
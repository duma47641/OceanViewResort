package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.ReservationDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.Reservation;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet("/updateReservation")
@MultipartConfig
public class UpdateReservationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("ReservationIDName"));
        String guestFullName = request.getParameter("GuestFullNameName");
        String guestAddress = request.getParameter("GuestAddressName");
        String guestContactNumber = request.getParameter("GuestContactNumberName");
        Date roomCheckOutDate = Date.valueOf(request.getParameter("RoomCheckOutDateName"));
        double total = Double.parseDouble(request.getParameter("TotalAmountPayableName"));

        // ---------- EMPTY CHECK ----------
        if(guestFullName == null || guestFullName.trim().isEmpty() || guestAddress == null || guestAddress.trim().isEmpty() || roomCheckOutDate == null || total <= 0) {
            out.println("<script>alert('Fields cannot be empty!'); history.back();</script>");
            return;
        }

        guestFullName = guestFullName.trim();
        guestAddress = guestAddress.trim();
        guestContactNumber = guestContactNumber.trim();

        ReservationDAO dao = DAOFactory.getReservationDAO();

        // ---------- UPDATE ----------
        Reservation reservation = new Reservation(id, guestFullName, guestAddress, guestContactNumber, roomCheckOutDate, total);
        boolean updated = dao.updateReservation(reservation);

        if(updated){
            out.println("<script>alert('Reservation Updated Successfully!'); location='staff_member_dashboard.jsp';</script>");
        }else{
            out.println("<script>alert('Update Failed!'); history.back();</script>");
        }
    }
}
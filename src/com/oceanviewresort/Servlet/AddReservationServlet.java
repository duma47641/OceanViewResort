package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.ReservationDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet("/addReservation")
public class AddReservationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try{

            HttpSession session = request.getSession(false);
            StaffMember staff1 = (StaffMember) session.getAttribute("staffMember");

            int staffId = staff1.getId(); // safest
            int roomId = Integer.parseInt(request.getParameter("roomId"));

            String name = request.getParameter("GuestFullNameName");
            String address = request.getParameter("GuestAddressName");
            String contact = request.getParameter("GuestContactNumberName");

            Date checkIn = Date.valueOf(request.getParameter("RoomCheckInDateName"));
            Date checkOut = Date.valueOf(request.getParameter("RoomCheckOutDateName"));

            double total = Double.parseDouble(request.getParameter("TotalAmountPayableName"));

            StaffMember staff = new StaffMember();
            staff.setId(staffId);

            Room room = new Room();
            room.setId(roomId);

            Reservation r = new Reservation(staff, room, name, address, contact, checkIn, checkOut, total);

            ReservationDAO dao = DAOFactory.getReservationDAO();

            if(dao.addReservation(r)){
                out.println("<script>alert('Reservation Added Successfully!'); location='staff_member_dashboard.jsp';</script>");
            }else{
                out.println("<script>alert('Error Adding Reservation!'); history.back();</script>");
            }

        }catch(Exception e){
            e.printStackTrace();
            out.println("<script>alert('Invalid Data!'); history.back();</script>");
        }
    }
}
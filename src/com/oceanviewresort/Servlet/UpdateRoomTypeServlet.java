package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.RoomType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateRoomType")
public class UpdateRoomTypeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("RoomTypeName");

        // ---------- EMPTY CHECK ----------
        if(name == null || name.trim().isEmpty()){
            out.println("<script>alert('Room type name cannot be empty!'); history.back();</script>");
            return;
        }

        name = name.trim();

        RoomTypeDAO dao = DAOFactory.getRoomTypeDAO();

        // ---------- GET EXISTING ----------
        RoomType existing = dao.getRoomTypeById(id);

        // ---------- UNIQUE CHECK ----------
        if(existing != null && !existing.getName().equalsIgnoreCase(name)
                && dao.isRoomTypeExists(name)){

            out.println("<script>alert('Room type already exists!'); history.back();</script>");
            return;
        }

        // ---------- UPDATE ----------
        RoomType roomType = new RoomType(id, name);
        boolean updated = dao.updateRoomType(roomType);

        if(updated){
            out.println("<script>alert('Room Type Updated Successfully!'); location='roomTypeList';</script>");
        }else{
            out.println("<script>alert('Update Failed!'); history.back();</script>");
        }
    }
}
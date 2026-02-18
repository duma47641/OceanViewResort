package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.RoomDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.Room;
import com.oceanviewresort.Models.RoomType;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;

import static com.oceanviewresort.Utils.ImageUtil.compressImage;

@WebServlet("/addRoom")
@MultipartConfig
public class AddRoomServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("RoomNameName");
        String details = request.getParameter("RoomDetailsName");
        double price = Double.parseDouble(request.getParameter("RoomPriceName"));
        int typeId = Integer.parseInt(request.getParameter("RoomTypeID"));

        Part imagePart = request.getPart("RoomImageName");
        byte[] imageBytes = imagePart.getInputStream().readAllBytes();

        final int MAX_SIZE = 16 * 1024 * 1024; // 16MB

        if(imageBytes.length > MAX_SIZE){

            float quality = 0.8f;

            while(imageBytes.length > MAX_SIZE && quality > 0.1f){
                imageBytes = compressImage(imageBytes, quality);
                quality -= 0.1f;
            }

            if(imageBytes.length > MAX_SIZE){
                out.println("<script>alert('Image too large even after compression!'); location='add_room.jsp';</script>");
                return;
            }
        }

        RoomDAO dao = DAOFactory.getRoomDAO();

        if (dao.isRoomNameExists(name)) {
            out.println("<script>alert('Room name already exists!'); location='add_room.jsp';</script>");
            return;
        }

        RoomType type = new RoomType();
        type.setId(typeId);

        Room room = new Room(type, imageBytes, name, details, price);

        boolean success = dao.addRoom(room);

        if (success)
            out.println("<script>alert('Room Added Successfully!'); location='add_room.jsp';</script>");
        else
            out.println("<script>alert('Error adding room!'); location='add_room.jsp';</script>");
    }
}
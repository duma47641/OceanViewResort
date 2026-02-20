package com.oceanviewresort.Servlet;

import com.oceanviewresort.DAO.RoomDAO;
import com.oceanviewresort.Factory.DAOFactory;
import com.oceanviewresort.Models.Room;
import com.oceanviewresort.Models.RoomType;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static com.oceanviewresort.Utils.ImageUtil.compressImage;

@WebServlet("/updateRoom")
@MultipartConfig
public class UpdateRoomServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("RoomID"));
        int typeId = Integer.parseInt(request.getParameter("RoomTypeID"));
        String name = request.getParameter("RoomNameName");
        String details = request.getParameter("RoomDetailsName");
        double price = Double.parseDouble(request.getParameter("RoomPriceName"));

        RoomDAO dao = DAOFactory.getRoomDAO();

        // Get existing room
        Room existingRoom = dao.getRoomById(id);

        // ---------- UNIQUE NAME CHECK ----------
        if(!existingRoom.getName().equalsIgnoreCase(name) && dao.isRoomNameExists(name)){
            out.println("<script>alert('Room name already exists!'); history.back();</script>");
            return;
        }

        byte[] imageBytes;

        Part filePart = request.getPart("RoomImageName");

        // ---------- IMAGE LOGIC ----------
        if(filePart != null && filePart.getSize() > 0){

            imageBytes = filePart.getInputStream().readAllBytes();

            final int MAX_SIZE = 16 * 1024 * 1024; // 16MB

            if(imageBytes.length > MAX_SIZE){

                float quality = 0.8f;

                while(imageBytes.length > MAX_SIZE && quality > 0.1f){
                    imageBytes = compressImage(imageBytes, quality);
                    quality -= 0.1f;
                }

                if(imageBytes.length > MAX_SIZE){
                    out.println("<script>alert('Image too large even after compression!'); history.back();</script>");
                    return;
                }
            }

        } else {
            // keep old image
            imageBytes = existingRoom.getImage();
        }

        // ---------- OBJECT BUILD ----------
        RoomType type = new RoomType();
        type.setId(typeId);

        Room room = new Room(
                id,
                type,
                imageBytes,
                name,
                details,
                price,
                existingRoom.getStatus()
        );

        // ---------- UPDATE ----------
        boolean updated = dao.updateRoom(room);

        if(updated){
            out.println("<script>alert('Room Updated Successfully!'); location='roomList';</script>");
        } else {
            out.println("<script>alert('Update Failed!'); history.back();</script>");
        }
    }
}
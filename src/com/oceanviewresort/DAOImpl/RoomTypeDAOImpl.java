package com.oceanviewresort.DAOImpl;

import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.DatabasePackage.DBConnection;
import com.oceanviewresort.Models.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeDAOImpl implements RoomTypeDAO {

    @Override
    public boolean isRoomTypeExists(String name) {

        boolean exists = false;

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT 1 FROM room_types WHERE LOWER(Room_Type_Name) = LOWER(?)")) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                exists = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;
    }

    @Override
    public boolean addRoomType(RoomType roomType) {

        boolean status = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "INSERT INTO room_types (Room_Type_Name) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, roomType.getName());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public List<RoomType> getAllRoomTypes() {

        List<RoomType> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "SELECT * FROM room_types ORDER BY Room_Type_Name";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new RoomType(
                        rs.getInt("Room_Type_ID"),
                        rs.getString("Room_Type_Name")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public RoomType getRoomTypeById(int id) {

        RoomType room = null;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "SELECT * FROM room_types WHERE Room_Type_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                room = new RoomType(
                        rs.getInt("Room_Type_ID"),
                        rs.getString("Room_Type_Name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return room;
    }

    @Override
    public boolean deleteRoomType(int id) {

        boolean status = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "DELETE FROM room_types WHERE Room_Type_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
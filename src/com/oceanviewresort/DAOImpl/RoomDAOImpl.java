package com.oceanviewresort.DAOImpl;

import com.oceanviewresort.DAO.RoomDAO;
import com.oceanviewresort.DatabasePackage.DBConnection;
import com.oceanviewresort.Models.Room;
import com.oceanviewresort.Models.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    @Override
    public boolean addRoom(Room room) {

        boolean status = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "INSERT INTO rooms (Room_Type_ID, Room_Image, Room_Name, Room_Details, Room_Price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, room.getRoomType().getId());
            ps.setBytes(2, room.getImage());
            ps.setString(3, room.getName());
            ps.setString(4, room.getDetails());
            ps.setDouble(5, room.getPrice());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public List<Room> getAllRooms() {

        List<Room> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql =
                    "SELECT r.*, rt.Room_Type_Name FROM rooms r " +
                            "JOIN room_types rt ON r.Room_Type_ID = rt.Room_Type_ID";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                RoomType type = new RoomType(
                        rs.getInt("Room_Type_ID"),
                        rs.getString("Room_Type_Name")
                );

                Room room = new Room(
                        rs.getInt("Room_ID"),
                        type,
                        rs.getBytes("Room_Image"),
                        rs.getString("Room_Name"),
                        rs.getString("Room_Details"),
                        rs.getDouble("Room_Price"),
                        rs.getString("Room_Status")
                );

                list.add(room);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Room getRoomById(int id) {

        Room room = null;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql =
                    "SELECT r.*, rt.Room_Type_Name FROM rooms r " +
                            "JOIN room_types rt ON r.Room_Type_ID = rt.Room_Type_ID " +
                            "WHERE r.Room_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                RoomType type = new RoomType(
                        rs.getInt("Room_Type_ID"),
                        rs.getString("Room_Type_Name")
                );

                room = new Room(
                        rs.getInt("Room_ID"),
                        type,
                        rs.getBytes("Room_Image"),
                        rs.getString("Room_Name"),
                        rs.getString("Room_Details"),
                        rs.getDouble("Room_Price"),
                        rs.getString("Room_Status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return room;
    }

    @Override
    public boolean deleteRoom(int id) {

        boolean status = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "DELETE FROM rooms WHERE Room_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public boolean isRoomNameExists(String name) {

        boolean exists = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "SELECT 1 FROM rooms WHERE Room_Name=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                exists = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;
    }

    @Override
    public List<Room> searchRooms(String keyword, String typeId){

        List<Room> list = new ArrayList<>();

        try{
            Connection conn = DBConnection.getInstance().getConnection();

            String sql =
                    "SELECT r.*, rt.Room_Type_Name FROM rooms r " +
                            "JOIN room_types rt ON r.Room_Type_ID = rt.Room_Type_ID " +
                            "WHERE 1=1 ";

            if(keyword != null && !keyword.trim().isEmpty()){
                sql += "AND ( " +
                        "CAST(r.Room_ID AS CHAR) LIKE ? OR " +
                        "r.Room_Name LIKE ? OR " +
                        "r.Room_Details LIKE ? OR " +
                        "r.Room_Status LIKE ? OR " +
                        "rt.Room_Type_Name LIKE ? OR " +
                        "CAST(r.Room_Price AS CHAR) LIKE ? " +
                        ") ";
            }

            if(typeId != null && !typeId.isEmpty()){
                sql += "AND r.Room_Type_ID = ? ";
            }

            PreparedStatement ps = conn.prepareStatement(sql);

            int index = 1;

            if(keyword != null && !keyword.trim().isEmpty()){
                String key = "%" + keyword.trim() + "%";

                ps.setString(index++, key);
                ps.setString(index++, key);
                ps.setString(index++, key);
                ps.setString(index++, key);
                ps.setString(index++, key);
                ps.setString(index++, key);
            }

            if(typeId != null && !typeId.isEmpty()){
                ps.setInt(index++, Integer.parseInt(typeId));
            }

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                RoomType type = new RoomType(
                        rs.getInt("Room_Type_ID"),
                        rs.getString("Room_Type_Name")
                );

                Room room = new Room(
                        rs.getInt("Room_ID"),
                        type,
                        rs.getBytes("Room_Image"),
                        rs.getString("Room_Name"),
                        rs.getString("Room_Details"),
                        rs.getDouble("Room_Price"),
                        rs.getString("Room_Status")
                );

                list.add(room);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean updateRoom(Room room){

        boolean status = false;

        try{
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "UPDATE rooms SET Room_Type_ID=?, Room_Image=?, Room_Name=?, Room_Details=?, Room_Price=? WHERE Room_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, room.getRoomType().getId());
            ps.setBytes(2, room.getImage());
            ps.setString(3, room.getName());
            ps.setString(4, room.getDetails());
            ps.setDouble(5, room.getPrice());
            ps.setInt(6, room.getId());

            status = ps.executeUpdate() > 0;

        }catch(Exception e){
            e.printStackTrace();
        }

        return status;
    }
}
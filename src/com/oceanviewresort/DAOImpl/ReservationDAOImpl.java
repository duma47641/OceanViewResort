package com.oceanviewresort.DAOImpl;

import com.oceanviewresort.DAO.ReservationDAO;
import com.oceanviewresort.DatabasePackage.DBConnection;
import com.oceanviewresort.Models.Reservation;
import com.oceanviewresort.Models.Room;
import com.oceanviewresort.Models.RoomType;
import com.oceanviewresort.Models.StaffMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public boolean addReservation(Reservation r) {

        boolean status = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            conn.setAutoCommit(false); // ⭐ start transaction

            // ---------- INSERT RESERVATION ----------
            String sql = "INSERT INTO reservations " +
                    "(Staff_Member_ID, Room_ID, Guest_Full_Name, Guest_Address, Guest_Contact_Number, Room_Check_In_Date, Room_Check_Out_Date, Total_Amount_Payable) "
                    +
                    "VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, r.getStaffMember().getId());
            ps.setInt(2, r.getRoom().getId());
            ps.setString(3, r.getGuestFullName());
            ps.setString(4, r.getGuestAddress());
            ps.setString(5, r.getGuestContactNumber());
            ps.setDate(6, r.getCheckInDate());
            ps.setDate(7, r.getCheckOutDate());
            ps.setDouble(8, r.getTotalAmount());

            int inserted = ps.executeUpdate();

            // ---------- UPDATE ROOM STATUS ----------
            String updateRoom = "UPDATE rooms SET Room_Status=? WHERE Room_ID=?";
            PreparedStatement ps2 = conn.prepareStatement(updateRoom);
            ps2.setString(1, "Checked-In");
            ps2.setInt(2, r.getRoom().getId());

            int updated = ps2.executeUpdate();

            // ---------- COMMIT ----------
            if (inserted > 0 && updated > 0) {
                conn.commit();
                status = true;
            } else {
                conn.rollback();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public List<Reservation> searchAllReservations(String keyword, String typeId, String roomId, String status) {

        List<Reservation> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "SELECT res.*, r.*, rt.* " +
                    "FROM reservations res " +
                    "INNER JOIN rooms r ON res.Room_ID = r.Room_ID " +
                    "INNER JOIN room_types rt ON r.Room_Type_ID = rt.Room_Type_ID " +
                    "WHERE 1=1 ";

            // ---------- KEYWORD SEARCH ----------
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql += " AND ( " +
                        "CAST(res.Reservation_ID AS CHAR) LIKE ? OR " +
                        "CAST(res.Staff_Member_ID AS CHAR) LIKE ? OR " +
                        "res.Guest_Full_Name LIKE ? OR " +
                        "res.Guest_Address LIKE ? OR " +
                        "res.Guest_Contact_Number LIKE ? OR " +
                        "CAST(res.Room_Check_In_Date AS CHAR) LIKE ? OR " +
                        "CAST(res.Room_Check_Out_Date AS CHAR) LIKE ? OR " +
                        "CAST(res.Total_Amount_Payable AS CHAR) LIKE ? OR " +

                        "CAST(r.Room_ID AS CHAR) LIKE ? OR " +
                        "r.Room_Name LIKE ? OR " +
                        "r.Room_Details LIKE ? OR " +
                        "CAST(r.Room_Price AS CHAR) LIKE ? OR " +
                        "r.Room_Status LIKE ? OR " +

                        "CAST(rt.Room_Type_ID AS CHAR) LIKE ? OR " +
                        "rt.Room_Type_Name LIKE ? " +
                        ") ";
            }

            // ---------- TYPE FILTER ----------
            if (typeId != null && !typeId.isEmpty()) {
                sql += " AND rt.Room_Type_ID = ? ";
            }

            // ---------- ROOM FILTER ----------
            if (roomId != null && !roomId.isEmpty()) {
                sql += " AND r.Room_ID = ? ";
            }

            if (status != null && !status.isEmpty()) {
                sql += " AND r.Room_Status = ? ";
            }

            PreparedStatement ps = conn.prepareStatement(sql);

            int index = 1;

            // ---------- SET KEYWORD VALUES ----------
            if (keyword != null && !keyword.trim().isEmpty()) {
                String key = "%" + keyword.trim() + "%";

                for (int i = 0; i < 15; i++) {
                    ps.setString(index++, key);
                }
            }

            // ---------- SET TYPE ----------
            if (typeId != null && !typeId.isEmpty()) {
                ps.setInt(index++, Integer.parseInt(typeId));
            }

            // ---------- SET ROOM ----------
            if (roomId != null && !roomId.isEmpty()) {
                ps.setInt(index++, Integer.parseInt(roomId));
            }

            if (status != null && !status.isEmpty()) {
                ps.setString(index++, status);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                RoomType type = new RoomType(
                        rs.getInt("Room_Type_ID"),
                        rs.getString("Room_Type_Name"));

                Room room = new Room(
                        rs.getInt("Room_ID"),
                        type,
                        rs.getBytes("Room_Image"),
                        rs.getString("Room_Name"),
                        rs.getString("Room_Details"),
                        rs.getDouble("Room_Price"),
                        rs.getString("Room_Status"));

                StaffMember staffMember = new StaffMember(
                        rs.getInt("Staff_Member_ID"));

                Reservation reservation = new Reservation();

                reservation.setId(rs.getInt("Reservation_ID"));
                reservation.setStaffMember(staffMember);
                reservation.setGuestFullName(rs.getString("Guest_Full_Name"));
                reservation.setGuestAddress(rs.getString("Guest_Address"));
                reservation.setGuestContactNumber(rs.getString("Guest_Contact_Number"));
                reservation.setCheckInDate(rs.getDate("Room_Check_In_Date"));
                reservation.setCheckOutDate(rs.getDate("Room_Check_Out_Date"));
                reservation.setTotalAmount(rs.getDouble("Total_Amount_Payable"));
                reservation.setRoom(room);

                list.add(reservation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Reservation> searchAllCheckedInReservations(String keyword, String typeId, String roomId) {

        List<Reservation> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "SELECT res.*, r.*, rt.* " +
                    "FROM reservations res " +
                    "INNER JOIN rooms r ON res.Room_ID = r.Room_ID " +
                    "INNER JOIN room_types rt ON r.Room_Type_ID = rt.Room_Type_ID " +
                    "WHERE 1=1 AND r.Room_Status='Checked-In'";

            // ---------- KEYWORD SEARCH ----------
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql += " AND ( " +
                        "CAST(res.Reservation_ID AS CHAR) LIKE ? OR " +
                        "CAST(res.Staff_Member_ID AS CHAR) LIKE ? OR " +
                        "res.Guest_Full_Name LIKE ? OR " +
                        "res.Guest_Address LIKE ? OR " +
                        "res.Guest_Contact_Number LIKE ? OR " +
                        "CAST(res.Room_Check_In_Date AS CHAR) LIKE ? OR " +
                        "CAST(res.Room_Check_Out_Date AS CHAR) LIKE ? OR " +
                        "CAST(res.Total_Amount_Payable AS CHAR) LIKE ? OR " +

                        "CAST(r.Room_ID AS CHAR) LIKE ? OR " +
                        "r.Room_Name LIKE ? OR " +
                        "r.Room_Details LIKE ? OR " +
                        "CAST(r.Room_Price AS CHAR) LIKE ? OR " +
                        "r.Room_Status LIKE ? OR " +

                        "CAST(rt.Room_Type_ID AS CHAR) LIKE ? OR " +
                        "rt.Room_Type_Name LIKE ? " +
                        ") ";
            }

            // ---------- TYPE FILTER ----------
            if (typeId != null && !typeId.isEmpty()) {
                sql += " AND rt.Room_Type_ID = ? ";
            }

            // ---------- ROOM FILTER ----------
            if (roomId != null && !roomId.isEmpty()) {
                sql += " AND r.Room_ID = ? ";
            }

            PreparedStatement ps = conn.prepareStatement(sql);

            int index = 1;

            // ---------- SET KEYWORD VALUES ----------
            if (keyword != null && !keyword.trim().isEmpty()) {
                String key = "%" + keyword.trim() + "%";

                for (int i = 0; i < 15; i++) {
                    ps.setString(index++, key);
                }
            }

            // ---------- SET TYPE ----------
            if (typeId != null && !typeId.isEmpty()) {
                ps.setInt(index++, Integer.parseInt(typeId));
            }

            // ---------- SET ROOM ----------
            if (roomId != null && !roomId.isEmpty()) {
                ps.setInt(index++, Integer.parseInt(roomId));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                RoomType type = new RoomType(
                        rs.getInt("Room_Type_ID"),
                        rs.getString("Room_Type_Name"));

                Room room = new Room(
                        rs.getInt("Room_ID"),
                        type,
                        rs.getBytes("Room_Image"),
                        rs.getString("Room_Name"),
                        rs.getString("Room_Details"),
                        rs.getDouble("Room_Price"),
                        rs.getString("Room_Status"));

                StaffMember staffMember = new StaffMember(
                        rs.getInt("Staff_Member_ID"));

                Reservation reservation = new Reservation();

                reservation.setId(rs.getInt("Reservation_ID"));
                reservation.setStaffMember(staffMember);
                reservation.setGuestFullName(rs.getString("Guest_Full_Name"));
                reservation.setGuestAddress(rs.getString("Guest_Address"));
                reservation.setGuestContactNumber(rs.getString("Guest_Contact_Number"));
                reservation.setCheckInDate(rs.getDate("Room_Check_In_Date"));
                reservation.setCheckOutDate(rs.getDate("Room_Check_Out_Date"));
                reservation.setTotalAmount(rs.getDouble("Total_Amount_Payable"));
                reservation.setRoom(room);

                list.add(reservation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Reservation getReservationById(int id) {

        Reservation reservation = null;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "SELECT res.*, r.*, rt.* FROM reservations res " +
                    "INNER JOIN rooms r ON res.Room_ID = r.Room_ID " +
                    "INNER JOIN room_types rt ON r.Room_Type_ID = rt.Room_Type_ID " +
                    "WHERE res.Reservation_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                StaffMember staffMember = new StaffMember(
                        rs.getInt("Staff_Member_ID"));

                RoomType type = new RoomType(
                        rs.getInt("Room_Type_ID"),
                        rs.getString("Room_Type_Name"));

                Room room = new Room(
                        rs.getInt("Room_ID"),
                        type,
                        rs.getBytes("Room_Image"),
                        rs.getString("Room_Name"),
                        rs.getString("Room_Details"),
                        rs.getDouble("Room_Price"),
                        rs.getString("Room_Status"));

                reservation = new Reservation(
                        rs.getInt("Reservation_ID"),
                        staffMember,
                        room,
                        rs.getString("Guest_Full_Name"),
                        rs.getString("Guest_Address"),
                        rs.getString("Guest_Contact_Number"),
                        rs.getDate("Room_Check_In_Date"),
                        rs.getDate("Room_Check_Out_Date"),
                        rs.getDouble("Total_Amount_Payable"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservation;
    }

    @Override
    public boolean updateReservation(Reservation reservation) {

        boolean status = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "UPDATE reservations SET Guest_Full_Name=?, Guest_Address=?, Guest_Contact_Number=?, Room_Check_Out_Date=?, Total_Amount_Payable=? WHERE Reservation_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, reservation.getGuestFullName());
            ps.setString(2, reservation.getGuestAddress());
            ps.setString(3, reservation.getGuestContactNumber());
            ps.setDate(4, reservation.getCheckOutDate());
            ps.setDouble(5, reservation.getTotalAmount());
            ps.setInt(6, reservation.getId());

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
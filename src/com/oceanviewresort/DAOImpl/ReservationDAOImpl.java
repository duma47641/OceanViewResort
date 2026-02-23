package com.oceanviewresort.DAOImpl;

import com.oceanviewresort.DAO.ReservationDAO;
import com.oceanviewresort.DatabasePackage.DBConnection;
import com.oceanviewresort.Models.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public boolean addReservation(Reservation r) {

        boolean status = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            conn.setAutoCommit(false); // ⭐ start transaction

            // ---------- INSERT RESERVATION ----------
            String sql = "INSERT INTO reservations " +
                    "(Staff_Member_ID, Room_ID, Guest_Full_Name, Guest_Address, Guest_Contact_Number, Room_Check_In_Date, Room_Check_Out_Date, Total_Amount_Payable) " +
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
            if(inserted > 0 && updated > 0){
                conn.commit();
                status = true;
            } else {
                conn.rollback();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return status;
    }
}
package com.oceanviewresort.DAO;

import java.sql.*;
import com.oceanviewresort.DatabasePackage.DBConnection;
import com.oceanviewresort.Models.StaffMember;
import org.mindrot.jbcrypt.BCrypt;

public class StaffMemberDAO {

    // ================= LOGIN =================
    public StaffMember login(String email, String password) {

        StaffMember staffMember = null;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "SELECT * FROM staff_members WHERE Staff_Member_Email=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String hashedPassword = rs.getString("Staff_Member_Password");

                // compare entered password with hashed password
                if (BCrypt.checkpw(password, hashedPassword)) {

                    staffMember = new StaffMember();
                    staffMember.setId(rs.getInt("Staff_Member_ID"));
                    staffMember.setFullName(rs.getString("Staff_Member_Full_Name"));
                    staffMember.setEmail(rs.getString("Staff_Member_Email"));
                    staffMember.setPassword(hashedPassword);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return staffMember;
    }

    // ================= REGISTER =================
    public boolean register(StaffMember staffMember) {

        boolean status = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "INSERT INTO staff_members (Staff_Member_Full_Name, Staff_Member_Email, Staff_Member_Password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            // HASH PASSWORD HERE
            String hashedPassword = BCrypt.hashpw(staffMember.getPassword(), BCrypt.gensalt());

            ps.setString(1, staffMember.getFullName());
            ps.setString(2, staffMember.getEmail());
            ps.setString(3, hashedPassword);

            int rows = ps.executeUpdate();

            if (rows > 0)
                status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
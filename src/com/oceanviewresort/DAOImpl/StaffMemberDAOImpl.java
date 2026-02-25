package com.oceanviewresort.DAOImpl;

import com.oceanviewresort.DAO.StaffMemberDAO;
import com.oceanviewresort.DatabasePackage.DBConnection;
import com.oceanviewresort.Models.StaffMember;
import com.oceanviewresort.Models.StaffMember;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StaffMemberDAOImpl implements StaffMemberDAO {

    // ================= LOGIN =================
    @Override
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
    @Override
    public boolean register(StaffMember staffMember) {

        boolean status = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "INSERT INTO staff_members (Staff_Member_Full_Name, Staff_Member_Email, Staff_Member_Password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

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

    @Override
    public StaffMember getStaffMemberById(int id) {
        StaffMember staffMember = null;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM staff_members WHERE Staff_Member_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                staffMember = new StaffMember(
                        rs.getInt("Staff_Member_ID"),
                        rs.getString("Staff_Member_Full_Name"),
                        rs.getString("Staff_Member_Email"),
                        rs.getString("Staff_Member_Password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffMember;
    }

    @Override
    public boolean updateStaffMember(StaffMember staffMember) {

        boolean status = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql;

            PreparedStatement ps;

            if(staffMember.getPassword() == null || staffMember.getPassword().isEmpty()){

                sql = "UPDATE staff_members SET Staff_Member_Full_Name=?, Staff_Member_Email=? WHERE Staff_Member_ID=?";
                ps = conn.prepareStatement(sql);

                ps.setString(1, staffMember.getFullName());
                ps.setString(2, staffMember.getEmail());
                ps.setInt(3, staffMember.getId());

            } else {

                sql = "UPDATE staff_members SET Staff_Member_Full_Name=?, Staff_Member_Email=?, StaffMember_Password=? WHERE Staff_Member_ID=?";
                ps = conn.prepareStatement(sql);

                String hashedPassword = BCrypt.hashpw(staffMember.getPassword(), BCrypt.gensalt());

                ps.setString(1, staffMember.getFullName());
                ps.setString(2, staffMember.getEmail());
                ps.setString(3, hashedPassword);
                ps.setInt(4, staffMember.getId());
            }

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public boolean isStaffMemberEmailExists(String email) {

        boolean exists = false;

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT 1 FROM staff_members WHERE LOWER(Staff_Member_Email) = LOWER(?)")) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                exists = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;
    }
}
package com.oceanviewresort.DAOImpl;

import java.sql.*;
import com.oceanviewresort.DAO.AdminDAO;
import com.oceanviewresort.DatabasePackage.DBConnection;
import com.oceanviewresort.Models.Admin;
import org.mindrot.jbcrypt.BCrypt;

public class AdminDAOImpl implements AdminDAO {

    // ================= LOGIN =================
    @Override
    public Admin login(String email, String password) {

        Admin admin = null;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "SELECT * FROM admins WHERE Admin_Email=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String hashedPassword = rs.getString("Admin_Password");

                if (BCrypt.checkpw(password, hashedPassword)) {

                    admin = new Admin();
                    admin.setId(rs.getInt("Admin_ID"));
                    admin.setFullName(rs.getString("Admin_Full_Name"));
                    admin.setEmail(rs.getString("Admin_Email"));
                    admin.setPassword(hashedPassword);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return admin;
    }

    // ================= REGISTER =================
    @Override
    public boolean register(Admin admin) {

        boolean status = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "INSERT INTO admins (Admin_Full_Name, Admin_Email, Admin_Password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            String hashedPassword = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());

            ps.setString(1, admin.getFullName());
            ps.setString(2, admin.getEmail());
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
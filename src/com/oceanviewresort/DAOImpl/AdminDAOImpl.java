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

    @Override
    public boolean updateAdmin(Admin admin) {

        boolean status = false;

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String sql = "UPDATE admins SET Admin_Full_Name=?, Admin_Email=?, Admin_Password=? WHERE Admin_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            String hashedPassword = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());

            ps.setInt(0, admin.getId());
            ps.setString(1, admin.getFullName());
            ps.setString(2, admin.getEmail());
            ps.setString(3, hashedPassword);

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public Admin getAdminById(int id) {
        Admin admin = null;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM admins WHERE Admin_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                admin = new Admin(
                        rs.getInt("Admin_ID"),
                        rs.getString("Admin_Email"),
                        rs.getString("Admin_Full_Name"),
                        rs.getString("AdminPassword")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }

    @Override
    public boolean isAdminEmailExists(String email) {

        boolean exists = false;

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT 1 FROM admins WHERE LOWER(Admin_Email) = LOWER(?)")) {

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
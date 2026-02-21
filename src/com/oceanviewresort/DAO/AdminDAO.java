package com.oceanviewresort.DAO;

import com.oceanviewresort.Models.Admin;

public interface AdminDAO {

    Admin login(String email, String password);

    boolean register(Admin admin);

    boolean updateAdmin(Admin admin);

    Admin getAdminById(int id);

    boolean isAdminEmailExists(String email);
}
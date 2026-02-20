package com.oceanviewresort.Factory;

import com.oceanviewresort.DAO.AdminDAO;
import com.oceanviewresort.DAO.RoomDAO;
import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.DAO.StaffMemberDAO;
import com.oceanviewresort.DAOImpl.AdminDAOImpl;
import com.oceanviewresort.DAOImpl.RoomDAOImpl;
import com.oceanviewresort.DAOImpl.RoomTypeDAOImpl;
import com.oceanviewresort.DAOImpl.StaffMemberDAOImpl;

public class DAOFactory {

    public static RoomTypeDAO getRoomTypeDAO() { return new RoomTypeDAOImpl(); }

    public static RoomDAO getRoomDAO() {
        return new RoomDAOImpl();
    }

    public static AdminDAO getAdminDAO() { return new AdminDAOImpl(); }

    public static StaffMemberDAO getStaffMemberDAO() { return new StaffMemberDAOImpl(); }
}
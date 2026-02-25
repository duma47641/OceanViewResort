package com.oceanviewresort.Factory;

import com.oceanviewresort.DAO.*;
import com.oceanviewresort.DAOImpl.*;

public class DAOFactory {

    public static RoomTypeDAO getRoomTypeDAO() { return new RoomTypeDAOImpl(); }

    public static RoomDAO getRoomDAO() {
        return new RoomDAOImpl();
    }

    public static AdminDAO getAdminDAO() { return new AdminDAOImpl(); }

    public static StaffMemberDAO getStaffMemberDAO() { return new StaffMemberDAOImpl(); }

    public static ReservationDAO getReservationDAO() { return new ReservationDAOImpl(); }
}
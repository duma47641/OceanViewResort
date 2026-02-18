package com.oceanviewresort.Factory;

import com.oceanviewresort.DAO.RoomDAO;
import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.DAOImpl.RoomDAOImpl;
import com.oceanviewresort.DAOImpl.RoomTypeDAOImpl;

public class DAOFactory {

    public static RoomTypeDAO getRoomTypeDAO() {
        return new RoomTypeDAOImpl();
    }

    public static RoomDAO getRoomDAO() {
        return new RoomDAOImpl();
    }
}
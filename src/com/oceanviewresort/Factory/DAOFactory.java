package com.oceanviewresort.Factory;

import com.oceanviewresort.DAO.RoomTypeDAO;
import com.oceanviewresort.DAOImpl.RoomTypeDAOImpl;

public class DAOFactory {

    public static RoomTypeDAO getRoomTypeDAO() {
        return new RoomTypeDAOImpl();
    }
}
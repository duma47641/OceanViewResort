package com.oceanviewresort.DAO;

import com.oceanviewresort.Models.RoomType;
import java.util.List;

public interface RoomTypeDAO {

    boolean isRoomTypeExists(String name);
    boolean addRoomType(RoomType roomType);

    List<RoomType> getAllRoomTypes();

    RoomType getRoomTypeById(int id);

    boolean deleteRoomType(int id);
}
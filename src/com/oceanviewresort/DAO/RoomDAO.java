package com.oceanviewresort.DAO;

import com.oceanviewresort.Models.Room;
import java.util.List;

public interface RoomDAO {
    boolean addRoom(Room room);
    List<Room> getAllRooms();
    Room getRoomById(int id);
    boolean deleteRoom(int id);
    boolean isRoomNameExists(String name);
    List<Room> searchRooms(String keyword, String typeId);
    boolean updateRoom(Room room);
}
package com.oceanviewresort.DAO;

import com.oceanviewresort.Models.Reservation;

import java.util.List;

public interface ReservationDAO {
    boolean addReservation (Reservation reservation);
    List<Reservation> searchAllReservations(String keyword, String typeId, String roomId, String status);
    List<Reservation> searchAllCheckedInReservations(String keyword, String typeId, String roomId);
    Reservation getReservationById(int id);
    boolean updateReservation(Reservation reservation);
}
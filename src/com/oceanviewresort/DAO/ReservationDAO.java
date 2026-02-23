package com.oceanviewresort.DAO;

import com.oceanviewresort.Models.Reservation;

import java.util.List;

public interface ReservationDAO {
    boolean addReservation (Reservation reservation);
    List<Reservation> searchAllReservations(String keyword, String typeId, String roomId, String status);
}
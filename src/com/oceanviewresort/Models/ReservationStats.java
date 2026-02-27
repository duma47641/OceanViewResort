package com.oceanviewresort.Models;

public class ReservationStats {

    private double totalRevenue;
    private int totalReservations;
    private int checkedInCount;
    private int checkedOutCount;
    private double avgBookingValue;

    public ReservationStats(double totalRevenue, int totalReservations,
                            int checkedInCount, int checkedOutCount,
                            double avgBookingValue) {
        this.totalRevenue = totalRevenue;
        this.totalReservations = totalReservations;
        this.checkedInCount = checkedInCount;
        this.checkedOutCount = checkedOutCount;
        this.avgBookingValue = avgBookingValue;
    }

    public double getTotalRevenue() { return totalRevenue; }
    public int getTotalReservations() { return totalReservations; }
    public int getCheckedInCount() { return checkedInCount; }
    public int getCheckedOutCount() { return checkedOutCount; }
    public double getAvgBookingValue() { return avgBookingValue; }
}
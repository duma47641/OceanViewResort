package com.oceanviewresort.Models;

import java.sql.Date;

public class Reservation {

    private int id;
    private StaffMember staffMember;
    private Room room;
    private String guestFullName;
    private String guestAddress;
    private String guestContactNumber;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalAmount;

    public Reservation(){}

    public Reservation(StaffMember staffMember, Room room,
                       String guestFullName, String guestAddress,
                       String guestContactNumber,
                       Date checkInDate, Date checkOutDate,
                       double totalAmount){

        this.staffMember = staffMember;
        this.room = room;
        this.guestFullName = guestFullName;
        this.guestAddress = guestAddress;
        this.guestContactNumber = guestContactNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = totalAmount;
    }

    // getters setters

    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }

    public StaffMember getStaffMember(){ return staffMember; }
    public void setStaffMember(StaffMember staffMember){ this.staffMember = staffMember; }

    public Room getRoom(){ return room; }
    public void setRoom(Room room){ this.room = room; }

    public String getGuestFullName(){ return guestFullName; }
    public void setGuestFullName(String guestFullName){ this.guestFullName = guestFullName; }

    public String getGuestAddress(){ return guestAddress; }
    public void setGuestAddress(String guestAddress){ this.guestAddress = guestAddress; }

    public String getGuestContactNumber(){ return guestContactNumber; }
    public void setGuestContactNumber(String guestContactNumber){ this.guestContactNumber = guestContactNumber; }

    public Date getCheckInDate(){ return checkInDate; }
    public void setCheckInDate(Date checkInDate){ this.checkInDate = checkInDate; }

    public Date getCheckOutDate(){ return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate){ this.checkOutDate = checkOutDate; }

    public double getTotalAmount(){ return totalAmount; }
    public void setTotalAmount(double totalAmount){ this.totalAmount = totalAmount; }
}
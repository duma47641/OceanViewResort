package com.oceanviewresort.Models;

public class Room {

    private int id;
    private RoomType roomType;
    private byte[] image;
    private String name;
    private String details;
    private double price;
    private String status;

    public Room() {}

    public Room(int id, RoomType roomType, byte[] image, String name, String details, double price, String status) {
        this.id = id;
        this.roomType = roomType;
        this.image = image;
        this.name = name;
        this.details = details;
        this.price = price;
        this.status = status;
    }

    public Room(RoomType roomType, byte[] image, String name, String details, double price) {
        this.roomType = roomType;
        this.image = image;
        this.name = name;
        this.details = details;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
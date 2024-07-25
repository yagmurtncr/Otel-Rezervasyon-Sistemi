package com.example.otelrezervasyonsistemi.Menu;

public class Reservation {
    private String entryDate;
    private String releaseDate;
    private String roomType;
    private int roomNumber;

    public Reservation(String entryDate, String releaseDate, String roomType,int roomNumber) {
        this.entryDate = entryDate;
        this.releaseDate = releaseDate;
        this.roomType = roomType;
        this.roomNumber =roomNumber;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
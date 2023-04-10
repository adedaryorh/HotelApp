package model.room;

import model.room.enums.RoomType;

import java.util.Objects;

public class Room implements IRoom {
    private String roomNumber;
    private Double roomPrice;
    private RoomType enumeration;
    private boolean isFree;


    public Room(String roomNumber, Double roomPrice, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.enumeration = roomType;
        if (roomPrice == 0){
            isFree = true;
        } else{
            isFree = false;
        }
    }

    public void setPrice(Double price) {
        this.roomPrice = price;
    }

    @Override
    public String getRoomNumber(){
        return roomNumber;}
    @Override
    public Double getRoomPrice(){
        return roomPrice;}
    @Override
    public RoomType getRoomType(){
        return enumeration;}
    @Override
    public boolean isFree(){
        return isFree;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", roomPrice=" + roomPrice +
                ", enumeration=" + enumeration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == this){
            return true;
        }
        if (!(o instanceof Room)){
            return false;
        }
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber)
                && enumeration == room.enumeration;
    }
    @Override
    public int hashcode() {
        return Objects.hash(roomNumber, enumeration);}

}

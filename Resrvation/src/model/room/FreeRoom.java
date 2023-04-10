package model.room;

import model.room.enums.RoomType;
import model.room.Room;
public class FreeRoom extends Room{

    public FreeRoom( String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }
    @Override
    public String toString(){
        return "FreeRoom => " + super.toString();
    }
    @Override
    public boolean equals(Object o){return super.equals(o);}

    @Override
    public int hashcode(){
        return super.hashcode();
    }

}


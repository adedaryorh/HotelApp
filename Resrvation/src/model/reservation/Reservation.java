package model.reservation;

import model.customer.Customer;
import model.room.IRoom;
import java.util.Date;
import java.util.Objects;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(){}


    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    //Tester construct.
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public void setRoom(IRoom room) {
        this.room = room;
    }

    public Date getCheckInDate() {return checkInDate;}

    public Date getCheckOutDate() {return checkOutDate ;}

    @Override
    public String toString() {
        return "Reservation{" +
                "customer=" + customer +
                ", room=" + room +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + +
                '}';
    }
    @Override
    public int hashCode(){
        return Objects.hash(getCustomer(),getRoom(),getCheckInDate(),getCheckOutDate());
    }
}


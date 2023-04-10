package api;

import java.util.*;
import java.util.Collection;
import model.room.IRoom;
import model.reservation.Reservation;
import model.customer.Customer;
import service.forcustomer.CustomerService;
import service.forReservation.ReservationService;
import java.util.Date;

public class HotelResource {
    private static final HotelResource SINGLETON = new HotelResource();
    private final CustomerService customerService =  CustomerService.getSingleton();
    private final ReservationService reservationService = ReservationService.getSingleton();

    public HotelResource(){
    }

    public static HotelResource getSingleton(){return SINGLETON;}
    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);}
    public Collection<IRoom> findARoom(Date checkedIn, Date checkedOut) {
        return reservationService.findRooms(checkedIn,checkedOut);
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);}

    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(firstName,email, lastName);
    }

    public Reservation bookNewRoom(String customerEmail, IRoom room, Date checkedInDate, Date checkedOutDate) {
        Customer customer = getCustomer(customerEmail);
        if (customer != null) {
            return reservationService.reserveARoom(customer,room, checkedInDate, checkedOutDate);
        } else {
            return null;
        }
    }

    public Collection<Reservation> getCustomersReservation(String customerEmail) {
        final Customer customer = getCustomer(customerEmail);
        if (customer == null) {
            return Collections.emptyList();
        }
        return reservationService.getCustomersReservation(getCustomer(customerEmail));
    }

}

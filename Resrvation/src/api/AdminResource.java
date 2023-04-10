package api;

import java.util.*;
import java.util.Collection;
import model.room.IRoom;
import model.customer.Customer;
import service.forcustomer.CustomerService;
import service.forReservation.ReservationService;
public class AdminResource {
    private static final AdminResource SINGLETON = new AdminResource();
    private final CustomerService customerService = CustomerService.getSingleton();
    private final ReservationService reservationService = ReservationService.getSingleton();

    public AdminResource() { }
    public static AdminResource getSingleton() {
        return SINGLETON; }
    public  Customer getCustomer(String email){
        return customerService.getCustomer(email);}


    public IRoom getARoom(String roomId){
        return reservationService.getARoom(roomId);
    }
    public Collection<IRoom> getAllRooms() {
            return reservationService.getAllRooms();
        }

    public Collection<Customer> getAllCustomer(){
        return customerService.getAllCustomer();}

    public void addRoom(List<IRoom> rooms) {
        for (IRoom room: rooms) {
            reservationService.addRoom(room);
        }
    }
    
    public void showAllAvailableReservations(){
        reservationService.printReservations();}
}

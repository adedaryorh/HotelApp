package service.forReservation;

import java.util.*;

import model.reservation.Reservation;
import model.room.IRoom;
import model.customer.Customer;
import model.room.Room;

public class ReservationService {
   public static final ReservationService SINGLETON = new ReservationService();
   public static final int RECOMMENDED_ROOM_CHECK_DAYS_SPACE = 7;
   //mapping and hastMap tag for IRoom and Reservation.
   private Map<Customer, ArrayList<Integer>> customerReservationMap = new HashMap<Customer, ArrayList<Integer>>();
   private Map<String , ArrayList<Date[]>> roomDatesMap = new HashMap<String , ArrayList<Date[]>>();
   private Map<String, IRoom> rooms = new HashMap<String, IRoom>();
   private Map<String, Collection<Reservation>> reservation = new HashMap<>();
   private List<Reservation> reservations= new ArrayList<Reservation>();
   private ReservationService() {}
   public static ReservationService getSingleton(){
      return SINGLETON;}

   public void addRoom(IRoom room) {
      rooms.put(room.getRoomNumber(), room);
   }

   public void mapRoomReservationDates(String roomNumber, Date checkIn, Date checkOut) {
      ArrayList<Date[]> reservationDates = new ArrayList<Date[]>();
      if (roomDatesMap.get(roomNumber) != null) {
         reservationDates = roomDatesMap.get(roomNumber);
      }
      reservationDates.add(new Date[] {checkIn, checkOut});
      roomDatesMap.put(roomNumber, reservationDates);
   }
   public Collection<IRoom>getAllRooms() {
      return rooms.values();
   }
   public IRoom getARoom(String roomId){
      return rooms.get(roomId);}


   public void mapCustomerReservation(Customer customer, int reservationIndex) {
      ArrayList<Integer> reservationIndices = new ArrayList<Integer>();
      if (customerReservationMap.get(customer) != null) {
         reservationIndices = customerReservationMap.get(customer);
      }
      reservationIndices.add(reservationIndex);
      customerReservationMap.put(customer, reservationIndices);
   }

   public Reservation reserveARoom(Customer customer, IRoom room, Date checkIn, Date checkOut){
      Reservation reservation = new Reservation(customer, room, checkIn, checkOut);
      Collection<Reservation> customerReservation = getCustomersReservation(customer);
      if (customerReservation == null) {
         customerReservation = new LinkedList<>();
      }
      reservations.add(reservation);
      int newReservationIndex = reservations.indexOf(reservation);
      mapCustomerReservation(customer, newReservationIndex);
      mapRoomReservationDates(room.getRoomNumber(), checkIn, checkOut);
      System.out.println("This is your reservation details: " + reservation);;
      return reservation;
   }

   public Collection<IRoom> findRooms(final Date checkedInDate, final Date checkedOutDate){
      return findAvailableRooms(checkedInDate,checkedOutDate);
   }


   public Collection<IRoom>findAlternativeRooms(Date checkedInDate, Date checkedOutDate){
      return findAlternativeRooms(addDefaultDays(checkedInDate), addDefaultDays(checkedOutDate));
   }

   public Date addDefaultDays(Date date){
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(Calendar.DATE, RECOMMENDED_ROOM_CHECK_DAYS_SPACE);
      return calendar.getTime();
   }
   public Collection<Reservation> getCustomersReservation(Customer customer){
      return reservation.get(customer.getEmail());
   }


   public Collection<Reservation> getAllReservations(Customer customer) {
      List<Reservation> customerReservations = new ArrayList<Reservation>();
      ArrayList<Integer> reservationIndices = customerReservationMap.get(customer);
      if (reservationIndices != null) {
         for (int reservationIndex : reservationIndices) {
            customerReservations.add(reservations.get(reservationIndex));
         }
      }
      return customerReservations;
   }
/*
   public Collection<IRoom> findAvailableRooms(Date checkIn, Date checkOut) {
      Collection<IRoom> allRooms = getAllRooms();
      Collection<IRoom> availableRooms = new ArrayList<>(getAllRooms());
      for (IRoom room: allRooms) {
         ArrayList<Date[]> reservationDates = roomDatesMap.get(room.getRoomNumber());
         if (reservationDates != null) {
            for (Date[] reservationDate: reservationDates) {
               if (checkIn.equals(reservationDate[0])
                       || (checkIn.after(reservationDate[0]) && checkIn.before(reservationDate[1])) || checkIn.equals(reservationDate[1]) || checkOut.equals(reservationDate[0])
                       || (checkOut.after(reservationDate[0]) && checkOut.before(reservationDate[1]))
                       || checkOut.equals(reservationDate[1])) {
                  availableRooms.remove(room);
               }
            }
         }
      }
      return availableRooms;
   }

 */

   public Collection<IRoom> findAvailableRooms(Date checkIn, Date checkOut) {
      Collection<IRoom> allRooms = getAllRooms();
      Collection<IRoom> availableRooms = new ArrayList<>(getAllRooms());
      for (IRoom room : allRooms) {
         ArrayList<Date[]> reservations = roomDatesMap.get(room.getRoomNumber());
         if (reservations != null) {
            for (Date[] reservation : reservations) {
               boolean checkInConflict = checkIn.equals(reservation[0])
                       || (checkIn.after(reservation[0]) && checkIn.before(reservation[1]))
                       || checkIn.equals(reservation[1]);
               boolean checkOutConflict = checkOut.equals(reservation[0])
                       || (checkOut.after(reservation[0]) && checkOut.before(reservation[1]))
                       || checkOut.equals(reservation[1]);
               if (checkInConflict || checkOutConflict) {
                  availableRooms.remove(room);
                  break;
               }
            }
         }
      }
      return availableRooms;
   }


   public void printReservations(){
      if (reservations.isEmpty()){
         System.out.println("You don't have any reservation yet");
      }else{
         for (Reservation reservation : reservations){
            System.out.println("----------Kindly find your active reservation---------");
            System.out.println(reservation + "\t\n");}
      }
   }


}

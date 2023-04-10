package service;

import java.util.*;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import api.HotelResource;
import model.reservation.Reservation;
import  model.room.IRoom;


public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getSingleton();
    private static Calendar cal = Calendar.getInstance();
    private static Scanner scanner = new Scanner(System.in);
    private static HotelResource hotelAPI = new HotelResource();
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");


    public static void printMainMenu(){
        Scanner scanner = new Scanner(System.in);
        String selection = "";
        do {
            try {
                System.out.println(".......................................\n" +
                        ".....................WELCOME.......................\n" +
                        "................This is ADEDAYO's Hotel..............\n" +
                        "1. Find and reserve a room\n" +
                        "2. See my reservations\n" +
                        "3. Create an Account\n" +
                        "4. Admin Page\n" +
                        "5. Exit\n" +
                        "Please select an option to proceed: \n");
                selection = scanner.nextLine().trim();
                if (selection.length() == 1) {
                    switch (selection.charAt(0)) {
                        case '1':
                            findAndReserveARoom();
                            break;
                        case '2':
                            ViewMyReservation();
                            break;
                        case '3':
                            createAnAccount();
                            break;
                        case '4':
                            AdminMenu.printAdminMenu();
                            break;
                        case '5':
                            System.out.println("Exiting");
                            break;
                        default:
                            System.out.println("Unknown action performed");
                            break;
                    }
                } else {
                    System.out.println("Error: Invalid action");
                }
            }catch (StringIndexOutOfBoundsException ex) {
                System.out.println("NO valid input received. Exiting the program.");}
        }while(!selection.equals("4") && !selection.equals("5"));
    }

    private static Collection<IRoom> printRooms(Date checkIn, Date checkOut) {
        try {
            Collection<IRoom> rooms = hotelAPI.findARoom(checkIn, checkOut);
            if(rooms.isEmpty()) {
                System.out.println("Sorry! No rooms available for " + dateFormatter.format(checkIn) +
                        " - " + dateFormatter.format(checkOut));
            } else {
                for(IRoom room : rooms) {
                    System.out.println(room);
                }
            }
            return rooms;
        }
        catch (Exception e) {
            System.out.println("Encountered error in finding available rooms. Please try again later!");
            return null;
        }
    }

    private static Date getInputDate(String dateType) {
        Date date = null;
        Date today = new Date();
        boolean getDate = true;
        while(getDate) {
            try {
                System.out.println("Please enter " + dateType + " date (MM/DD/YYYY):");
                date = dateFormatter.parse(scanner.nextLine().trim());
                if (date.after(today)) {
                    getDate = false;
                } else {
                    System.out.println("Date is before today! Please provide a valid input.");
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println("Please input a valid date");
            }
            catch (ParseException e) {
                System.out.println("Error: Invalid date");
                findAndReserveARoom();
            }
        }
        return date;
    }

    private static Date addDaysToADate(Date date, int numOfDays) {
        cal.setTime(date);
        cal.add(Calendar.DATE, numOfDays);
        return cal.getTime();
    }

    private static void findAndReserveARoom() {
        Date today = new Date();
        Date checkedInDate;
        Date checkedOutDate;
        Collection<IRoom> availableRooms;

        while(true) {
            System.out.println("Enter CheckedInDate in format mm/dd/yy '03/02/2023'");
            checkedInDate = getInputDate("Check In");
            System.out.println("Enter CheckedOutDate in format mm/dd/yy '03/07/2023'");
            checkedOutDate = getInputDate("Check Out");
            if (checkedInDate.before(checkedOutDate)) {
                break;
            } else {
                System.out.println("CheckOut is before CheckIn! Please provide a valid input.");
            }
        }
        availableRooms = printRooms(checkedInDate, checkedOutDate);

        if (availableRooms == null) {
            return;
        }
        if(availableRooms.isEmpty()) {
            checkedInDate = addDaysToADate(checkedInDate, 7);
            checkedOutDate = addDaysToADate(checkedOutDate, 7);
            System.out.println("But we have alternative options for your Suit: " +
                    "\n......Find below the available options...." +
                    dateFormatter.format(checkedInDate) + " - " +
                    dateFormatter.format(checkedOutDate));
            availableRooms = printRooms(checkedInDate, checkedOutDate);
        }

        if(!availableRooms.isEmpty()) {
            System.out.println("Enter Room Number of the room you would like to reserve:");
            String response = scanner.nextLine().trim();
            IRoom reserveRoom = hotelAPI.getRoom(response);
            if (reserveRoom != null && availableRooms.contains(reserveRoom)) {
                reserveRoom(reserveRoom, checkedInDate, checkedOutDate);
            } else {
                System.out.println("No such room exists or is available for reservation! Please try again.");
            }
        };
    }


    private static void reserveRoom(IRoom room, Date checkIn, Date checkOut) {
        System.out.println("Please provide email for reservation:");
        String email = scanner.nextLine().trim();
        Reservation newReservation = hotelAPI.bookNewRoom(email, room, checkIn, checkOut);
        if (newReservation == null) {
            System.out.println("You are a new Customer, creating a new account.");
            newReservation = hotelAPI.bookNewRoom(email, room, checkIn, checkOut);
        }
        System.out.println("Reservation successful!");
        System.out.println(newReservation);
    }


    private static void ViewMyReservation(){
        final Scanner scanner = new Scanner (System.in);
        System.out.println("Supply your Email: name@gmail.com");
        final String customerEmail = scanner.nextLine();
        printReservation(hotelResource.getCustomersReservation(customerEmail));
    }

    private static void printReservation(final Collection<Reservation> reservations){
        if (reservations == null  || reservations.isEmpty()){
            System.out.println("Empty Reservation record");
        } else {
            reservations.forEach(reservation -> System.out.println("Please find your" + reservation));
        }
    }


    private static boolean createAnAccount(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Supply firstName");
        final String firstName = scanner.nextLine().trim().toUpperCase();

        System.out.println("Supply lastName");
        final String lastName = scanner.nextLine().trim().toUpperCase();

        System.out.println("Supply Email Address");
        String email = scanner.nextLine().trim();
        if(email==null){System.out.println("Supply email: ");
            email=scanner.nextLine().trim();
        }
        if(hotelAPI.getCustomer(email) == null){
            try{
                hotelAPI.createACustomer(firstName, lastName, email);
                System.out.println("Account successfully created");
                System.out.println(hotelAPI.getCustomer(email));
            }catch (IllegalArgumentException ex){
                System.out.println("Invalid Email");
                return false;
        }
        } else {
            System.out.println("Account with email already present");
        }
        return true;
    }
}


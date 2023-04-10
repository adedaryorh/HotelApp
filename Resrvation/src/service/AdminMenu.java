package service;

import api.AdminResource;
import model.customer.Customer;
import model.room.FreeRoom;
import model.room.enums.RoomType;
import model.room.Room;
import model.room.IRoom;

import java.util.Scanner;

import java.util.*;
public class AdminMenu {
    public static Scanner scanner = new Scanner(System.in);
    public static AdminResource adminAPI = new AdminResource();

    public  static void printAdminMenu(){
        String selection = "";
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println("\t\nAdmin Menu\n" +
                        "....................................\n" +
                        "\n.....Welcome to the Admin Page.........\n" +
                        "1. See all Customers\n" +
                        "\t2. See all Rooms\n" +
                        "\t3. See all Reservations\n" +
                        "\t4. Add a Room\n" +
                        "\t5. Back to Main Menu\n" +
                        ".........................................\n" +
                        "Kindly select an option to proceed\n: ");
                selection = scanner.nextLine();
                if (selection.length() == 1) {
                    switch (selection.charAt(0)) {
                        case '1':
                            displayAllCustomers();
                            break;
                        case '2':
                            displayAllRooms();
                            break;
                        case '3':
                            showAllAvailableReservations();
                            break;
                        case '4':
                            addRoom();
                            break;
                        case '5':
                            MainMenu.printMainMenu();
                            break;
                        default:
                            System.out.println("Selection not Recognized");
                            break;
                    }
                } else {
                    System.out.println("Error: Invalid action");
                }
            } catch (StringIndexOutOfBoundsException ex) {
                System.out.println("NO input received. Exiting the program.");
            }
        }while (selection.charAt(0) != '5' || selection.length() != 1 );
    }

    public static void displayAllCustomers() {
        Collection<Customer> customers = adminAPI.getAllCustomer();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public static void displayAllRooms() {
        Collection<IRoom> rooms = adminAPI.getAllRooms();
        for (IRoom room : rooms) {
            System.out.println(room);
        }
    }

    static Double enterRoomPrice() {
        Double roomPrice;
        while (true) {
            System.out.println("Please enter room price in $:");
            try {
                roomPrice = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid price!");
            }
        }
        return roomPrice;
    }
    static RoomType enterRoomType() {
        String roomTypeResponse;
        RoomType roomType;
        while (true) {
            System.out.println("Enter room type - 1 for Single, 2 for Double:");
            roomTypeResponse = scanner.nextLine().trim();
            if (roomTypeResponse.equals("1")) {
                roomType = RoomType.SINGLE;
                break;
            } else if (roomTypeResponse.equals("2")) {
                roomType = RoomType.DOUBLE;
                break;
            } else {
                System.out.println("Invalid room type.");
            }
        }
        return roomType;
    }


    static Boolean addAnotherRoom() {
        boolean addMoreRooms = true;
        while (true) {
            System.out.println("Would you like to add more rooms? Please enter y/n");
            String moreRoom = scanner.nextLine().trim().toLowerCase();
            if (moreRoom.equals("n")) {
                addMoreRooms = false;
                break;
            } else if (moreRoom.equals("y")) {
                break;
            } else {
                System.out.println("Invalid input!");
            }
        }
        return addMoreRooms;
    }
    private static void showAllAvailableReservations(){
        adminAPI.showAllAvailableReservations();
    }

    public static void addRoom() {
        boolean addMoreRooms = true;
        String roomNumber;
        Double roomPrice;
        RoomType roomType;
        List<IRoom> newRooms = new ArrayList<IRoom>();
        while (addMoreRooms) {
            System.out.println("enter room number:");
            roomNumber = scanner.nextLine().trim();
            roomPrice = enterRoomPrice();
            roomType = enterRoomType();
            newRooms.add(roomPrice == 0 ? new FreeRoom(roomNumber, roomType) : new Room(roomNumber, roomPrice, roomType));
            addMoreRooms = addAnotherRoom();
        }
        adminAPI.addRoom(newRooms);
    }



}


package service;

import java.util.Scanner;

public class Main {

    public static Object main;

    public static void main(String[] args){
        Scanner scanner =new Scanner(System.in);
        String selection = "";
        do{
            System.out.println(
                    "1. Customer Menu\n" +
                            "2. Admin Menu\n"
            );
            try {
                selection = scanner.nextLine();
                switch  (Integer.parseInt(selection)) {
                    case 1:
                        MainMenu.printMainMenu();
                        break;
                    case 2:
                        AdminMenu.printAdminMenu();
                        break;
                    default:
                        System.out.println("Please enter a valid option in 1 or 2 ");
                        break;
                }
            } catch (NumberFormatException e){
                System.out.println("Enter a valid option");
            }
        }
        while (!selection.equals("1") && !selection.equals("2"));
        scanner.close();
    }

}

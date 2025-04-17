package boundary;

import control.AuthControl;
import entity.User;
import shared.Data;

import java.util.Scanner;

public class AuthInterface {

    // UI for login / registration
    public static User authenticate(Scanner sc, Data data) {
        int loginChoice = -1;
        while (loginChoice != 0) {
            System.out.println();
            System.out.println("======================================");
            System.out.println("|                                    |");
            System.out.println("|         BTO Management App         |");
            System.out.println("|                                    |");
            System.out.println("======================================");
            System.out.println();
            System.out.println("(1) Login");
            System.out.println("(2) Register");
            System.out.println("(0) Quit");
            System.out.print("Select Action: ");
            try {
                loginChoice = Integer.parseInt(sc.nextLine());
                System.out.println("------------------------------");

                switch (loginChoice) {
                    case 1:
                        User user = loginUser(sc, data);
                        if (user != null)
                            return user;
                        break;
                    case 2:
                        registerUser(sc, data);
                        break;
                    case 0:
                        System.out.println("Exiting app...");
                        return null;
                    default:
                        System.out.println("Invalid input.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input.");
                System.out.println("------------------------------");
            }
        }
        System.out.println("------------------------------");
        return null;
    }

    public static User loginUser(Scanner sc, Data data) {
        System.out.println();
        System.out.print("NRIC: ");
        String NRIC = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        User user = AuthControl.login(NRIC, password, data);
        System.out.println("------------------------------");
        return user;
    }

    public static void registerUser(Scanner sc, Data data) {
        System.out.println();
        System.out.print("NRIC: ");
        String NRIC = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Age: ");
        try {
            int age = Integer.parseInt(sc.nextLine());
            System.out.print("Marital Status (1. Single, 2. Married): ");
            int maritalStatusCode = Integer.parseInt(sc.nextLine());
            System.out.println("Do you have a registration code? (Leave blank if unsure)");
            String userCode = sc.nextLine();
            AuthControl.register(NRIC, name, age, maritalStatusCode, userCode, data);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid input.");
        }
        System.out.println("------------------------------");
    }
}

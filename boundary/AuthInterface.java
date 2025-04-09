package boundary;

import control.AuthControl;
import entity.User;
import enums.MaritalStatus;
import shared.Data;

import java.util.Scanner;
import java.util.regex.Pattern;

public class AuthInterface {
    public static final String NRIC_PATTERN = "^[A-Za-z][0-9]{7}[A-Za-z]$";

    // UI for login / registration
    public static User authenticate(Scanner sc, Data data) {
        int loginChoice = -1;
        while (loginChoice != 0) {
            System.out.println();
            System.out.println("==========================");
            System.out.println("=== BTO Management App ===");
            System.out.println("==========================");
            System.out.println("(1) Login");
            System.out.println("(2) Register");
            System.out.println("(0) Quit");
            System.out.print("Select Action: ");
            loginChoice = sc.nextInt();
            sc.nextLine();
            System.out.println();
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
                    break;
                default:
                    System.out.println("Invalid input.\n");
            }
        }
        return null;
    }

    public static User loginUser(Scanner sc, Data data) {
        System.out.print("NRIC: ");
        String NRIC = sc.nextLine().toLowerCase();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.println();
        User user = AuthControl.login(NRIC, password, data);
        if (user == null)
            // Failed login
            System.out.println("Invalid credentials. Please try again.\n");
        return user;

    }

    public static void registerUser(Scanner sc, Data data) {
        System.out.print("NRIC: ");
        String regNRIC = sc.nextLine();
        if (!Pattern.matches(NRIC_PATTERN, regNRIC)) {
            // Validate NRIC regex pattern
            System.out.println("Invalid NRIC.");
        } else {
            if (AuthControl.NRICExists(regNRIC, data)) {
                // Ensure new NRIC - new user
                System.out.println("User already exists!");
            } else {
                System.out.print("Name: ");
                String regName = sc.nextLine();
                System.out.print("Age: ");
                int regAge = sc.nextInt();
                sc.nextLine();
                MaritalStatus regMartialStatus = null;
                while (regMartialStatus == null) {
                    System.out.print("Marital Status (1. Single, 2. Married): ");
                    int regMarChoice = sc.nextInt();
                    sc.nextLine();
                    switch (regMarChoice) {
                        case 1:
                            regMartialStatus = MaritalStatus.Single;
                            break;
                        case 2:
                            regMartialStatus = MaritalStatus.Married;
                            break;
                        default:
                            System.out.println("Invalid Input.\n");
                    }
                }
                System.out.println("Do you have a registration code? (Leave blank if unsure)");
                String userCode = sc.nextLine();
                AuthControl.registerUser(regNRIC, regName, regAge, regMartialStatus, userCode, data);
            }
        }
    }
}

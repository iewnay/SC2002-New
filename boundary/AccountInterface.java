package boundary;

import java.util.Scanner;
import entity.User;
import entity.Manager;
import shared.Data;

public class AccountInterface {
    public static <T extends User> boolean accountSettings(T user, Scanner sc, Data data) {

        int accChoice = -1;
        while (accChoice != 0) {
            System.out.println();
            System.out.println("------------------------------------");
            System.out.println("|         Account Settings         |");
            System.out.println("------------------------------------");
            System.out.println();
            System.out.println("(1) View Account Details");
            System.out.println("(2) Change password");
            if (user instanceof Manager) {
                System.out.println("(3) Manage Invite Codes");
            }
            System.out.println("(0) Back to Main Menu");
            System.out.print("Choose an option: ");
            try {
                accChoice = Integer.parseInt(sc.nextLine());
                System.out.println("------------------------------");
                switch (accChoice) {
                    case 1:
                        System.out.println("Account details:");
                        System.out.println(user.toString(true));
                        break;
                    case 2:
                        System.out.println("Old Password:");
                        String oldPass = sc.nextLine();
                        System.out.println("New Password:");
                        String newPass = sc.nextLine();
                        boolean passChange = user.changePassword(oldPass, newPass);
                        if (passChange) {
                            System.out.println("Password changed!");
                            return true;
                        } else
                            System.out.println("Wrong password, please try again.");
                        break;
                    case 3:
                        if (user instanceof Manager) {
                            manageCodes(sc, data);
                        } else {
                            System.out.println("Invalid input.");
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid input.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input.");
                System.out.println("------------------------------");
            }
        }
        return false;
    }

    public static void manageCodes(Scanner sc, Data data) {
        System.out.println();
        System.out.println("| Managing Codes| ");
        System.out.println();
        System.out.println("(1) View Codes");
        System.out.println("(2) Add Manager Code");
        System.out.println("(3) Add Officer Code");
        System.out.println("(4) Remove Manager Code");
        System.out.println("(5) Remove Officer Code Code");
        System.out.println("Enter any other key to return");
        System.out.print("Choose an option: ");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            System.out.println("------------------------------");
            switch (choice) {
                case 1:
                    viewCodes(data);
                    break;
                case 2:
                    addManagerCode(sc, data);
                    break;
                case 3:
                    addOfficerCode(sc, data);
                    break;
                case 4:
                    removeManagerCode(sc, data);
                    break;
                case 5:
                    removeOfficerCode(sc, data);
                    break;
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid input.");
            System.out.println("------------------------------");
        }
    }

    public static void viewCodes(Data data) {
        System.out.println();
        System.out.println("Manager Codes:");
        System.out.println(data.getManagerCodes());
        System.out.println();
        System.out.println("Officer Codes:");
        System.out.println(data.getOfficerCodes());
        System.out.println("------------------------------");
    }

    public static void addManagerCode(Scanner sc, Data data) {
        System.out.println();
        System.out.print("Enter new manager code: ");
        String newCode = sc.nextLine();
        if (data.getManagerCodes().contains(newCode)) {
            System.out.println("Code already exists!");
        } else if (data.getOfficerCodes().contains(newCode)) {
            System.out.println("Officer code already exists!");
        } else {
            data.getManagerCodes().add(newCode);
            System.out.println("Code successfully added.");
        }
        System.out.println("------------------------------");
    }

    public static void addOfficerCode(Scanner sc, Data data) {
        System.out.println();
        System.out.print("Enter new officer code: ");
        String newCode = sc.nextLine();
        if (data.getManagerCodes().contains(newCode)) {
            System.out.println("Manager code already exists!");
        } else if (data.getOfficerCodes().contains(newCode)) {
            System.out.println("Code already exists!");
        } else {
            data.getOfficerCodes().add(newCode);
            System.out.println("Code successfully added.");
        }
        System.out.println("------------------------------");
    }

    public static void removeManagerCode(Scanner sc, Data data) {
        System.out.println();
        System.out.print("Enter manager code to remove (case-sensitive): ");
        String code = sc.nextLine();
        if (data.getManagerCodes().contains(code)) {
            data.getManagerCodes().remove(code);
        } else {
            System.out.println("Code does not exist.");
        }
        System.out.println("------------------------------");
    }

    public static void removeOfficerCode(Scanner sc, Data data) {
        System.out.println();
        System.out.print("Enter officer code to remove (case-sensitive): ");
        String code = sc.nextLine();
        if (data.getOfficerCodes().contains(code)) {
            data.getOfficerCodes().remove(code);
        } else {
            System.out.println("Code does not exist.");
        }
        System.out.println("------------------------------");
    }
}

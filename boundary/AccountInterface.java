package boundary;

import java.util.Scanner;
import entity.*;
import shared.*;

public class AccountInterface {
    public static <T extends User> boolean accountSettings(T user, Scanner sc, Data data) {

        int accChoice = -1;
        while (accChoice != 0) {
            System.out.println("\n--- Account Settings ---");
            System.out.println("(1) Change password");
            System.out.println("(2) Delete account");
            System.out.println("(0) Back to Main Menu");
            System.out.print("Choose an option: ");
            accChoice = sc.nextInt();
            sc.nextLine();
            System.out.println();
            switch (accChoice) {
                case 1:
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

                case 2:
                    System.out.println("Enter your password to delete account:");
                    String deletePass = sc.nextLine();
                    boolean checkPass = user.checkPassword(deletePass);
                    if (checkPass) {
                        System.out.print("Are you sure you want to delete your account? (Y/N) ");
                        String deleteChoice = sc.nextLine().toLowerCase();
                        if (deleteChoice.equals("y")) {
                            if (user instanceof Manager)
                                data.applicantList.remove(user);
                            else if (user instanceof Officer)
                                data.officerList.remove(user);
                            else if (user instanceof Applicant)
                                data.managerList.remove(user);
                            System.out.println("Account successfully deleted.\n");
                            return true;
                        } else if (deleteChoice.equals("n")) {
                            System.out.println();
                        } else {
                            System.out.println("Invalid input.\n");
                        }
                    } else {
                        System.out.println("Wrong password, please try again.");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid input.\n");
            }
        }
        return false;
    }
}

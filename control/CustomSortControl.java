package control;

import java.util.Scanner;

import entity.*;

public class CustomSortControl {
    public static <T extends User> void setFilter(T user, Scanner sc) {
        System.out.println("\nChoose how you want to sort projects:");
        System.out.println("(1) By Project Name");
        System.out.println("(2) By Location");
        System.out.println("(3) By Total Units Available");
        System.out.println("(4) By Average Price");
        System.out.print("(5) Reverse Order ");
        if (user.getReverseSort())
            System.out.println("(Currently descending)");
        else
            System.out.println("(Currently ascending)");
        System.out.println("(6) Clear sort");
        System.out.println("Enter any other key to return");

        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                user.setSortSetting(1);
                break;
            case "2":
                user.setSortSetting(2);
                break;
            case "3":
                user.setSortSetting(3);
                break;
            case "4":
                user.setSortSetting(4);
                break;
            case "5":
                user.reverseSort();
                break;
            case "6":
                user.clearSort();
                break;
            default:
                break;
        }
    }

}

package control;

import java.util.Scanner;

import entity.User;

/**
 * The CustomSortControl class provides functionality to allow users to
 * customize the sorting
 * of projects based on various attributes such as project name, location, total
 * units available,
 * and average price. It also allows the user to toggle the sort order
 * (ascending or descending)
 * and clear the sorting criteria.
 */
public class CustomSortControl {

    /**
     * Presents the user with sorting options for projects and sets the selected
     * sorting criteria
     * for the given user. The user can choose to sort by project name, location,
     * total units available,
     * or average price. The user can also reverse the sort order or clear the
     * sorting criteria.
     *
     * @param <T>  the type of user (extends User)
     * @param user the user whose sort settings are to be modified
     * @param sc   the Scanner object for receiving input from the user
     */
    public static <T extends User> void setFilter(T user, Scanner sc) {
        System.out.println("\nChoose how you want to sort projects:");
        System.out.println("(1) By Project Name");
        System.out.println("(2) By Location");
        System.out.println("(3) By Total Units Available");
        System.out.println("(4) By Average Price");
        System.out.print("(5) Reverse Order ");

        // Display the current sort order
        if (user.getReverseSort())
            System.out.println("(Currently descending)");
        else
            System.out.println("(Currently ascending)");

        System.out.println("(6) Clear sort");
        System.out.println("Enter any other key to return");

        // Get the user's choice
        String choice = sc.nextLine();

        // Handle the user's choice
        switch (choice) {
            case "1":
                user.setSortSetting(1); // Sort by project name
                break;
            case "2":
                user.setSortSetting(2); // Sort by location
                break;
            case "3":
                user.setSortSetting(3); // Sort by total units available
                break;
            case "4":
                user.setSortSetting(4); // Sort by average price
                break;
            case "5":
                user.reverseSort(); // Toggle reverse order
                break;
            case "6":
                user.clearSort(); // Clear sorting criteria
                break;
            default:
                break; // Return if the input is invalid
        }
    }
}

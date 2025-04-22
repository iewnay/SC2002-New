package boundary;

import java.util.Scanner;
import java.util.List;

import control.ApplicantControl;
import control.CustomSortControl;
import control.ArrayControl;
import entity.Applicant;
import entity.Officer;
import entity.Project;
import entity.Enquiry;
import shared.Data;
import enums.Status;

/**
 * The ApplicantInterface class provides a menu-driven interface for applicants 
 * to interact with the system. It allows them to view projects, their applications,
 * their enquiries, request withdrawals, and manage their account settings.
 */
public class ApplicantInterface {

    /**
     * Displays the main menu for the applicant and processes their selection 
     * to navigate between different functionalities such as viewing projects, 
     * viewing applications, managing enquiries, and account settings.
     *
     * @param applicant the current applicant
     * @param sc the scanner object to read user input
     * @param data the data object that stores all the relevant data
     */
    public static void showMenu(Applicant applicant, Scanner sc, Data data) {
        int choice = -1;
        while (choice != 0) {
            System.out.println();
            System.out.println("----------------------------------");
            System.out.println("|         Applicant Menu         |");
            System.out.println("----------------------------------");
            System.out.println();
            System.out.println("(1) View Projects");
            System.out.println("(2) View Application");
            System.out.println("(3) View Enquiries");
            System.out.println("(4) Request Withdrawal");
            System.out.println("(5) Account Settings");
            if (applicant instanceof Officer) {
                System.out.println("(0) Back to Officer Menu");
            } else {
                System.out.println("(0) Logout");
            }
            System.out.print("Choose an option: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                System.out.println("------------------------------");
                switch (choice) {
                    case 1:
                        // VIEW PROJECTS
                        viewProjects(applicant, sc, data);
                        break;
                    case 2:
                        // VIEW APPLICATION
                        viewApplication(applicant, sc, data);
                        break;
                    case 3:
                        // VIEW ENQUIRIES
                        viewEnquiries(applicant, sc, data);
                        break;
                    case 4:
                        // REQUEST WITHDRAWAL
                        ApplicantControl.requestWithdrawal(applicant, sc, data);
                        break;
                    case 5:
                        // ACCOUNT SETTINGS
                        if (AccountInterface.accountSettings(applicant, sc, data))
                            return;
                        break;
                    case 0:
                        // LOGOUT
                        if (applicant instanceof Applicant)
                            System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid input.");
                        System.out.println("------------------------------");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input.");
                System.out.println("------------------------------");
            }
        }
    }

    /**
     * Displays the available projects that the applicant can view, sort, apply for, 
     * or enquire about. Provides options to sort, apply, or enquire based on the user's selection.
     *
     * @param applicant the current applicant
     * @param sc the scanner object to read user input
     * @param data the data object that contains project information
     */
    public static void viewProjects(Applicant applicant, Scanner sc, Data data) {
        // VIEW PROJECTS
        System.out.println();
        System.out.println("| Projects List |");
        System.out.println();
        List<Project> projects = ApplicantControl.getApplicableProjects(applicant, data);
        if (projects.size() > 0) {
            Project.sortProjects(projects, applicant);
            ArrayControl.printFromList(projects, false);
            System.out.println();
            System.out.println("(1) Sort Projects");
            System.out.println("(2) Apply for Project");
            System.out.println("(3) Enquire about Project");
            System.out.println("Enter any other key to return");
            System.out.print("Choose an option: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                System.out.println("------------------------------");
                switch (choice) {
                    case 1:
                        System.out.println();
                        CustomSortControl.setFilter(applicant, sc);
                        Project.sortProjects(projects, applicant);
                        ArrayControl.printFromList(projects, false);
                        System.out.println("------------------------------");
                        break;
                    case 2:
                        ApplicantControl.applyForProject(applicant, projects, sc, data);
                        break;
                    case 3:
                        ApplicantControl.enquireAboutProject(applicant, projects, sc, data);
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input.");
                System.out.println("------------------------------");
            }
        } else {
            System.out.println("No projects currently available.");
            System.out.println("------------------------------");
        }
    }

    /**
     * Displays the applicant's current application details. If the application is successful
     * and the applicant has not yet booked a flat, it offers the option to book a flat.
     *
     * @param applicant the current applicant
     * @param sc the scanner object to read user input
     * @param data the data object that stores all relevant data
     */
    public static void viewApplication(Applicant applicant, Scanner sc, Data data) {
        // VIEW APPLICATION
        if (applicant.hasApplication()) {
            System.out.println(applicant.getApplication());
            if (applicant.getApplication().getStatus() == Status.Successful && applicant.getBooking() == null) {
                System.out.println("Would you like to book flat? (Y/N)");
                String choice = sc.nextLine().toLowerCase();
                if (choice.equals("y")) {
                    applicant.getApplication().book(data);
                }
            }
        } else {
            System.out.println("Currently no application.");
        }
        System.out.println("------------------------------");
    }

    /**
     * Displays the applicant's existing enquiries and provides options to edit or delete an enquiry.
     *
     * @param applicant the current applicant
     * @param sc the scanner object to read user input
     * @param data the data object that stores enquiry information
     */
    public static void viewEnquiries(Applicant applicant, Scanner sc, Data data) {
        if (Enquiry.viewEnquiries(applicant.getEnquiries())) {
            System.out.println();
            System.out.println("(1) Edit Enquiry");
            System.out.println("(2) Delete Enquiry");
            System.out.println("Enter any other key to return");
            System.out.print("Choose an option: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                System.out.println("------------------------------");

                switch (choice) {
                    case 1:
                        ApplicantControl.editEnquiry(applicant, sc, data);
                        System.out.println("------------------------------");
                        break;
                    case 2:
                        ApplicantControl.deleteEnquiry(applicant, sc, data);
                        System.out.println("------------------------------");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input.");
                System.out.println("------------------------------");
            }
        }
    }
}

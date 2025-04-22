package boundary;

import java.util.Scanner;

import entity.Manager;
import entity.Enquiry;
import entity.Project;
import entity.Registration;
import entity.Application;
import entity.Withdrawal;
import shared.Data;
import control.ManagerControl;
import control.ArrayControl;
import control.CustomSortControl;

/**
 * The ManagerInterface class provides a user interface for a manager to manage
 * projects, enquiries, applications, and withdrawal requests.
 * It includes options to view and modify projects, approve or reject
 * applications and withdrawals, and handle enquiries.
 */
public class ManagerInterface {

    /**
     * Displays the manager menu and processes the manager's choice.
     * The manager can view all projects, all enquiries, their own projects, view
     * withdrawal requests, and manage account settings.
     *
     * @param manager the manager object for whom the menu is displayed
     * @param sc      the scanner object to read user input
     * @param data    the data object containing the application data
     */
    public static void showMenu(Manager manager, Scanner sc, Data data) {
        int choice = -1;
        while (choice != 0) {
            System.out.println();
            System.out.println("--------------------------------");
            System.out.println("|         Manager Menu         |");
            System.out.println("--------------------------------");
            System.out.println();
            System.out.println("(1) All Projects");
            System.out.println("(2) All Enquiries");
            System.out.println("(3) Your Projects");
            System.out.println("(4) View Withdrawal Requests");
            System.out.println("(5) Account Settings");
            System.out.println("(0) Logout");
            System.out.print("Choose an option: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                System.out.println("------------------------------");
                switch (choice) {
                    case 1:
                        // ALL PROJECTS
                        System.out.println("All Projects:");
                        ArrayControl.printFromList(data.getProjectList());
                        break;
                    case 2:
                        // ALL ENQUIRIES
                        System.out.println("All Enquiries:");
                        Enquiry.viewEnquiries(data.getEnquiryList());
                        break;
                    case 3:
                        // YOUR PROJECTS
                        projectMenu(manager, sc, data);
                        break;
                    case 4:
                        // VIEW WITHDRAWALS
                        withdrawalMenu(manager, sc, data);
                        break;
                    case 5:
                        // ACCOUNT SETTINGS
                        AccountInterface.accountSettings(manager, sc, data);
                        break;
                    case 0:
                        // LOGOUT
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid input.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input.");
                System.out.println("------------------------------");
            }
        }
    }

    /**
     * Displays the menu for managing the manager's projects.
     * Allows sorting, selecting, and creating projects.
     *
     * @param manager the manager object for whom the project menu is displayed
     * @param sc      the scanner object to read user input
     * @param data    the data object containing the application data
     */
    public static void projectMenu(Manager manager, Scanner sc, Data data) {
        System.out.println();
        System.out.println("| Your Projects |");
        System.out.println();
        // Sort before printing
        Project.sortProjects(manager.getProjects(), manager);
        ArrayControl.printFromList(manager.getProjects(), true);
        System.out.println();
        System.out.println("(1) Sort Projects");
        System.out.println("(2) Select Project");
        System.out.println("(3) Create New Project");
        System.out.println("Enter any other key to return");
        System.out.print("Choose an option: ");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            System.out.println("------------------------------");
            switch (choice) {
                case 1:
                    // SORT PROJECT
                    System.out.println();
                    CustomSortControl.setFilter(manager, sc);
                    Project.sortProjects(manager.getProjects(), manager);
                    ArrayControl.printFromList(manager.getProjects(), true);
                    System.out.println("------------------------------");
                    break;
                case 2:
                    // SELECT PROJECT
                    System.out.println();
                    Project selectedProject = ArrayControl.selectFromList(manager.getProjects(), sc);
                    System.out.println("------------------------------");
                    if (selectedProject != null)
                        selectedProjectMenu(selectedProject, manager, sc, data);
                    break;
                case 3:
                    // CREATE NEW PROJECT
                    ManagerControl.createProject(manager, sc, data);
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid input.");
            System.out.println("------------------------------");
        }
    }

    /**
     * Displays the menu for managing a selected project.
     * Allows the manager to toggle visibility, edit, delete, view registrations,
     * applications, and enquiries,
     * and generate reports for the project.
     *
     * @param project the selected project to manage
     * @param manager the manager object for whom the project menu is displayed
     * @param sc      the scanner object to read user input
     * @param data    the data object containing the application data
     */
    public static void selectedProjectMenu(Project project, Manager manager, Scanner sc, Data data) {
        System.out.println();
        System.out.println(project.toString(true));
        System.out.println();
        System.out.println("(1) Toggle Visibility");
        System.out.println("(2) Edit Project");
        System.out.println("(3) Delete Project");
        System.out.println("(4) View Registrations");
        System.out.println("(5) View All Applications");
        System.out.println("(6) View Pending Applications");
        System.out.println("(7) View All Enquiries");
        System.out.println("(8) View Unanswered Enquiries");
        System.out.println("(9) Generate Report");
        System.out.println("Enter any other key to return");
        System.out.print("Choose an option: ");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    project.toggleVisibility();
                    break;
                case 2:
                    ManagerControl.editProject(manager, project, sc);
                    break;
                case 3:
                    ManagerControl.deleteProject(project, data);
                    break;
                case 4:
                    // VIEW REGISTRATIONS
                    Registration selectedRegistration = ArrayControl.selectFromList(project.getRegistrations(), sc);
                    if (selectedRegistration != null) {
                        System.out.println("(1) Approve application");
                        System.out.println("(2) Reject application");
                        System.out.println("Enter any other key to return");
                        String applicationChoice = sc.nextLine();
                        if (applicationChoice.equals("1")) {
                            selectedRegistration.approve(selectedRegistration.getOfficer());
                        } else if (applicationChoice.equals("2")) {
                            selectedRegistration.reject();
                        }
                    }
                    break;
                case 5:
                    // VIEW APPLICATIONS
                    ArrayControl.printFromList(project.getApplications());
                    break;
                case 6:
                    // VIEW PENDING APPLICATIONS
                    Application selectedApplication = ArrayControl.selectFromList(
                            Application.filterPending(project.getApplications()), sc);
                    if (selectedApplication != null) {
                        System.out.println("(1) Approve application");
                        System.out.println("(2) Reject application");
                        System.out.println("Enter any other key to return");
                        String applicationChoice = sc.nextLine();
                        if (applicationChoice.equals("1")) {
                            selectedApplication.approve();
                        } else if (applicationChoice.equals("2")) {
                            selectedApplication.reject();
                        }
                    }
                    break;
                case 7:
                    // VIEW ENQUIRIES
                    ArrayControl.printFromList(project.getEnquiries());
                    break;
                case 8:
                    // VIEW UNANSWERED ENQUIRIES
                    Enquiry selectedEnquiry = ArrayControl.selectFromList(project.getUnansweredEnquiries(), sc);
                    if (selectedEnquiry != null) {
                        System.out.println("Enter response:");
                        String reply = sc.nextLine();
                        selectedEnquiry.setReply(reply);
                    }
                    break;
                case 9:
                    // GENERATE REPORT
                    ManagerControl.generateReport(project, sc);
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid input.");
            System.out.println("------------------------------");
        }
    }

    /**
     * Displays the withdrawal menu and allows the manager to approve or reject
     * withdrawal requests.
     *
     * @param manager the manager object for whom the withdrawal menu is displayed
     * @param sc      the scanner object to read user input
     * @param data    the data object containing the application data
     */
    public static void withdrawalMenu(Manager manager, Scanner sc, Data data) {
        System.out.println();
        Withdrawal selectedWithdrawal = ArrayControl.selectFromList(data.getWithdrawals(), sc);
        System.out.println("------------------------------");
        if (selectedWithdrawal != null) {
            System.out.println("(1) Approve withdrawal");
            System.out.println("(2) Reject withdrawal");
            System.out.println("Enter any other key to return");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    selectedWithdrawal.approve(data);
                    System.out.println("Withdrawal request approved.");
                    System.out.println("------------------------------");
                    break;
                case 2:
                    selectedWithdrawal.reject(data);
                    System.out.println("Withdrawal request rejected.");
                    System.out.println("------------------------------");
                    break;
            }
        }
    }
}

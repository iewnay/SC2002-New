package boundary;

import java.util.Scanner;

import entity.Officer;
import entity.Project;
import entity.Enquiry;
import entity.FlatBooking;
import control.OfficerControl;
import control.ArrayControl;
import control.CustomSortControl;
import shared.Data;
import enums.Status;

public class OfficerInterface {
    public static void showMenu(Officer officer, Scanner sc, Data data) {

        int choice = -1;
        while (choice != 0) {
            System.out.println();
            System.out.println("--------------------------------");
            System.out.println("|         Officer Menu         |");
            System.out.println("--------------------------------");
            System.out.println();
            System.out.println("(1) Assigned Projects");
            System.out.println("(2) Applicants Menu");
            System.out.println("(3) Account Settings");
            System.out.println("(0) Logout");
            System.out.print("Choose an option: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                System.out.println("------------------------------\n");
                switch (choice) {
                    // ASSIGNED PROJECTS MENU
                    case 1:
                        projectsMenu(officer, sc, data);
                        break;
                    // APPLICANT MENU
                    case 2:
                        ApplicantInterface.showMenu(officer, sc, data);
                        break;
                    // ACCOUNT SETTINGS
                    case 3:
                        AccountInterface.accountSettings(officer, sc, data);
                        break;
                    // LOGOUT
                    case 0:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid input.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input.");
                System.out.println("------------------------------");
            }
        }
    }

    public static void projectsMenu(Officer officer, Scanner sc, Data data) {
        System.out.println();
        System.out.println("| Assigned Projects |");
        System.out.println();
        ArrayControl.printFromList(officer.getAssignedProjects(), true);
        System.out.println();
        System.out.println("(1) Sort Projects");
        System.out.println("(2) Select Project");
        System.out.println("(3) Register For New Project");
        System.out.println("(4) View Registrations");
        System.out.println("Enter any other key to return");
        System.out.print("Choose an option: ");
        try {
            int projectChoice = Integer.parseInt(sc.nextLine());
            System.out.println("------------------------------");
            switch (projectChoice) {
                case 1:
                    System.out.println();
                    CustomSortControl.setFilter(officer, sc);
                    Project.sortProjects(officer.getAssignedProjects(), officer);
                    ArrayControl.printFromList(officer.getAssignedProjects(), true);
                    System.out.println("------------------------------");
                    break;
                case 2:
                    Project selectedProject = ArrayControl.selectFromList(officer.getAssignedProjects(), sc, true);
                    if (selectedProject != null)
                        selectedProjectMenu(selectedProject, officer, sc, data);
                    break;
                case 3:
                    OfficerControl.registerForProject(officer, sc, data);
                    break;
                case 4:
                    OfficerControl.viewRegistrations(officer);
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid input.");
            System.out.println("------------------------------");
        }
    }

    public static void selectedProjectMenu(Project project, Officer officer, Scanner sc, Data data) {
        System.out.println();
        System.out.println(project.toString(true));
        System.out.println();
        System.out.println("(1) View All Enquiries");
        System.out.println("(2) View Unanswered Enquiries");
        System.out.println("(3) View Applications");
        System.out.println("(4) View Pending Bookings");
        System.out.println("(5) Generate Receipt");
        System.out.println("Enter any other key to return");
        System.out.print("Choose an option: ");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            System.out.println("------------------------------");
            switch (choice) {
                case 1:
                    // VIEW ALL ENQUIRIES
                    ArrayControl.printFromList(project.getEnquiries());
                    break;
                case 2:
                    // VIEW UNANSWERED ENQUIRIES
                    Enquiry selectedEnquiry = ArrayControl.selectFromList(project.getUnansweredEnquiries(), sc);
                    if (selectedEnquiry != null) {
                        System.out.println("Enter response:");
                        String reply = sc.nextLine();
                        selectedEnquiry.setReply(reply);
                    }
                    break;
                case 3:
                    // VIEW ALL APPLICATIONS
                    ArrayControl.printFromList(project.getApplications());
                    break;
                case 4:
                    // VIEW PENDING BOOKINGS
                    FlatBooking selectedBooking = ArrayControl.selectFromList(project.getPendingBookings(), sc);
                    if (selectedBooking != null) {
                        System.out.println("Confirm booking? (Y/N)");
                        String confirm = sc.nextLine();
                        if (confirm.equalsIgnoreCase("y")) {
                            project.decrementUnits(selectedBooking.getUnitType());
                            System.out.println("Confirmed booking for " + selectedBooking.getApplicant());
                            selectedBooking.getApplication().setStatus(Status.Booked);
                            selectedBooking.getApplicant().setUnitType(selectedBooking.getUnitType());
                        }
                    }
                    break;
                case 5:
                    // GENERATE RECEIPT
                    OfficerControl.generateReceipt(project, sc);
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid input.");
            System.out.println("------------------------------");
        }
    }
}

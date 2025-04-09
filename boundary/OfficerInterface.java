package boundary;

import java.util.Scanner;

import entity.*;
import control.*;
import shared.*;
import enums.*;

public class OfficerInterface {
    public static void showMenu(Officer officer, Scanner sc, Data data) {

        int choice = -1;
        while (choice != 0) {
            System.out.println("\n--- Officer Menu ---");
            System.out.println("(1) Assigned Projects");
            System.out.println("(2) Applicants Menu");
            System.out.println("(3) Account Settings");
            System.out.println("(0) Logout");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                // PROJECTS MENU
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
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    public static void projectsMenu(Officer officer, Scanner sc, Data data) {
        System.out.println("\nAssigned Projects:");
        ArrayControl.printFromList(officer.getAssignedProjects(), true);
        System.out.println();
        System.out.println("(1) Sort Projects");
        System.out.println("(2) Select Project");
        System.out.println("(3) Register For New Project");
        System.out.println("(4) View Registrations");
        System.out.println("Enter any other key to return");
        System.out.print("Choose an option: ");
        String projectChoice = sc.nextLine();
        switch (projectChoice) {
            case "1":
                CustomSortControl.setFilter(officer, sc);
                Project.sortProjects(officer.getAssignedProjects(), officer);
                ArrayControl.printFromList(officer.getAssignedProjects(), true);
                break;
            case "2":
                Project selectedProject = ArrayControl.selectFromList(officer.getAssignedProjects(), sc, true);
                if (selectedProject != null)
                    selectedProjectMenu(selectedProject, officer, sc, data);
                break;
            case "3":
                OfficerControl.registerForProject(officer, sc, data);
                break;
            case "4":
                OfficerControl.viewRegistrations(officer);
                break;
            default:
                break;
        }
    }

    public static void selectedProjectMenu(Project project, Officer officer, Scanner sc, Data data) {
        System.out.println(project.toString(true));
        System.out.println("(1) View All Enquiries");
        System.out.println("(2) View Unanswered Enquiries");
        System.out.println("(3) View Applications");
        System.out.println("(4) View Pending Bookings");
        System.out.println("Enter any other key to return");
        System.out.print("Choose an option: ");
        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                ArrayControl.printFromList(project.getEnquiries());
                break;
            case "2":
                Enquiry selectedEnquiry = ArrayControl.selectFromList(project.getUnansweredEnquiries(), sc);
                if (selectedEnquiry != null) {
                    System.out.println("Enter response:");
                    String reply = sc.nextLine();
                    selectedEnquiry.setReply(reply);
                }
                break;
            case "3":
                ArrayControl.printFromList(project.getApplications());
                break;
            case "4":
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
        }
    }
}

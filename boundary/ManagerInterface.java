package boundary;

import java.util.Scanner;

import entity.*;
import shared.*;
import control.*;

public class ManagerInterface {
    public static void showMenu(Manager manager, Scanner sc, Data data) {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n--- Manager Menu ---");
            System.out.println("(1) All Projects");
            System.out.println("(2) All Enquiries");
            System.out.println("(3) Your Projects");
            System.out.println("(4) View Withdrawal Requests");
            System.out.println("(5) Generate Report");
            System.out.println("(6) Account Settings");
            System.out.println("(0) Logout");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    // ALL PROJECTS
                    System.out.println("\nAll Projects:");
                    ArrayControl.printFromList(data.projectList);
                    break;
                case 2:
                    // ALL ENQUIRIES
                    System.out.println("\nAll Enquiries:");
                    Enquiry.viewEnquiries(data.enquiryList);
                    break;
                case 3:
                    // YOUR PROJECTS
                    projectMenu(manager, sc, data);
                    break;
                case 4:
                    withdrawalMenu(manager, sc, data);
                    break;
                case 5:
                    break;
                case 6:
                    AccountInterface.accountSettings(manager, sc, data);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    public static void projectMenu(Manager manager, Scanner sc, Data data) {
        System.out.println("\nYour Projects:");
        // Sort before printing
        Project.sortProjects(manager.getProjects(), manager);
        ArrayControl.printFromList(manager.getProjects(), true);
        System.out.println("\n(1) Sort Projects");
        System.out.println("(2) Select Project");
        System.out.println("(3) Create New Project");
        System.out.println("Enter any other key to return");
        System.out.print("Choose an option: ");
        String projectChoice = sc.nextLine();
        switch (projectChoice) {
            case "1":
                // SORT PROJECT
                CustomSortControl.setFilter(manager, sc);
                Project.sortProjects(manager.getProjects(), manager);
                ArrayControl.printFromList(manager.getProjects(), true);
                break;
            case "2":
                // SELECT PROJECT
                Project selectedProject = ArrayControl.selectFromList(manager.getProjects(), sc);
                if (selectedProject != null)
                    selectedProjectMenu(selectedProject, manager, sc, data);
                break;
            case "3":
                // CREATE NEW PROJECT
                ManagerControl.createProject(manager, sc, data);
                break;
            default:
                break;
        }
    }

    public static void selectedProjectMenu(Project project, Manager manager, Scanner sc, Data data) {
        System.out.println(project.toString(true));
        System.out.println("(1) Toggle Visibility");
        System.out.println("(2) Edit Project");
        System.out.println("(3) Delete Project");
        System.out.println("(4) View Registrations");
        System.out.println("(5) View All Applications");
        System.out.println("(6) View Pending Applications");
        System.out.println("(7) View All Enquiries");
        System.out.println("(8) View Unanswered Enquiries");
        System.out.println("Enter any other key to return");
        System.out.print("Choose an option: ");
        String selectedProjectChoice = sc.nextLine();
        switch (selectedProjectChoice) {
            case "1":
                project.toggleVisibility();
                break;
            case "2":
                ManagerControl.editProject(manager, project, sc);
                break;
            case "3":
                ManagerControl.deleteProject(project, data);
                break;
            case "4":
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
            case "5":
                // VIEW APPLICATIONS
                ArrayControl.printFromList(project.getApplications());
                break;
            case "6":
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
            case "7":
                // VIEW ENQUIRIES
                ArrayControl.printFromList(project.getEnquiries());
                break;
            case "8":
                // VIEW UNANSWERED ENQUIRIES
                Enquiry selectedEnquiry = ArrayControl.selectFromList(project.getUnansweredEnquiries(), sc);
                if (selectedEnquiry != null) {
                    System.out.println("Enter response:");
                    String reply = sc.nextLine();
                    selectedEnquiry.setReply(reply);
                }
                break;
        }
    }

    public static void withdrawalMenu(Manager manager, Scanner sc, Data data) {
        Withdrawal selectedWithdrawal = ArrayControl.selectFromList(data.withdrawals, sc);
        if (selectedWithdrawal != null) {
            System.out.println("(1) Approve withdrawal");
            System.out.println("(2) Reject withdrawal");
            System.out.println("Enter any other key to return");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                selectedWithdrawal.approve();
            } else {
                selectedWithdrawal.reject();
            }
        }
    }
}

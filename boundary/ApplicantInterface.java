package boundary;

import java.util.Scanner;
import java.util.List;

import control.ApplicantControl;
import control.CustomSortControl;
import control.ArrayControl;
import entity.Applicant;
import entity.Project;
import entity.Withdrawal;
import entity.Enquiry;
import shared.Data;
import enums.Status;

public class ApplicantInterface {
    public static void showMenu(Applicant applicant, Scanner sc, Data data) {
        int choice = -1;
        while (choice != 0) {
            System.out.println();
            System.out.println("--- Applicant Menu ---");
            System.out.println("(1) View Projects");
            System.out.println("(2) View Application");
            System.out.println("(3) View Enquiries");
            System.out.println("(4) Request Withdrawal");
            System.out.println("(5) Account Settings");
            System.out.println("(0) Logout");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine();
            System.out.println();
            switch (choice) {
                case 1:
                    viewProjects(applicant, sc, data);
                    break;
                case 2:
                    viewApplication(applicant, sc, data);
                    break;
                case 3:
                    viewEnquiries(applicant, sc, data);
                    break;
                case 4:
                    requestWithdrawal(applicant, sc, data);
                    break;
                case 5:
                    if (AccountInterface.accountSettings(applicant, sc, data))
                        return;
                    break;
                // === Misc === //
                case 0:
                    System.out.println("Logging out...\n");
                    return;
                default:
                    System.out.println("Invalid input.\n");
            }
        }
    }

    public static void viewProjects(Applicant applicant, Scanner sc, Data data) {
        // VIEW PROJECTS
        List<Project> projects = ApplicantControl.getApplicableProjects(applicant, data);
        int numProjects = projects.size();
        if (numProjects > 0) {
            System.out.println("Projects list:");
            Project.sortProjects(projects, applicant);
            ArrayControl.printFromList(projects, false);
            System.out.println("\n(1) Sort Projects");
            System.out.println("(2) Apply for Project");
            System.out.println("(3) Enquire about Project");
            System.out.println("Enter any other key to return");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    CustomSortControl.setFilter(applicant, sc);
                    Project.sortProjects(projects, applicant);
                    ArrayControl.printFromList(projects, false);
                    break;
                case "2":
                    ApplicantControl.applyForProject(applicant, projects, sc, data);
                    break;
                case "3":
                    ApplicantControl.enquireAboutProject(applicant, projects, sc, data);
                    break;
            }

        } else {
            System.out.println("No projects currently available.");
        }
    }

    public static void viewApplication(Applicant applicant, Scanner sc, Data data) {
        if (applicant.hasApplication()) {
            System.out.println(applicant.getApplication());
            if (applicant.getApplication().getStatus() == Status.Successful) {
                System.out.println("Would you like to book flat? (Y/N)");
                String choice = sc.nextLine().toLowerCase();
                if (choice.equals("y")) {
                    applicant.getApplication().book(data);
                }
            }
        } else {
            System.out.println("Currently no application.");
        }
    }

    public static void viewEnquiries(Applicant applicant, Scanner sc, Data data) {

        if (Enquiry.viewEnquiries(applicant.getEnquiries())) {
            System.out.println();
            System.out.println("(1) Edit Enquiry");
            System.out.println("(2) Delete Enquiry");
            System.out.println("Enter any other key to return");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    ApplicantControl.editEnquiry(applicant, sc, data);
                    break;
                case "2":
                    ApplicantControl.deleteEnquiry(applicant, sc, data);
                    break;
            }
        }
    }

    public static void requestWithdrawal(Applicant applicant, Scanner sc, Data data) {
        if (applicant.getApplication() != null) {
            if (applicant.getApplication().getStatus() != Status.Booked) {
                Withdrawal withdrawal = new Withdrawal(applicant.getApplication());
                data.withdrawals.add(withdrawal);
            } else {
                System.out.println("HDB already booked and approved!");
            }
        } else {
            System.out.println("No application to withdraw from.");
        }
    }
}

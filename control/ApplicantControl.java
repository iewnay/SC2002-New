package control;

import shared.Data;
import entity.Application;
import entity.Applicant;
import entity.Officer;
import entity.Project;
import entity.Enquiry;
import entity.Withdrawal;
import enums.Status;
import enums.UnitType;
import enums.MaritalStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApplicantControl {
    public static void applyForProject(Applicant applicant, List<Project> projects, Scanner sc, Data data) {
        boolean canApply = false;
        if (applicant.getApplication() == null) {
            canApply = true;
        } else {
            if (applicant.getApplication().getStatus() == Status.Unsuccessful) {
                canApply = true;
            }
        }
        if (!canApply) {
            System.out.println("You already have an application. Withdraw to apply for a new project.");
            System.out.println("------------------------------");
        } else {
            System.out.println();
            System.out.println("| Eligible Projects |");
            System.out.println();
            Project selectedProject = ArrayControl.selectFromList(projects, sc);
            System.out.println("------------------------------");
            if (selectedProject != null) {
                // Option to select unit type only if married
                UnitType selectedUnit;
                if (applicant.getMaritalStatus() == MaritalStatus.Married)
                    selectedUnit = ArrayControl.selectUnitType(selectedProject, sc);
                else
                    selectedUnit = UnitType.TWO_ROOM;
                Application application = new Application(applicant, selectedProject, selectedUnit);
                data.getApplicationList().add(application);
                System.out.println("Successfully applied for project!");
                System.out.println("------------------------------");
            }
        }
    }

    public static List<Project> getApplicableProjects(Applicant applicant, Data data) {
        List<Project> applicableProjects = new ArrayList<>();
        boolean eligible = false;
        for (Project project : data.getProjectList()) {
            if (project.getVisibility() && project.inDateRange())
                // Further filter
                if (applicant.isMarried())
                    // Married applicants can apply if 21 years and above
                    if (applicant.getAge() >= 21)
                        eligible = true;
                    // Single applicants must be 35 years and above
                    else if (applicant.getAge() >= 35)
                        // Single and >=35 can only apply for 2-Room
                        if (project.hasTwoRoom())
                            eligible = true;

            // Additional filter for Officers using Applicant Interface
            if (applicant instanceof Officer) {
                if (((Officer) applicant).getAssignedProjects().contains(project))
                    eligible = false;
            }
            if (eligible)
                applicableProjects.add(project);

        }
        return applicableProjects;
    }

    public static void enquireAboutProject(Applicant applicant, List<Project> projects, Scanner sc, Data data) {
        Project selectedProject = ArrayControl.selectFromList(projects, sc);
        if (selectedProject != null) {
            System.out.println("Enquiry header:");
            String header = sc.nextLine();
            System.out.println("Enquiry body (In one line):");
            String body = sc.nextLine();
            Enquiry enquiry = new Enquiry(applicant, selectedProject, header, body);
            data.getEnquiryList().add(enquiry);
        }
        System.out.println("------------------------------");
    }

    public static void editEnquiry(Applicant applicant, Scanner sc, Data data) {
        Enquiry selectedEnquiry = ArrayControl.selectFromList(applicant.getEnquiries(), sc);
        if (selectedEnquiry != null) {
            System.out.println("Enquiry body (In one line):");
            String body = sc.nextLine();
            selectedEnquiry.editEnquiry(body);
        }
    }

    public static void deleteEnquiry(Applicant applicant, Scanner sc, Data data) {
        Enquiry selectedEnquiry = ArrayControl.selectFromList(applicant.getEnquiries(), sc);
        if (selectedEnquiry != null) {
            selectedEnquiry.deleteEnquiry(data);
            selectedEnquiry = null;
            System.out.println("Enquiry deleted.");
        }
    }

    public static void requestWithdrawal(Applicant applicant, Scanner sc, Data data) {
        if (applicant.getApplication() != null) {
            if (applicant.getApplication().getStatus() != Status.Booked) {
                Withdrawal withdrawal = new Withdrawal(applicant.getApplication(), applicant);
                data.getWithdrawals().add(withdrawal);
            } else {
                System.out.println("HDB already booked and approved!");
            }
        } else {
            System.out.println("No application to withdraw from.");
        }
    }

}
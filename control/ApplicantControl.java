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

/**
 * The ApplicantControl class provides methods for handling actions that an
 * applicant can perform,
 * such as applying for projects, making enquiries, editing or deleting
 * enquiries, and requesting withdrawal.
 * It ensures that applicants can manage their applications and interact with
 * projects within the system.
 */
public class ApplicantControl {

    /**
     * Allows an applicant to apply for a project.
     * The method checks if the applicant is eligible to apply and handles the
     * application process,
     * including selecting a unit type if the applicant is married.
     *
     * @param applicant the applicant applying for the project
     * @param projects  the list of available projects to apply for
     * @param sc        the scanner object to read user input
     * @param data      the data object containing the application data
     */
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
                if (applicant.getMaritalStatus() == MaritalStatus.Married) {
                    selectedUnit = ArrayControl.selectUnitType(selectedProject, sc);
                    if (selectedUnit == null) {
                        System.out.println("------------------------------");
                        return;
                    }
                } else
                    selectedUnit = UnitType.TWO_ROOM;
                Application application = new Application(applicant, selectedProject, selectedUnit);
                data.getApplicationList().add(application);
                System.out.println("Successfully applied for project!");
                System.out.println("------------------------------");
            }
        }
    }

    /**
     * Retrieves a list of projects that the applicant is eligible to apply for,
     * based on their marital status
     * and age. The method applies additional filters, including visibility and date
     * range for the projects.
     *
     * @param applicant the applicant for whom the eligible projects are being
     *                  retrieved
     * @param data      the data object containing the project data
     * @return a list of projects that the applicant can apply for
     */
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

    /**
     * Allows an applicant to make an enquiry about a project.
     * The method prompts the applicant to provide a header and body for the
     * enquiry, which is then added to the enquiry list.
     *
     * @param applicant the applicant making the enquiry
     * @param projects  the list of available projects to enquire about
     * @param sc        the scanner object to read user input
     * @param data      the data object containing the enquiry data
     */
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

    /**
     * Allows an applicant to edit an existing enquiry.
     * The applicant can modify the body of the enquiry, which is then updated in
     * the system.
     *
     * @param applicant the applicant editing the enquiry
     * @param sc        the scanner object to read user input
     * @param data      the data object containing the enquiry data
     */
    public static void editEnquiry(Applicant applicant, Scanner sc, Data data) {
        Enquiry selectedEnquiry = ArrayControl.selectFromList(applicant.getEnquiries(), sc);
        if (selectedEnquiry != null) {
            System.out.println("Enquiry body (In one line):");
            String body = sc.nextLine();
            selectedEnquiry.editEnquiry(body);
        }
    }

    /**
     * Allows an applicant to delete an existing enquiry.
     * If the enquiry is successfully deleted, it is removed from the system and the
     * applicant's list.
     *
     * @param applicant the applicant deleting the enquiry
     * @param sc        the scanner object to read user input
     * @param data      the data object containing the enquiry data
     */
    public static void deleteEnquiry(Applicant applicant, Scanner sc, Data data) {
        Enquiry selectedEnquiry = ArrayControl.selectFromList(applicant.getEnquiries(), sc);
        if (selectedEnquiry != null) {
            selectedEnquiry.deleteEnquiry(data);
            selectedEnquiry = null;
            System.out.println("Enquiry deleted.");
        }
    }

    /**
     * Allows an applicant to request withdrawal from a project they have applied
     * for.
     * If the application is not booked, the withdrawal is processed, and a new
     * withdrawal record is created.
     * Otherwise, an error message is displayed.
     *
     * @param applicant the applicant requesting the withdrawal
     * @param sc        the scanner object to read user input
     * @param data      the data object containing the withdrawal data
     */
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

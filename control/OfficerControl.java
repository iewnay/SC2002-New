package control;

import entity.Officer;
import entity.Registration;
import entity.Project;
import entity.FlatBooking;
import entity.Applicant;
import shared.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The OfficerControl class manages functionalities that are specific to
 * officers,
 * such as viewing registrations, registering for projects, and generating
 * receipts for approved bookings.
 * This class facilitates the operations related to officer interactions with
 * projects and bookings.
 */
public class OfficerControl {

    /**
     * The file path where receipt files are saved.
     */
    public static final String RECEIPT_FILEPATH = "./data/receipts/";

    /**
     * Displays all the registrations associated with the officer.
     * Iterates through the officer's list of registrations and prints each
     * registration.
     *
     * @param officer the officer whose registrations are being viewed
     */
    public static void viewRegistrations(Officer officer) {
        List<Registration> registrations = officer.getRegistrations();
        for (Registration registration : registrations) {
            System.out.println(registration);
        }
    }

    /**
     * Allows an officer to register for a project. The officer is presented with a
     * list of available projects
     * that are not already applied for, assigned, or have no remaining officer
     * slots.
     * Projects that clash with currently assigned projects are also excluded from
     * the list.
     *
     * @param officer the officer who is registering for a project
     * @param sc      the scanner object used to capture user input
     * @param data    the data object containing the list of all projects and
     *                registrations
     */
    public static void registerForProject(Officer officer, Scanner sc, Data data) {
        // Filter out projects that 1. Officer applied for 2. Officer already assigned
        List<Project> filteredProjects = new ArrayList<>(data.getProjectList());
        if (officer.getApplication() != null)
            filteredProjects.remove(officer.getApplication().getProject());
        filteredProjects.removeAll(officer.getAssignedProjects());

        // Filter out projects that clash with currently assigned projects
        List<Project> projects = filteredProjects.stream()
                .filter(project -> officer.getAssignedProjects().stream()
                        .noneMatch(assigned -> project.inDateRange(assigned)))
                .collect(Collectors.toList());

        // Filter out already registered projects
        projects.removeAll(
                officer.getRegistrations().stream()
                        .map(Registration::getProject)
                        .collect(Collectors.toList()));

        // Filter out projects with no officer slots remaining
        projects.removeIf(project -> project.getOfficerSlots() == 0);

        Project selectedProject = ArrayControl.selectFromList(projects, sc, true);
        if (selectedProject != null) {
            Registration registration = new Registration(officer, selectedProject);
            data.getRegistrationList().add(registration);
        }
    }

    /**
     * Generates a receipt for an approved booking within a selected project. The
     * receipt includes information
     * about the applicant, the flat type, the project details, and the associated
     * neighbourhood.
     * The receipt is saved to a text file with a filename based on the current date
     * and the applicant's NRIC.
     *
     * @param project the project associated with the approved booking
     * @param sc      the scanner object used to capture user input for selecting
     *                the approved booking
     */
    public static void generateReceipt(Project project, Scanner sc) {
        FlatBooking approvedBooking = ArrayControl.selectFromList(project.getApprovedBookings(), sc);
        Applicant applicant = approvedBooking.getApplicant();
        String s = "";
        s += applicant.toString(true);
        s += "\nFlat Type\t:" + approvedBooking.getApplication().getUnitType();
        s += "\nProject\t: " + project.getName();
        s += "\nNeighbourhood\t: " + project.getNeighbourhood();
        s += project;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);

        String filename = formattedDateTime + "_" + applicant.getNRIC().substring(5) + ".txt";

        try (FileWriter writer = new FileWriter(RECEIPT_FILEPATH + filename)) {
            writer.write(s);
            System.out.println(s);
            System.out.println();
            System.out.println("Receipt generated - " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

}

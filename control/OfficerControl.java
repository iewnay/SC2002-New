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

public class OfficerControl {
    public static final String RECEIPT_FILEPATH = "./data/receipts/";

    public static void viewRegistrations(Officer officer) {
        List<Registration> registrations = officer.getRegistrations();
        for (Registration registration : registrations) {
            System.out.println(registration);
        }
    }

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

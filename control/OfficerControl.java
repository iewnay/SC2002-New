package control;

import entity.*;
import shared.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OfficerControl {
    public static void viewRegistrations(Officer officer) {
        List<Registration> registrations = officer.getRegistrations();
        for (Registration registration : registrations) {
            System.out.println(registration);
        }
    }

    public static void registerForProject(Officer officer, Scanner sc, Data data) {
        // Filter out projects that 1. Officer applied for 2. Officer already assigned
        List<Project> filteredProjects = new ArrayList<>(data.projectList);
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
            data.registrationList.add(registration);
        }
    }
}

package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.MaritalStatus;

public class Manager extends User {
    // Attributes
    private List<Project> managedProjects;

    // Constructor
    public Manager(String NRIC, String name, int age, MaritalStatus maritalStatus) {
        super(NRIC, name, age, maritalStatus);
        this.managedProjects = new ArrayList<>();
    }

    // Getters and Setters
    public List<Project> getProjects() {
        return this.managedProjects;
    }

    // Methods
    public boolean createProject(String name, String neighbourhood, List<Unit> units,
            LocalDate applicationOpenDate, LocalDate applicationClosingDate, boolean visibility,
            int officerSlots, List<Project> projectsList) {
        // check if project clashes with any other project
        for (Project project : this.managedProjects) {
            if (project.inDateRange(applicationOpenDate, applicationClosingDate)) {
                return false;
            }
        }
        // if new project does not clash with any others, create the object and form
        // link
        Project tempProject = new Project(name, neighbourhood, units, applicationOpenDate,
                applicationClosingDate, visibility, officerSlots);
        tempProject.setManager(this);
        projectsList.add(tempProject);
        this.managedProjects.add(tempProject);
        return true;
    }

    public boolean editProject(String name, String neighbourhood, List<Unit> units,
            LocalDate applicationOpenDate, LocalDate applicationClosingDate, boolean visibility,
            int officerSlots, Project currentProject) {
        // create temporary list of managedProjects that excludes currentProject
        List<Project> projects = new ArrayList<>(this.managedProjects);
        projects.remove(currentProject);

        // check if project clashes with any other project
        for (Project project : projects) {
            if (project.inDateRange(applicationOpenDate, applicationClosingDate)) {
                return false;
            }
        }

        // if edited project does not clash with any others, edit the attributes
        currentProject.setName(name);
        currentProject.setNeighbourhood(neighbourhood);
        currentProject.setUnits(units);
        currentProject.setApplicationOpenDate(applicationClosingDate);
        currentProject.setApplicationClosingDate(applicationClosingDate);
        return true;
    }
}
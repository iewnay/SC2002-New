package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.MaritalStatus;

/**
 * A manager user in the BTO system.
 * A manager can manage multiple projects, but only one at a time.
 */
public class Manager extends User {

    // Attributes
    private List<Project> managedProjects;

    // Constructor

    /**
     * Constructs a Manager with the specified personal details.
     * Initializes an empty list for the managed projects.
     *
     * @param NRIC          The NRIC of the manager.
     * @param name          The name of the manager.
     * @param age           The age of the manager.
     * @param maritalStatus The marital status of the manager.
     */
    public Manager(String NRIC, String name, int age, MaritalStatus maritalStatus) {
        super(NRIC, name, age, maritalStatus);
        this.managedProjects = new ArrayList<>();
    }

    // Getters and Setters
    public List<Project> getProjects() {
        return this.managedProjects;
    }

    // Methods

    /**
     * Creates a new project if it does not clash with existing projects.
     * The new project is added to both the list of managed projects and the global
     * projects list.
     *
     * @param name                   The name of the project.
     * @param neighbourhood          The neighbourhood of the project.
     * @param units                  The list of units available in the project.
     * @param applicationOpenDate    The opening date of the application period.
     * @param applicationClosingDate The closing date of the application period.
     * @param visibility             The visibility status of the project.
     * @param officerSlots           The number of officer slots available for the
     *                               project.
     * @param projectsList           The global list of projects to add the new
     *                               project to.
     * @return true if the project is created successfully, false if it clashes with
     *         another project.
     */
    public boolean createProject(String name, String neighbourhood, List<Unit> units,
            LocalDate applicationOpenDate, LocalDate applicationClosingDate, boolean visibility,
            int officerSlots, List<Project> projectsList) {
        // check if project clashes with any other project
        for (Project project : this.managedProjects) {
            if (project.inDateRange(applicationOpenDate, applicationClosingDate)) {
                return false;
            }
        }
        // if new project does not clash with any others, create the object and form the
        // link
        Project tempProject = new Project(name, neighbourhood, units, applicationOpenDate,
                applicationClosingDate, visibility, officerSlots);
        tempProject.setManager(this);
        projectsList.add(tempProject);
        this.managedProjects.add(tempProject);
        return true;
    }

    /**
     * Edits an existing project if it does not clash with other projects.
     *
     * @param name                   The new name for the project.
     * @param neighbourhood          The new neighbourhood for the project.
     * @param units                  The new list of units available in the project.
     * @param applicationOpenDate    The new opening date of the application period.
     * @param applicationClosingDate The new closing date of the application period.
     * @param visibility             The new visibility status of the project.
     * @param officerSlots           The new number of officer slots available for
     *                               the project.
     * @param currentProject         The project to be edited.
     * @return true if the project is edited successfully, false if it clashes with
     *         another project.
     */
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
        currentProject.setApplicationOpenDate(applicationOpenDate);
        currentProject.setApplicationClosingDate(applicationClosingDate);
        return true;
    }
}

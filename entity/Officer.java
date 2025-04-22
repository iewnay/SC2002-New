package entity;

import enums.MaritalStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * An officer user in the BTO system.
 * An officer is a type of applicant that has the added responsibilities of
 * assigned projects and registrations.
 */
public class Officer extends Applicant {

    // Attributes
    private List<Project> assignedProjects;
    private List<Registration> registrations;

    // Constructor

    /**
     * Constructs an Officer with the specified personal details.
     * Initializes empty lists for assigned projects and registrations.
     *
     * @param NRIC          The NRIC of the officer.
     * @param name          The name of the officer.
     * @param age           The age of the officer.
     * @param maritalStatus The marital status of the officer.
     */
    public Officer(String NRIC, String name, int age, MaritalStatus maritalStatus) {
        super(NRIC, name, age, maritalStatus);
        this.assignedProjects = new ArrayList<>();
        this.registrations = new ArrayList<>();
    }

    // Getters and Setters
    public List<Project> getAssignedProjects() {
        return this.assignedProjects;
    }

    public List<Registration> getRegistrations() {
        return this.registrations;
    }
}

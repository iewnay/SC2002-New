package entity;

import enums.MaritalStatus;

import java.util.ArrayList;
import java.util.List;

public class Officer extends Applicant {
    // Attributes
    private List<Project> assignedProjects;
    private List<Registration> registrations;

    // Constructor
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
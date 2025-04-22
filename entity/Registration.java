package entity;

import java.io.Serializable;

import shared.Data;
import enums.Status;

/**
 * A registration of an officer to a project with a specific status.
 */
public class Registration implements Serializable {

    private Officer officer;
    private Project project;
    private Status status;

    /**
     * Constructor for creating a new registration with the specified officer and
     * project.
     * The initial status is set to Pending.
     * 
     * @param officer The officer involved in the registration.
     * @param project The project the officer is being registered for.
     */
    public Registration(Officer officer, Project project) {
        this.officer = officer;
        this.project = project;
        this.status = Status.Pending;
        this.officer.getRegistrations().add(this);
        this.project.getRegistrations().add(this);
    }

    // Getters and Setters

    public Officer getOfficer() {
        return officer;
    }

    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Methods

    /**
     * Rejects the registration, setting the status to Unsuccessful and removing
     * the registration from the project.
     */
    public void reject() {
        this.status = Status.Unsuccessful;
        this.project.getRegistrations().remove(this);
    }

    /**
     * Approves the registration by adding the officer to the project and setting
     * the status to Successful. Removes the registration from the project once
     * approved.
     * 
     * @param officer The officer to approve for the project.
     * @return True if the officer was successfully added to the project, false
     *         otherwise.
     */
    public boolean approve(Officer officer) {
        if (!this.project.addOfficer(officer)) {
            return false;
        }
        this.status = Status.Successful;
        this.officer.getAssignedProjects().add(this.project);
        this.project.getRegistrations().remove(this);
        return true;
    }

    /**
     * Deletes the registration, removing it from both the officer and project
     * registrations, as well as from the data's registration list.
     * 
     * @param data The data object that holds the list of registrations.
     */
    public void deleteRegistration(Data data) {
        this.officer.getRegistrations().remove(this);
        this.project.getRegistrations().remove(this);
        data.getRegistrationList().remove(this);
    }

    /**
     * Returns a string representation of the registration, including the project,
     * officer, and registration status.
     * 
     * @return A string representation of the registration.
     */
    @Override
    public String toString() {
        return this.project.toString() + "\nOfficer:\n" + this.officer.toString() +
                "\nRegistration Status : " + this.status;
    }
}

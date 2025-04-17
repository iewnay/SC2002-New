package entity;

import java.io.Serializable;

import shared.Data;
import enums.Status;

public class Registration implements Serializable {
    private Officer officer;
    private Project project;
    private Status status;

    // Constructor
    public Registration(Officer officer, Project project) {
        this.officer = officer;
        this.project = project;
        this.status = Status.Pending;
        this.officer.getRegistrations().add(this);
        this.project.getRegistrations().add(this);
    }

    // Getters
    public Officer getOfficer() {
        return officer;
    }

    public Project getProject() {
        return project;
    }

    public Status getStatus() {
        return status;
    }

    // Setters
    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void reject() {
        this.status = Status.Unsuccessful;
        this.project.getRegistrations().remove(this);
    }

    public boolean approve(Officer officer) {
        if (!this.project.addOfficer(officer)) {
            return false;
        }
        this.status = Status.Successful;
        this.officer.getAssignedProjects().add(this.project);
        this.project.getRegistrations().remove(this);
        return true;
    }

    public void deleteRegistration(Data data) {
        this.officer.getRegistrations().remove(this);
        this.project.getRegistrations().remove(this);
        data.getRegistrationList().remove(this);
    }
 
    @Override
    public String toString() {
        return this.project.toString() + "\nOfficer:\n" + this.officer.toString() +
                "\nRegistration Status : " + this.status;
    }
}

package entity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import shared.Data;
import enums.UnitType;
import enums.Status;

public class Application implements Serializable {
    // Attributes
    private Applicant applicant;
    private Project project;
    private UnitType unitType;
    private Status status; // Unsuccessful, Pending, Successful, Booked, NULL

    // Contrsuctor
    public Application(Applicant applicant, Project project, UnitType unitType) {
        this.applicant = applicant;
        this.project = project;
        this.unitType = unitType;
        this.status = Status.Pending;
        this.applicant.setApplication(this);
        this.project.addApplication(this);
    }

    // Getters and Setters
    public Applicant getApplicant() {
        return this.applicant;
    }

    public Project getProject() {
        return this.project;
    }

    public Status getStatus() {
        return this.status;
    }

    public UnitType getUnitType() {
        return this.unitType;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Methods
    public void approve() {
        this.status = Status.Successful;
    }

    public void reject() {
        this.status = Status.Unsuccessful;
        this.project.getApplications().remove(this);
    }

    public void book(Data data) {
        FlatBooking booking = new FlatBooking(this);
        data.getFlatBookings().add(booking);
        this.project.getApplications().remove(this);
    }

    public static List<Application> filterPending(List<Application> applications) {
        return applications.stream().filter(p -> p.getStatus() == Status.Pending)
                .collect(Collectors.toList());
    }

    public void deleteApplication(Data data) {
        this.applicant.clearApplication();
        data.getApplicationList().remove(this);
    }

    @Override
    public String toString() {
        String s = "";
        s += "Applied Project:\n" +
                project.toString(false) + "\n" +
                "Current Status: " + status;
        s += "\n\n" + applicant.toString(true);
        return s;
    }
}
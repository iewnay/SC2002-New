package entity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import shared.Data;
import enums.UnitType;
import enums.Status;

/**
 * An application made by an applicant for a project.
 * Application includes the status and the associated project.
 */
public class Application implements Serializable {

    // Attributes
    private Applicant applicant;
    private Project project;
    private UnitType unitType;
    private Status status; // Unsuccessful, Pending, Successful, Booked, NULL

    // Constructor

    /**
     * Constructs an application for a given applicant and project.
     * The application is set to "Pending" status by default.
     *
     * @param applicant   The applicant who is applying.
     * @param project     The project the applicant is applying for.
     * @param unitType    The unit type the applicant is interested in.
     */
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

    /**
     * Approves the application, changing its status to "Successful".
     */
    public void approve() {
        this.status = Status.Successful;
    }

    /**
     * Rejects the application, changing its status to "Unsuccessful".
     * The application is also removed from the project's application list.
     */
    public void reject() {
        this.status = Status.Unsuccessful;
        this.project.getApplications().remove(this);
    }

    /**
     * Books the application by creating a new FlatBooking.
     * The application is removed from the project's application list and added to the flat booking list.
     *
     * @param data The data containing the list of flat bookings.
     */
    public void book(Data data) {
        FlatBooking booking = new FlatBooking(this);
        data.getFlatBookings().add(booking);
        this.project.getApplications().remove(this);
    }

    /**
     * Filters and returns all pending applications from a list of applications.
     *
     * @param applications The list of applications to filter.
     * @return A list of applications with "Pending" status.
     */
    public static List<Application> filterPending(List<Application> applications) {
        return applications.stream().filter(p -> p.getStatus() == Status.Pending)
                .collect(Collectors.toList());
    }

    /**
     * Deletes the application by clearing the applicant's application and removing the application from the data.
     *
     * @param data The data containing the list of applications.
     */
    public void deleteApplication(Data data) {
        this.applicant.clearApplication();
        data.getApplicationList().remove(this);
    }

    /**
     * Returns a string representation of the application, including details about the project and the applicant.
     *
     * @return A formatted string showing the applied project and current status.
     */
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

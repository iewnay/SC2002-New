package entity;

import java.io.Serializable;

import enums.UnitType;
import shared.Data;

/**
 * A booking made by an applicant for a unit in a project.
 * The booking links an applicant, application, and project, and tracks its
 * booking status.
 */
public class FlatBooking implements Serializable {

    // Attributes
    private Applicant applicant;
    private Application application;
    private Project project;
    private boolean booked;

    // Constructor

    /**
     * Constructs a FlatBooking object using an application, and sets up the related
     * applicant and project.
     * The booking is initially marked as not booked, and the applicant and project
     * are updated accordingly.
     *
     * @param application The application associated with this booking.
     */
    public FlatBooking(Application application) {
        this.applicant = application.getApplicant();
        this.application = application;
        this.project = application.getProject();
        this.booked = false;
        this.applicant.setBooking(this);
        this.project.addBooking(this);
    }

    // Getters and Setters

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public Application getApplication() {
        return this.application;
    }

    public UnitType getUnitType() {
        return this.application.getUnitType();
    }

    // Methods

    /**
     * Checks if the booking has been confirmed.
     *
     * @return true if the booking is confirmed, false otherwise.
     */
    public boolean isBooked() {
        return booked;
    }

    /**
     * Deletes the booking, removing the relationship between the applicant,
     * project, and the global data.
     *
     * @param data The data object containing the list of all bookings.
     */
    public void deleteBooking(Data data) {
        this.applicant.setBooking(null);
        this.project.getBookings().remove(this);
        data.getFlatBookings().remove(this);
    }

    /**
     * Returns a string representation of the flat booking, displaying the details
     * of the associated application.
     *
     * @return A formatted string representing the booking details.
     */
    @Override
    public String toString() {
        return this.application.toString();
    }
}

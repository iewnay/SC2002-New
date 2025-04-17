package entity;

import java.io.Serializable;

import enums.UnitType;
import shared.Data;

public class FlatBooking implements Serializable {
    private Applicant applicant;
    private Application application;
    private Project project;
    private boolean booked;

    public FlatBooking(Application application) {
        this.applicant = application.getApplicant();
        this.application = application;
        this.project = application.getProject();
        this.booked = false;
        this.applicant.setBooking(this);
        this.project.addBooking(this);
    }

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

    public boolean isBooked() {
        return booked;
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

    public void deleteBooking(Data data) {
        this.applicant.setBooking(null);
        this.project.getBookings().remove(this);
        data.getFlatBookings().remove(this);
    }

    @Override
    public String toString() {
        return this.application.toString();
    }
}

package entity;

import java.util.ArrayList;
import java.util.List;

import enums.UnitType;
import enums.MaritalStatus;

public class Applicant extends User {
    // Attributes
    private Application application;
    private FlatBooking booking;
    private List<Enquiry> enquiries;
    private UnitType unitType;
    private Withdrawal withdrawalRequest;

    // Constructor
    public Applicant(String NRIC, String name, int age, MaritalStatus maritalStatus) {
        super(NRIC, name, age, maritalStatus);
        this.application = null;
        this.booking = null;
        this.enquiries = new ArrayList<>();
        this.withdrawalRequest = null;
    }

    // Getters and Setters
    public Application getApplication() {
        return this.application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public FlatBooking getBooking() {
        return this.booking;
    }

    public void setBooking(FlatBooking booking) {
        this.booking = booking;
    }

    public List<Enquiry> getEnquiries() {
        return this.enquiries;
    }

    public UnitType getUnitType() {
        return this.unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public Withdrawal getWithdrawal() {
        return this.withdrawalRequest;
    }

    public void setWithdrawal(Withdrawal withdrawal) {
        this.withdrawalRequest = withdrawal;
    }

    // Methods
    public void addEnquiry(Enquiry enquiry) {
        this.enquiries.add(enquiry);
    }

    public boolean hasApplication() {
        return this.application != null;
    }

    public void clearApplication() {
        this.application.getProject().getApplications().remove(this.application);
        this.application = null;
    }

    public void removeEnquiry(Enquiry enquiry) {
        this.enquiries.remove(enquiry);
    }
}

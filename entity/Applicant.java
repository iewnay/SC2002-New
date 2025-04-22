package entity;

import java.util.ArrayList;
import java.util.List;

import enums.UnitType;
import enums.MaritalStatus;

/**
 * An applicant user in the BTO system.
 * Extends User class and manages own data related to applications, bookings,
 * and enquiries.
 */
public class Applicant extends User {

    // Attributes
    private Application application;
    private FlatBooking booking;
    private List<Enquiry> enquiries;
    private UnitType unitType;
    private Withdrawal withdrawalRequest;

    // Constructor

    /**
     * Constructs an Applicant with the specified personal details.
     *
     * @param NRIC          The NRIC of the applicant.
     * @param name          The name of the applicant.
     * @param age           The age of the applicant.
     * @param maritalStatus The marital status of the applicant.
     */
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

    /**
     * Adds an enquiry to the applicant's enquiry list.
     *
     * @param enquiry The enquiry to add.
     */
    public void addEnquiry(Enquiry enquiry) {
        this.enquiries.add(enquiry);
    }

    /**
     * Checks whether the applicant has submitted an application.
     *
     * @return true if an application exists, false otherwise.
     */
    public boolean hasApplication() {
        return this.application != null;
    }

    /**
     * Clears the applicant's application and removes it from the associated
     * project.
     */
    public void clearApplication() {
        this.application.getProject().getApplications().remove(this.application);
        this.application = null;
    }

    /**
     * Removes a specific enquiry from the applicant's enquiry list.
     *
     * @param enquiry The enquiry to remove.
     */
    public void removeEnquiry(Enquiry enquiry) {
        this.enquiries.remove(enquiry);
    }
}

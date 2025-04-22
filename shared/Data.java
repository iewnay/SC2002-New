package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entity.*;

/**
 * A Data Transfer Object (DTO), or container, for data collections.
 * Stores lists of managers, officers, applicants, projects, and entity objects.
 */
public class Data implements Serializable {

    // Attributes
    private List<Manager> managerList = new ArrayList<>();
    private List<Officer> officerList = new ArrayList<>();
    private List<Applicant> applicantList = new ArrayList<>();
    private List<Project> projectList = new ArrayList<>();
    private List<Application> applicationList = new ArrayList<>();
    private List<Registration> registrationList = new ArrayList<>();
    private List<Enquiry> enquiryList = new ArrayList<>();
    private List<FlatBooking> flatBookings = new ArrayList<>();
    private List<Withdrawal> withdrawals = new ArrayList<>();
    private List<String> managerCodes = new ArrayList<>();
    private List<String> officerCodes = new ArrayList<>();

    // Getters and Setters
    public List<Manager> getManagerList() {
        return managerList;
    }

    public List<Officer> getOfficerList() {
        return officerList;
    }

    public List<Applicant> getApplicantList() {
        return applicantList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public List<Application> getApplicationList() {
        return applicationList;
    }

    public List<Registration> getRegistrationList() {
        return registrationList;
    }

    public List<Enquiry> getEnquiryList() {
        return enquiryList;
    }

    public List<FlatBooking> getFlatBookings() {
        return flatBookings;
    }

    public List<Withdrawal> getWithdrawals() {
        return withdrawals;
    }

    public List<String> getManagerCodes() {
        return managerCodes;
    }

    public List<String> getOfficerCodes() {
        return officerCodes;
    }

    // Methods

    /**
     * Adds a manager code to the list if it does not already exist.
     *
     * @param code The manager code to add.
     */
    public void addManagerCode(String code) {
        if (!managerCodes.contains(code)) {
            managerCodes.add(code);
        }
    }

    /**
     * Removes a manager code from the list if it exists.
     *
     * @param code The manager code to remove.
     */
    public void removeManagerCode(String code) {
        if (managerCodes.contains(code)) {
            managerCodes.remove(code);
        }
    }

    /**
     * Adds an officer code to the list if it does not already exist.
     *
     * @param code The officer code to add.
     */
    public void addOfficerCode(String code) {
        if (!officerCodes.contains(code)) {
            officerCodes.add(code);
        }
    }

    /**
     * Removes an officer code from the list if it exists.
     *
     * @param code The officer code to remove.
     */
    public void removeOfficerCode(String code) {
        if (officerCodes.contains(code)) {
            officerCodes.remove(code);
        }
    }

    /**
     * Checks the type of a given code.
     *
     * @param code The code to check.
     * @return "Manager" if the code is a manager code, "Officer" if it's an officer
     *         code, default "Applicant".
     */
    public String checkCode(String code) {
        if (managerCodes.contains(code))
            return "Manager";
        if (officerCodes.contains(code))
            return "Officer";
        return "Applicant";
    }

    /**
     * Adds a manager to the manager list.
     *
     * @param manager The manager to add.
     */
    public void addManager(Manager manager) {
        managerList.add(manager);
    }

    /**
     * Adds an officer to the officer list.
     *
     * @param officer The officer to add.
     */
    public void addOfficer(Officer officer) {
        officerList.add(officer);
    }

    /**
     * Adds an applicant to the applicant list.
     *
     * @param applicant The applicant to add.
     */
    public void addApplicant(Applicant applicant) {
        applicantList.add(applicant);
    }
}

package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entity.*;

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
    public void addManagerCode(String code) {
        if (!managerCodes.contains(code)) {
            managerCodes.add(code);
        }
    }

    public void removeManagerCode(String code) {
        if (managerCodes.contains(code)) {
            managerCodes.remove(code);
        }
    }

    public void addOfficerCode(String code) {
        if (!officerCodes.contains(code)) {
            officerCodes.add(code);
        }
    }

    public void removeOfficerCode(String code) {
        if (officerCodes.contains(code)) {
            officerCodes.remove(code);
        }
    }

    public String checkCode(String code) {
        if (managerCodes.contains(code))
            return "Manager";
        if (officerCodes.contains(code))
            return "Officer";
        return "Applicant";
    }

    public void addManager(Manager manager) {
        managerList.add(manager);
    }

    public void addOfficer(Officer officer) {
        officerList.add(officer);
    }

    public void addApplicant(Applicant applicant) {
        applicantList.add(applicant);
    }
}

package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entity.*;

public class Data implements Serializable{
    public List<Manager> managerList = new ArrayList<>();
    public List<Officer> officerList = new ArrayList<>();
    public List<Applicant> applicantList = new ArrayList<>();
    public List<Project> projectList = new ArrayList<>();
    public List<Application> applicationList = new ArrayList<>();
    public List<Registration> registrationList = new ArrayList<>();
    public List<Enquiry> enquiryList = new ArrayList<>();
    public List<FlatBooking> flatBookings = new ArrayList<>();
    public List<Withdrawal> withdrawals = new ArrayList<>();
}

package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import enums.Status;
import enums.UnitType;
import shared.Data;

public class Project implements Serializable {
    // Attributes
    private Manager manager;
    private String name;
    private String neighbourhood;
    private List<Unit> units;
    private LocalDate applicationOpenDate;
    private LocalDate applicationClosingDate;
    private boolean visibility;
    private int officerSlots;
    private List<Officer> assignedOfficers;
    private List<Registration> registrations;
    private List<Application> applications;
    private List<Enquiry> enquiries;
    private List<FlatBooking> bookings;

    // Constructor
    public Project(String name, String neighbourhood,
            List<Unit> units, LocalDate applicationOpenDate,
            LocalDate applicationClosingDate, boolean visibility, int officerSlots) {
        this.name = name;
        this.neighbourhood = neighbourhood;
        this.units = units;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationClosingDate = applicationClosingDate;
        this.visibility = visibility; // can be set to default true? or based on input
        this.officerSlots = officerSlots;
        this.assignedOfficers = new ArrayList<>();
        this.registrations = new ArrayList<>();
        this.applications = new ArrayList<>();
        this.enquiries = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // Getters and Setters
    public Manager getManager() {
        return manager;
    }

    public boolean setManager(Manager manager) {
        if (this.manager != null) {
            this.manager = manager;
            return true;
        }
        return false; // if manager already exists, don't overtake (can change)
    }

    public List<FlatBooking> getBookings() {
        return this.bookings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public LocalDate getApplicationOpenDate() {
        return this.applicationOpenDate;
    }

    public LocalDate getApplicationClosingDate() {
        return this.applicationClosingDate;
    }

    public List<Unit> getUnits() {
        return this.units;
    }

    public int getTotalUnits() {
        int total = 0;
        for (Unit unit : this.units) {
            total += unit.getUnitsAvailable();
        }
        return total;
    }

    public double getAveragePrice() {
        int count = 0;
        double price = 0.0;
        for (Unit unit : this.units) {
            price += unit.getSellingPrice();
            count++;
        }
        return price / count;
    }

    public void setApplicationOpenDate(LocalDate date) {
        this.applicationOpenDate = date;
    }

    public void setApplicationClosingDate(LocalDate date) {
        this.applicationClosingDate = date;
    }

    public boolean getVisibility() {
        return this.visibility;
    }

    public List<Registration> getRegistrations() {
        return this.registrations;
    }

    public List<Application> getApplications() {
        return this.applications;
    }

    public void addApplication(Application application) {
        this.applications.add(application);
    }

    public void addEnquiry(Enquiry enquiry) {
        this.enquiries.add(enquiry);
    }

    public void addBooking(FlatBooking booking) {
        this.bookings.add(booking);
    }

    public List<Enquiry> getEnquiries() {
        return this.enquiries;
    }

    public int getOfficerSlots() {
        return this.officerSlots;
    }

    public boolean decrementUnits(UnitType unitType) {
        Unit unit = this.units.stream()
                .filter(p -> unitType.equals(p.getUnitType()))
                .findFirst()
                .orElse(null);
        if (unit == null) {
            return false;
        }
        return unit.decrementUnits();
    }

    public boolean addOfficer(Officer officer) {
        if (this.officerSlots == 0) {
            return false;
        }
        this.officerSlots = this.officerSlots - 1;
        this.assignedOfficers.add(officer);
        return true;
    }

    public boolean hasTwoRoom() {
        for (Unit unit : this.units) {
            if (unit.getUnitType() == UnitType.TWO_ROOM) {
                return true;
            }
        }
        return false;
    }

    public List<Enquiry> getUnansweredEnquiries() {
        List<Enquiry> unanswered = new ArrayList<>();
        for (Enquiry enquiry : this.enquiries) {
            if (!enquiry.isAnswered()) {
                unanswered.add(enquiry);
            }
        }
        return unanswered;
    }

    public List<Application> getApprovedApplications() {
        List<Application> approved = new ArrayList<>();
        for (Application application : this.applications) {
            if (application.getStatus() == Status.Successful) {
                approved.add(application);
            }
        }
        return approved;
    }

    public List<FlatBooking> getSuccessfulBookings() {
        List<FlatBooking> successful = new ArrayList<>();
        for (FlatBooking booking : this.bookings) {
            if (booking.isBooked()) {
                successful.add(booking);
            }
        }
        return successful;
    }

    public List<FlatBooking> getPendingBookings() {
        List<FlatBooking> pending = new ArrayList<>();
        for (FlatBooking booking : this.bookings) {
            if (!booking.isBooked()) {
                pending.add(booking);
            }
        }
        return pending;
    }

    public List<FlatBooking> getApprovedBookings() {
        List<FlatBooking> approved = new ArrayList<>();
        for (FlatBooking booking : this.bookings) {
            if (booking.isBooked()) {
                approved.add(booking);
            }
        }
        return approved;
    }

    // Default sort
    public static void sortProjects(List<Project> projects) {
        // Re-Sort projects
        projects.sort(Comparator.comparing(Project::getName));
    }

    // Sort based on user settings
    public static <T extends User> void sortProjects(List<Project> projects, T user) {
        // Re-Sort projects
        switch (user.getSortSetting()) {
            case 1:
                projects.sort(Comparator.comparing(Project::getName));
                break;
            case 2:
                projects.sort(Comparator.comparing(Project::getNeighbourhood));
                break;
            case 3:
                projects.sort(Comparator.comparing(Project::getTotalUnits));
                break;
            case 4:
                projects.sort(Comparator.comparing(Project::getAveragePrice));
                break;
            default:
                projects.sort(Comparator.comparing(Project::getName));
        }

        if (user.getReverseSort())
            Collections.reverse(projects);
    }

    public boolean inDateRange() {
        LocalDate today = LocalDate.now();
        return !(today.isAfter(applicationClosingDate) || today.isBefore(this.applicationOpenDate));
    }

    public boolean inDateRange(Project project) {
        return !(project.getApplicationOpenDate().isAfter(this.applicationClosingDate)
                || project.getApplicationClosingDate().isBefore(this.applicationOpenDate));
    }

    public boolean inDateRange(LocalDate startDate, LocalDate endDate) {
        return !(startDate.isAfter(this.applicationClosingDate) || endDate.isBefore(this.applicationOpenDate));
    }

    public void toggleVisibility() {
        this.visibility = !visibility;
    }

    public void removeEnquiry(Enquiry enquiry) {
        this.enquiries.remove(enquiry);
    }


    public void deleteProject(Data data) {
        for (Registration registration : this.registrations) {
            registration.deleteRegistration(data);
        }
        for (Officer officer : this.assignedOfficers) {
            officer.getAssignedProjects().remove(this);
        }
        for (Application application: this.applications) {
            application.deleteApplication(data);
        }
        for (FlatBooking booking: this.bookings) {
            booking.deleteBooking(data);
        }
        for (Enquiry enquiry: this.enquiries) {
            enquiry.deleteEnquiry(data);
        }
        this.manager.getProjects().remove(this);
        data.getProjectList().remove(this);
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean showAdditionalInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Project Name\t\t: ").append(this.name).append("\n");
        sb.append("Neighbourhood\t\t: ").append(this.neighbourhood).append("\n");
        for (Unit unit : this.units) {
            sb.append(unit.getUnitType()).append(" Flats Available\t: ")
                    .append(unit.getUnitsAvailable()).append("\n");
            sb.append(unit.getUnitType()).append(" Selling Price\t: $")
                    .append(unit.getSellingPrice()).append("\n");
        }
        sb.append("Open Date:\t\t: ").append(this.applicationOpenDate).append("\n");
        sb.append("Close Date:\t\t: ").append(this.applicationClosingDate).append("\n");
        if (showAdditionalInfo) {
            sb.append("Visibility\t\t: ").append(this.visibility).append("\n");
            sb.append("Officer Slots\t\t: ").append(this.officerSlots).append("\n");
            sb.append("Assigned Officers\t: ").append(this.assignedOfficers).append("\n");
            sb.append("Officer Registrations\t: ").append(this.registrations.size()).append("\n");
            sb.append("Applications\t\t: ").append(this.applications.size()).append("\n");
            sb.append("Pending Bookings\t: ").append(getPendingBookings().size()).append("\n");
            sb.append("Successful Bookings\t: ").append(getSuccessfulBookings().size()).append("\n");
            sb.append("Enquiries\t\t: ").append(this.enquiries.size()).append("\n");
            sb.append("Unanswered Enquiries\t: ").append(getUnansweredEnquiries().size()).append("\n");
        }
        return sb.toString();
    }

}

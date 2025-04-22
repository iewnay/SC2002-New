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

/**
 * A project with various attributes, including information about units,
 * applications, bookings, and more.
 * This class provides methods for managing and interacting with project data.
 */
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

    /**
     * Constructor to initialize a Project.
     *
     * @param name                   The name of the project.
     * @param neighbourhood          The neighbourhood where the project is located.
     * @param units                  A list of units in the project.
     * @param applicationOpenDate    The date the application opens.
     * @param applicationClosingDate The date the application closes.
     * @param visibility             The visibility status of the project.
     * @param officerSlots           The number of officer slots available.
     */
    public Project(String name, String neighbourhood,
            List<Unit> units, LocalDate applicationOpenDate,
            LocalDate applicationClosingDate, boolean visibility, int officerSlots) {
        this.name = name;
        this.neighbourhood = neighbourhood;
        this.units = units;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationClosingDate = applicationClosingDate;
        this.visibility = visibility;
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
        return false;
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

    public List<Enquiry> getEnquiries() {
        return this.enquiries;
    }

    public int getOfficerSlots() {
        return this.officerSlots;
    }

    // Methods

    /**
     * Adds an application to the project.
     *
     * @param application The application to be added.
     */
    public void addApplication(Application application) {
        this.applications.add(application);
    }

    /**
     * Adds an enquiry to the project.
     *
     * @param enquiry The enquiry to be added.
     */
    public void addEnquiry(Enquiry enquiry) {
        this.enquiries.add(enquiry);
    }

    /**
     * Adds a booking to the project.
     *
     * @param booking The booking to be added.
     */
    public void addBooking(FlatBooking booking) {
        this.bookings.add(booking);
    }

    /**
     * Decrements the number of units available for a given unit type.
     *
     * @param unitType The unit type whose units are to be decremented.
     * @return True if the units were decremented, false otherwise.
     */
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

    /**
     * Adds an officer to the project if there are available officer slots.
     *
     * @param officer The officer to be added.
     * @return True if the officer was successfully added, false otherwise.
     */
    public boolean addOfficer(Officer officer) {
        if (this.officerSlots == 0) {
            return false;
        }
        this.officerSlots = this.officerSlots - 1;
        this.assignedOfficers.add(officer);
        return true;
    }

    /**
     * Checks if the project has at least one two-room unit.
     *
     * @return True if there is a two-room unit, false otherwise.
     */
    public boolean hasTwoRoom() {
        for (Unit unit : this.units) {
            if (unit.getUnitType() == UnitType.TWO_ROOM) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of unanswered enquiries for the project.
     *
     * @return The list of unanswered enquiries.
     */
    public List<Enquiry> getUnansweredEnquiries() {
        List<Enquiry> unanswered = new ArrayList<>();
        for (Enquiry enquiry : this.enquiries) {
            if (!enquiry.isAnswered()) {
                unanswered.add(enquiry);
            }
        }
        return unanswered;
    }

    /**
     * Gets the list of approved applications for the project.
     *
     * @return The list of approved applications.
     */
    public List<Application> getApprovedApplications() {
        List<Application> approved = new ArrayList<>();
        for (Application application : this.applications) {
            if (application.getStatus() == Status.Successful) {
                approved.add(application);
            }
        }
        return approved;
    }

    /**
     * Gets the list of successful bookings for the project.
     *
     * @return The list of successful bookings.
     */
    public List<FlatBooking> getSuccessfulBookings() {
        List<FlatBooking> successful = new ArrayList<>();
        for (FlatBooking booking : this.bookings) {
            if (booking.isBooked()) {
                successful.add(booking);
            }
        }
        return successful;
    }

    /**
     * Gets the list of pending bookings for the project.
     *
     * @return The list of pending bookings.
     */
    public List<FlatBooking> getPendingBookings() {
        List<FlatBooking> pending = new ArrayList<>();
        for (FlatBooking booking : this.bookings) {
            if (!booking.isBooked()) {
                pending.add(booking);
            }
        }
        return pending;
    }

    /**
     * Gets the list of approved bookings for the project.
     *
     * @return The list of approved bookings.
     */
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
    /**
     * Sorts a list of projects by project name.
     *
     * @param projects The list of projects to be sorted.
     */
    public static void sortProjects(List<Project> projects) {
        projects.sort(Comparator.comparing(Project::getName));
    }

    // Sort based on user settings
    /**
     * Sorts a list of projects based on a user's settings.
     *
     * @param projects The list of projects to be sorted.
     * @param user     The user whose settings will be used to sort the projects.
     * @param <T>      The type of user.
     */
    public static <T extends User> void sortProjects(List<Project> projects, T user) {
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

        if (user.getReverseSort()) {
            Collections.reverse(projects);
        }
    }

    /**
     * Checks if the current project is within its application date range.
     *
     * @return True if the current date is within the project's date range, false
     *         otherwise.
     */
    public boolean inDateRange() {
        LocalDate today = LocalDate.now();
        return !(today.isAfter(applicationClosingDate) || today.isBefore(this.applicationOpenDate));
    }

    /**
     * Checks if a given project is within the current project's date range.
     *
     * @param project The project to be checked.
     * @return True if the project is within the date range, false otherwise.
     */
    public boolean inDateRange(Project project) {
        return !(project.getApplicationOpenDate().isAfter(this.applicationClosingDate)
                || project.getApplicationClosingDate().isBefore(this.applicationOpenDate));
    }

    /**
     * Checks if a given date range is within the project's application date range.
     *
     * @param startDate The start date of the range.
     * @param endDate   The end date of the range.
     * @return True if the date range is within the project's range, false
     *         otherwise.
     */
    public boolean inDateRange(LocalDate startDate, LocalDate endDate) {
        return !(startDate.isAfter(this.applicationClosingDate) || endDate.isBefore(this.applicationOpenDate));
    }

    /**
     * Toggles the visibility of the project.
     */
    public void toggleVisibility() {
        this.visibility = !visibility;
    }

    /**
     * Removes a specific enquiry from the project.
     *
     * @param enquiry The enquiry to be removed.
     */
    public void removeEnquiry(Enquiry enquiry) {
        this.enquiries.remove(enquiry);
    }

    /**
     * Deletes the project, removing it from all associated data and references.
     *
     * @param data The data object used for removing the project.
     */
    public void deleteProject(Data data) {
        for (Registration registration : this.registrations) {
            registration.deleteRegistration(data);
        }
        for (Officer officer : this.assignedOfficers) {
            officer.getAssignedProjects().remove(this);
        }
        for (Application application : this.applications) {
            application.deleteApplication(data);
        }
        for (FlatBooking booking : this.bookings) {
            booking.deleteBooking(data);
        }
        for (Enquiry enquiry : this.enquiries) {
            enquiry.deleteEnquiry(data);
        }
        this.manager.getProjects().remove(this);
        data.getProjectList().remove(this);
    }

    public String toString() {
        return toString(false);
    }

    /**
     * Returns a string representation of the project, optionally including
     * additional information.
     *
     * @param showAdditionalInfo Flag indicating whether to show additional
     *                           information.
     * @return A string representation of the project.
     */
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

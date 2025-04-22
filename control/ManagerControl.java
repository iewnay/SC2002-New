package control;

import shared.Data;
import entity.Manager;
import entity.Project;
import entity.Unit;
import entity.Application;
import enums.UnitType;
import enums.MaritalStatus;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The ManagerControl class manages functionalities for a Manager, such as
 * creating, editing, deleting projects,
 * and generating reports for projects. It provides methods to interact with
 * projects, including setting project
 * parameters, filtering applications, and generating reports based on specific
 * criteria.
 */
public class ManagerControl {

    /**
     * The file path where the report files are saved.
     */
    public static final String REPORT_FILEPATH = "./data/reports/";

    /**
     * Edits an existing project by updating its details such as project name,
     * neighbourhood, available units,
     * opening and closing dates, and officer slots. This method ensures that the
     * opening date is not after the closing date,
     * and officer slots do not exceed the maximum allowed.
     *
     * @param manager the manager editing the project
     * @param project the project to be edited
     * @param sc      the scanner object used to capture user input
     */
    public static void editProject(Manager manager, Project project, Scanner sc) {
        System.out.println("Enter project information:");
        System.out.print("Project name\t\t\t: ");
        String name = sc.nextLine();
        System.out.print("Neighbourhood\t\t\t: ");
        String neighbourhood = sc.nextLine();
        List<Unit> units = new ArrayList<>();
        for (UnitType unitType : UnitType.values()) {
            System.out.print(unitType.toString() + " Flats Available\t\t: ");
            int flats = sc.nextInt();
            sc.nextLine();
            if (flats > 0) {
                System.out.print("Price of " + unitType.toString() + " Flats ($)\t: ");
                double price = sc.nextDouble();
                sc.nextLine();
                units.add(new Unit(unitType, flats, price));
            }
        }
        if (units.size() > 0) {
            LocalDate openingDate, closingDate;
            while (true) {
                try {
                    System.out.print("Opening date (yyyy-MM-dd)\t: ");
                    String openingDateString = sc.nextLine();
                    openingDate = LocalDate.parse(openingDateString);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Incorrect date format. Please try again.");
                }
            }
            while (true) {
                try {
                    System.out.print("Closing date (yyyy-MM-dd)\t: ");
                    String closingDateString = sc.nextLine();
                    closingDate = LocalDate.parse(closingDateString);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Incorrect date format. Please try again.");
                }
            }
            if (!openingDate.isAfter(closingDate)) {
                System.out.print("Officer Slots (Max 10)\t\t: ");
                int officerSlots = Math.abs(sc.nextInt());
                if (officerSlots <= 10) {
                    if (manager.editProject(name, neighbourhood,
                            units, openingDate, closingDate, true, officerSlots, project)) {
                        System.out.println("Project edited successfully!");
                    } else {
                        System.out.println(
                                "There is a clash in dates! Please double check your existing projects before creating a new one.");
                    }
                } else {
                    System.out.println("Officer slots exceeded 10. Please try again.");
                }
            } else {
                System.out.println("Error! Opening date is after closing date. Please try again.");
            }
        } else {
            System.out.println("No units input, cancelling creation of project...");
        }
    }

    /**
     * Creates a new project by taking input for project details such as project
     * name, neighbourhood,
     * available units, opening and closing dates, and officer slots. It ensures
     * that the opening date is not after the closing date,
     * and officer slots do not exceed the maximum allowed.
     *
     * @param manager the manager creating the project
     * @param sc      the scanner object used to capture user input
     * @param data    the data object containing the list of projects
     */
    public static void createProject(Manager manager, Scanner sc, Data data) {
        System.out.println("Enter project information:");
        System.out.print("Project name\t\t\t: ");
        String name = sc.nextLine();
        System.out.print("Neighbourhood\t\t\t: ");
        String neighbourhood = sc.nextLine();
        List<Unit> units = new ArrayList<>();
        for (UnitType unitType : UnitType.values()) {
            System.out.print(unitType.toString() + " Flats Available\t\t: ");
            int flats = sc.nextInt();
            sc.nextLine();
            if (flats > 0) {
                System.out.print("Price of " + unitType.toString() + " Flats ($)\t: ");
                double price = sc.nextDouble();
                sc.nextLine();
                units.add(new Unit(unitType, flats, price));
            }
        }
        if (units.size() > 0) {
            LocalDate openingDate, closingDate;
            while (true) {
                try {
                    System.out.print("Opening date (yyyy-MM-dd)\t: ");
                    String openingDateString = sc.nextLine();
                    openingDate = LocalDate.parse(openingDateString);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Incorrect date format. Please try again.");
                }
            }
            while (true) {
                try {
                    System.out.print("Closing date (yyyy-MM-dd)\t: ");
                    String closingDateString = sc.nextLine();
                    closingDate = LocalDate.parse(closingDateString);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Incorrect date format. Please try again.");
                }
            }
            if (!openingDate.isAfter(closingDate)) {
                System.out.print("Officer Slots (Max 10)\t\t: ");
                try {
                    int officerSlots = Integer.parseInt(sc.nextLine());
                    if (officerSlots <= 10) {
                        if (manager.createProject(name, neighbourhood,
                                units, openingDate, closingDate, true, officerSlots, data.getProjectList())) {
                            System.out.println("Project created successfully!");
                        } else {
                            System.out.println(
                                    "There is a clash in dates! Please double check your existing projects before creating a new one.");
                        }
                    } else {
                        System.out.println("Officer slots exceeded 10. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            } else {
                System.out.println("Error! Opening date is after closing date. Please try again.");
            }
        } else {
            System.out.println("No units input, cancelling creation of project...");
        }
    }

    /**
     * Deletes the specified project and removes it from the data. The project is
     * then set to null.
     *
     * @param project the project to be deleted
     * @param data    the data object containing the project list
     */
    public static void deleteProject(Project project, Data data) {
        project.deleteProject(data);
        project = null;
        System.out.println("Project Deleted");
    }

    /**
     * Generates a report for the specified project, allowing the user to filter the
     * applications based on criteria such as
     * flat type, age range, and marital status. The generated report is saved as a
     * text file with a timestamped filename.
     *
     * @param project the project for which the report is generated
     * @param sc      the scanner object used to capture user input for filtering
     *                and generating the report
     */
    public static void generateReport(Project project, Scanner sc) {
        String report = "";
        report += "| Report for Project |\n";

        // Filter report
        System.out.println();
        System.out.println("Filter report (Y/N)");
        String filter = sc.nextLine();
        List<Application> applications = project.getApplications();

        if (filter.equalsIgnoreCase("y")) {
            report += "\nFilter settings:";
            System.out.println("| Filter Report |");
            System.out.println("Leave blank to continue:");
            // Choose flat type
            System.out.println("Flat Type");
            UnitType selUnitType = ArrayControl.selectUnitType(sc);
            if (selUnitType != null) {
                report += "\nFlat Type: " + selUnitType;
                applications = applications.stream().filter(a -> a.getUnitType() == selUnitType)
                        .collect(Collectors.toList());
            }
            // Choose age range
            try {
                System.out.print("Minimum Age (0 to skip): ");
                int minAge = Integer.parseInt(sc.nextLine());
                if (minAge != 0) {
                    report += "\nMin Age: " + minAge;
                    applications = applications.stream()
                            .filter(a -> a.getApplicant().getAge() >= minAge)
                            .collect(Collectors.toList());
                }
                System.out.print("Maximum Age: (0 to skip)");
                int maxAge = Integer.parseInt(sc.nextLine());
                if (maxAge != 0) {
                    report += "\nMax Age: " + maxAge;
                    applications = applications.stream()
                            .filter(a -> a.getApplicant().getAge() <= maxAge)
                            .collect(Collectors.toList());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
            // Choose marital status
            System.out.println("Marital Status");
            MaritalStatus selMaritalStatus = ArrayControl.selectMaritalStatus(sc);
            if (selMaritalStatus != null) {
                report += "\nMarital Status: " + selMaritalStatus;
                applications = applications.stream()
                        .filter(a -> a.getApplicant().getMaritalStatus() == selMaritalStatus)
                        .collect(Collectors.toList());
            }
            report += "\n";
        } else if (!filter.equalsIgnoreCase("n")) {
            // Cancel report generation
            return;
        }

        // Generate Report String
        report += project.toString(true);
        report += "\n";

        if (applications.size() == 0) {
            report += "No applications.";
        } else {
            for (Application application : applications) {
                report += "\n------------------------------";
                report += "\nApplicant:\n";
                report += application.getApplicant().toString(true);
                report += "\nFlat Booking:\n";
                report += application.getUnitType();
            }
        }

        // Declare filename
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);

        String filename = formattedDateTime + "_" + project.getName() + ".txt";

        // Write to file
        try (FileWriter writer = new FileWriter(REPORT_FILEPATH + filename)) {
            writer.write(report);
            System.out.println(report);
            System.out.println();
            System.out.println("Report generated - " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}

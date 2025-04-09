package control;

import shared.*;
import entity.*;
import enums.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagerControl {
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
                int officerSlots = Math.abs(sc.nextInt());
                if (officerSlots <= 10) {
                    if (manager.createProject(name, neighbourhood,
                            units, openingDate, closingDate, true, officerSlots, data.projectList)) {
                        System.out.println("Project created successfully!");
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

    public static void deleteProject(Project project, Data data) {
        // TO DO
    }
}

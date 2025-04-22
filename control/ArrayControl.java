package control;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import entity.Project;
import entity.Unit;
import enums.UnitType;
import enums.MaritalStatus;

/**
 * The ArrayControl class provides utility methods to handle lists of objects.
 * It supports printing lists, selecting items from lists, and allowing users
 * to choose specific options, including projects, units, and marital status.
 */
public class ArrayControl {

    /**
     * Prints a list of projects, with an option to display additional information
     * for each project.
     * 
     * @param list               the list of projects to be printed
     * @param showAdditionalInfo whether to show additional information for each
     *                           project
     */
    public static void printFromList(List<Project> list, boolean showAdditionalInfo) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("-------------------------");
            System.out.println("(" + (i + 1) + ")");
            System.out.println(list.get(i).toString(showAdditionalInfo));
        }
    }

    /**
     * Prints a list of generic items with indexing for easier reading.
     *
     * @param list the list of items to be printed
     * @param <T>  the type of the items in the list
     */
    public static <T> void printFromList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("-------------------------");
            System.out.println("(" + (i + 1) + ")");
            System.out.println(list.get(i));
        }
    }

    /**
     * Allows the user to select a project from a list of projects. The user can
     * view
     * additional project information if specified. If the user selects an invalid
     * input,
     * a message will be shown and no project will be selected.
     *
     * @param list               the list of projects to choose from
     * @param sc                 the scanner object to read user input
     * @param showAdditionalInfo whether to show additional project information
     * @return the selected project, or null if the selection was cancelled or
     *         invalid
     */
    public static Project selectFromList(List<Project> list, Scanner sc, boolean showAdditionalInfo) {
        if (list.isEmpty()) {
            System.out.println("Nothing to select.");
            return null;
        }

        printFromList(list, showAdditionalInfo);
        System.out.print("(0): Cancel | (1 - " + list.size() + "): Select from list: ");
        try {
            int selectedIndex = Integer.parseInt(sc.nextLine());
            if (selectedIndex == 0)
                return null;
            if (selectedIndex > 0 && selectedIndex <= list.size())
                return list.get(selectedIndex - 1);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid input.");
        }
        System.out.println("Nothing selected.");
        return null;
    }

    /**
     * Allows the user to select an item from a list of generic items.
     * The user can cancel the selection or choose an item based on the displayed
     * list.
     *
     * @param list the list of items to choose from
     * @param sc   the scanner object to read user input
     * @param <T>  the type of the items in the list
     * @return the selected item, or null if the selection was cancelled or invalid
     */
    public static <T> T selectFromList(List<T> list, Scanner sc) {
        if (list.isEmpty()) {
            System.out.println("Nothing to select.");
            return null;
        }

        printFromList(list);
        System.out.print("(0): Cancel | (1 - " + list.size() + "): Select from list: ");
        try {
            int selectedIndex = Integer.parseInt(sc.nextLine());
            if (selectedIndex == 0)
                return null;
            if (selectedIndex > 0 && selectedIndex <= list.size())
                return list.get(selectedIndex - 1);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid input.");
        }
        System.out.println("Nothing selected.");
        return null;
    }

    /**
     * Allows the user to select a unit type from a project's available units.
     * The user will be presented with a list of available unit types based on the
     * project.
     *
     * @param project the project from which the unit types will be selected
     * @param sc      the scanner object to read user input
     * @return the selected unit type, or null if the selection is cancelled
     */
    public static UnitType selectUnitType(Project project, Scanner sc) {
        List<UnitType> unitTypes = new ArrayList<>();
        for (Unit unit : project.getUnits()) {
            if (unit.getUnitsAvailable() > 0) {
                unitTypes.add(unit.getUnitType());
            }
        }
        return selectFromList(unitTypes, sc);
    }

    /**
     * Allows the user to select a unit type from all available unit types.
     * This is used when no specific project is provided, and a general selection is
     * needed.
     *
     * @param sc the scanner object to read user input
     * @return the selected unit type
     */
    public static UnitType selectUnitType(Scanner sc) {
        List<UnitType> unitTypes = new ArrayList<>();
        for (UnitType unitType : UnitType.values()) {
            unitTypes.add(unitType);
        }
        return selectFromList(unitTypes, sc);
    }

    /**
     * Allows the user to select a marital status from the available types.
     *
     * @param sc the scanner object to read user input
     * @return the selected marital status
     */
    public static MaritalStatus selectMaritalStatus(Scanner sc) {
        List<MaritalStatus> MaritalStatusTypes = new ArrayList<>();
        for (MaritalStatus maritalStatus : MaritalStatus.values()) {
            MaritalStatusTypes.add(maritalStatus);
        }
        return selectFromList(MaritalStatusTypes, sc);
    }
}

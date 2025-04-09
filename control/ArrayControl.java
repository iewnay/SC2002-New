package control;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import entity.*;
import enums.*;

public class ArrayControl {
    // Print project specific with / without additional info
    public static void printFromList(List<Project> list, boolean showAdditionalInfo) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("-------------------------");
            System.out.println("(" + (i + 1) + ")");
            System.err.println(list.get(i).toString(showAdditionalInfo));
        }
    }

    // Print items in list with indexing for easy reading
    public static <T> void printFromList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("-------------------------");
            System.out.println("(" + (i + 1) + ")");
            System.err.println(list.get(i));
        }
    }

    // Select item, project specific with / without printing additional info
    public static Project selectFromList(List<Project> list, Scanner sc, boolean showAdditionalInfo) {
        if (list.isEmpty()) {
            System.out.println("Nothing to select.");
            return null;
        }

        printFromList(list, showAdditionalInfo);
        System.out.print("(0): Cancel | (1 - " + list.size() + "): Select from list: ");
        int selectedIndex = Math.abs(sc.nextInt());
        sc.nextLine();
        if (selectedIndex == 0)
            return null;
        if (selectedIndex > 0 && selectedIndex <= list.size())
            return list.get(selectedIndex - 1);

        System.out.println("Invalid input, please try again.");
        return null;
    }

    // Select an item from list using index
    public static <T> T selectFromList(List<T> list, Scanner sc) {
        if (list.isEmpty()) {
            System.out.println("Nothing to select.");
            return null;
        }

        printFromList(list);
        System.out.print("(0): Cancel | (1 - " + list.size() + "): Select from list: ");
        int selectedIndex = Math.abs(sc.nextInt());
        sc.nextLine();
        if (selectedIndex == 0)
            return null;
        if (selectedIndex > 0 && selectedIndex <= list.size())
            return list.get(selectedIndex - 1);

        System.out.println("Invalid input, please try again.");
        return null;
    }

    public static UnitType selectUnitType(Project project, Scanner sc) {
        List<UnitType> unitTypes = new ArrayList<>();
        for (Unit unit : project.getUnits()) {
            if (unit.getUnitsAvailable() > 0) {
                unitTypes.add(unit.getUnitType());
            }
        }
        return selectFromList(unitTypes, sc);
    }
}

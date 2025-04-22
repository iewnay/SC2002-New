import java.util.Scanner;

import boundary.AuthInterface;
import boundary.ManagerInterface;
import boundary.OfficerInterface;
import boundary.ApplicantInterface;
import control.Initialize;
import entity.User;
import entity.Manager;
import entity.Officer;
import entity.Applicant;
import shared.Data;

/**
 * {@code BTOApp} class serves as the main entry point for the BTO system
 * application.
 * The application utilizes a Data Transfer Object (DTO) to maintain application
 * data, which is loaded at startup and saved at logout.
 */
// Main class, calls interfaces from boundary classes after login
public class BTOApp {

    /**
     * Main method starts the application.
     * Initialize necessary data.
     * Handle user authentication.
     * Display UI menu based on user role.
     */
    public static void main(String[] args) {
        // Initialize data transfer object - Stores all Objects / Convrete classes used
        Data dataDTO = Initialize.initializeData();
        Scanner sc = new Scanner(System.in);
        User loggedInUser = null;

        do {
            loggedInUser = AuthInterface.authenticate(sc, dataDTO);
            if (loggedInUser == null)
                break;

            if (loggedInUser instanceof Manager)
                ManagerInterface.showMenu((Manager) loggedInUser, sc, dataDTO);

            else if (loggedInUser instanceof Officer)
                OfficerInterface.showMenu((Officer) loggedInUser, sc, dataDTO);

            else if (loggedInUser instanceof Applicant)
                ApplicantInterface.showMenu((Applicant) loggedInUser, sc, dataDTO);

            Initialize.saveData(dataDTO); // Save data every logout

        } while (loggedInUser != null);

        sc.close();

        // Save data
        Initialize.saveData(dataDTO);
    }
}

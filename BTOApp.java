import java.util.*;

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

// Main class, calls interfaces from boundary classes after login
public class BTOApp {
    public static void main(String[] args) {
        // Initialize data - Lists of all Objects used
        Data dataDTO = Initialize.initializeData();
        Scanner sc = new Scanner(System.in);

        User loggedInUser = null;
        do {
            loggedInUser = AuthInterface.authenticate(sc, dataDTO);
            if (loggedInUser == null)
                break;
            else if (loggedInUser instanceof Manager)
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

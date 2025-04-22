package control;

import java.util.List;
import java.util.regex.Pattern;

import entity.User;
import entity.Manager;
import entity.Officer;
import entity.Applicant;
import enums.MaritalStatus;
import shared.Data;

/**
 * The AuthControl class handles user authentication, registration, and
 * validation
 * for different types of users (Manager, Officer, Applicant). It provides
 * methods
 * for logging in, checking the existence of a user, and registering new users.
 */
public class AuthControl {

    /** Pattern for validating NRIC format (e.g., S1234567D). */
    public static final String NRIC_PATTERN = "^[A-Za-z][0-9]{7}[A-Za-z]$";

    /**
     * Authenticates a user based on their NRIC and password.
     * The method checks if the user exists in the manager, officer, or applicant
     * lists.
     * If found, returns the authenticated user; otherwise, returns null.
     *
     * @param NRIC     the NRIC of the user
     * @param password the password of the user
     * @param data     the Data object containing lists of users (managers,
     *                 officers, applicants)
     * @return the authenticated User, or null if credentials are invalid
     */
    public static User login(String NRIC, String password, Data data) {
        User user;
        NRIC = NRIC.toLowerCase();

        // Validate NRIC format
        if (!Pattern.matches(NRIC_PATTERN, NRIC)) {
            System.out.println("Invalid NRIC.");
            return null;
        }

        // Authenticate user by checking each user list (manager, officer, applicant)
        if ((user = authenticate(data.getManagerList(), NRIC, password)) != null)
            return user;
        if ((user = authenticate(data.getOfficerList(), NRIC, password)) != null)
            return user;
        if ((user = authenticate(data.getApplicantList(), NRIC, password)) != null)
            return user;

        // If no user is found
        System.out.println("Invalid credentials. Please try again.");
        return null;
    }

    /**
     * Helper method to authenticate a user from a list of users.
     * This method checks if the provided NRIC and password match any user in the
     * list.
     *
     * @param <T>      the type of user (Manager, Officer, or Applicant)
     * @param userList the list of users to authenticate from
     * @param NRIC     the NRIC of the user
     * @param password the password of the user
     * @return the authenticated user, or null if no match is found
     */
    private static <T extends User> T authenticate(List<T> userList, String NRIC, String password) {
        return userList.stream()
                .filter(user -> user.getNRIC().equalsIgnoreCase(NRIC) && user.login(password))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if a user with the given NRIC exists in the system.
     * The method checks if the NRIC exists in any of the user lists (managers,
     * officers, applicants).
     *
     * @param NRIC the NRIC of the user
     * @param data the Data object containing the lists of users
     * @return true if the user exists, false otherwise
     */
    public static boolean NRICExists(String NRIC, Data data) {
        return data.getManagerList().stream().anyMatch(m -> m.getNRIC().equals(NRIC)) ||
                data.getOfficerList().stream().anyMatch(o -> o.getNRIC().equals(NRIC)) ||
                data.getApplicantList().stream().anyMatch(a -> a.getNRIC().equals(NRIC));
    }

    /**
     * Registers a new user (Manager, Officer, or Applicant) based on the provided
     * information.
     * The method checks if the NRIC is valid and not already registered in the
     * system,
     * then creates a new user object and adds it to the appropriate list.
     * 
     * @param NRIC              the NRIC of the user
     * @param name              the name of the user
     * @param age               the age of the user
     * @param maritalStatusCode the marital status code (1 for single, 2 for
     *                          married)
     * @param userCode          the code to identify the user type (Manager,
     *                          Officer, or Applicant)
     * @param data              the Data object containing the lists of users
     */
    public static void register(String NRIC, String name, int age, int maritalStatusCode, String userCode, Data data) {
        NRIC = NRIC.toLowerCase();

        // Error checks
        if (NRICExists(NRIC, data)) {
            System.out.println("User already exists!");
            return;
        }
        if (!Pattern.matches(NRIC_PATTERN, NRIC)) {
            System.out.println("Invalid NRIC.");
            return;
        }

        // Determine marital status
        MaritalStatus maritalStatus;
        if (maritalStatusCode == 1)
            maritalStatus = MaritalStatus.Single;
        else if (maritalStatusCode == 2)
            maritalStatus = MaritalStatus.Married;
        else {
            System.out.println("Invalid marital status code.");
            return;
        }

        // Create and add user based on the user code
        String userType = data.checkCode(userCode);
        switch (userType) {
            case ("Manager"):
                Manager tempManager = new Manager(NRIC, name, age, maritalStatus);
                data.addManager(tempManager);
                break;
            case ("Officer"):
                Officer tempOfficer = new Officer(NRIC, name, age, maritalStatus);
                data.addOfficer(tempOfficer);
                break;
            default:
                Applicant tempApplicant = new Applicant(NRIC, name, age, maritalStatus);
                data.addApplicant(tempApplicant);
        }

        System.out.println("Welcome " + userType + " " + name
                + ", please change your default password (password) on login.");
    }
}

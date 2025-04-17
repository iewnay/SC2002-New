package control;

import java.util.List;
import java.util.regex.Pattern;

import entity.User;
import entity.Manager;
import entity.Officer;
import entity.Applicant;
import enums.MaritalStatus;
import shared.Data;

public class AuthControl {
    public static final String NRIC_PATTERN = "^[A-Za-z][0-9]{7}[A-Za-z]$";

    public static User login(String NRIC, String password, Data data) {
        User user;
        NRIC = NRIC.toLowerCase();
        if (!Pattern.matches(NRIC_PATTERN, NRIC)) {
            System.out.println("Invalid NRIC.");
            return null;
        }
        // Search manager, then officer, then applicant for correct user
        if ((user = authenticate(data.getManagerList(), NRIC, password)) != null)
            return user;
        if ((user = authenticate(data.getOfficerList(), NRIC, password)) != null)
            return user;
        if ((user = authenticate(data.getApplicantList(), NRIC, password)) != null)
            return user;
        // If user not found, return null
        System.out.println("Invalid credentials. Please try again.");
        return null;
    }

    private static <T extends User> T authenticate(List<T> userList, String NRIC, String password) {
        return userList.stream()
                .filter(user -> user.getNRIC().equalsIgnoreCase(NRIC) && user.login(password))
                .findFirst()
                .orElse(null);
    }

    public static boolean NRICExists(String NRIC, Data data) {
        return data.getManagerList().stream().anyMatch(m -> m.getNRIC().equals(NRIC)) ||
                data.getOfficerList().stream().anyMatch(o -> o.getNRIC().equals(NRIC)) ||
                data.getApplicantList().stream().anyMatch(a -> a.getNRIC().equals(NRIC));
    }

    public static void register(String NRIC, String name, int age, int maritalStatusCode, String userCode,
            Data data) {

        NRIC = NRIC.toLowerCase();

        // Possible errors
        if (NRICExists(NRIC, data)) {
            System.out.println("User already exists!");
            return;
        }
        if (!Pattern.matches(NRIC_PATTERN, NRIC)) {
            System.out.println("Invalid NRIC.");
            return;
        }
        MaritalStatus maritalStatus;
        if (maritalStatusCode == 1)
            maritalStatus = MaritalStatus.Single;
        else if (maritalStatusCode == 2)
            maritalStatus = MaritalStatus.Married;
        else {
            System.out.println("Invalid marital status code.");
            return;
        }

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

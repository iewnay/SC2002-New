package control;

import java.util.List;
import java.util.ArrayList;

import entity.User;
import entity.Manager;
import entity.Officer;
import entity.Applicant;
import enums.MaritalStatus;
import shared.Data;

public class AuthControl {
    public static List<String> managerCodes = new ArrayList<>();
    public static List<String> officerCodes = new ArrayList<>();

    private static <T extends User> T authenticate(List<T> userList, String NRIC, String password) {
        return userList.stream()
                .filter(user -> user.getNRIC().equalsIgnoreCase(NRIC) && user.login(password))
                .findFirst()
                .orElse(null);
    }

    private static String checkCode(String userCode) {
        if (managerCodes.contains(userCode))
            return "Manager";
        if (officerCodes.contains(userCode))
            return "Officer";
        return "Applicant";
    }

    public static void registerUser(String NRIC, String name, int age, MaritalStatus maritalStatus, String userCode,
            Data data) {
        String userType = checkCode(userCode);
        NRIC.toLowerCase();
        switch (userType) {
            case ("Manager"):
                Manager tempManager = new Manager(NRIC, name, age, maritalStatus);
                data.managerList.add(tempManager);
                break;
            case ("Officer"):
                Officer tempOfficer = new Officer(NRIC, name, age, maritalStatus);
                data.officerList.add(tempOfficer);
                break;
            default:
                Applicant tempApplicant = new Applicant(NRIC, name, age, maritalStatus);
                data.applicantList.add(tempApplicant);
        }
        System.out.println("Welcome " + userType + " " + name
                + ", please change your default password (password) on login.\n");
    }

    public static boolean NRICExists(String NRIC, Data data) {
        return data.managerList.stream().anyMatch(m -> m.getNRIC().equals(NRIC)) ||
                data.officerList.stream().anyMatch(o -> o.getNRIC().equals(NRIC)) ||
                data.applicantList.stream().anyMatch(a -> a.getNRIC().equals(NRIC));
    }

    public static User login(String NRIC, String password, Data data) {
        User user;
        NRIC.toLowerCase();
        // Search manager, then officer, then applicant for correct user
        if ((user = authenticate(data.managerList, NRIC, password)) != null)
            return user;
        if ((user = authenticate(data.officerList, NRIC, password)) != null)
            return user;
        if ((user = authenticate(data.applicantList, NRIC, password)) != null)
            return user;
        // If user not found, return null
        return null;
    }
}

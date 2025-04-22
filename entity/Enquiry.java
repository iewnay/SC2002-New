package entity;

import shared.Data;

import java.io.Serializable;
import java.util.List;

/**
 * An enquiry made by an applicant regarding a project.
 * The enquiry includes a header, text, and an optional reply.
 */
public class Enquiry implements Serializable {

    // Attributes
    private Applicant applicant;
    private Project project;
    private String enquiryHeader;
    private String enquiryText;
    private String reply;

    // Constructor

    /**
     * Constructs an enquiry for a given applicant and project with a specified
     * header and text.
     * The enquiry is added to both the applicant and project.
     *
     * @param applicant     The applicant who made the enquiry.
     * @param project       The project the enquiry is about.
     * @param enquiryHeader The header/title of the enquiry.
     * @param enquiryText   The body of the enquiry.
     */
    public Enquiry(Applicant applicant, Project project, String enquiryHeader, String enquiryText) {
        this.applicant = applicant;
        this.project = project;
        this.enquiryHeader = enquiryHeader;
        this.enquiryText = enquiryText;
        this.reply = null;
        this.applicant.addEnquiry(this);
        this.project.addEnquiry(this);
    }

    // Getters and Setters

    /**
     * Checks if the enquiry has been answered by checking if a reply exists.
     *
     * @return true if the enquiry has a reply, false otherwise.
     */
    public boolean isAnswered() {
        return !(this.reply == null);
    }

    /**
     * Sets the reply for the enquiry.
     *
     * @param reply The reply text.
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    // Methods

    /**
     * Displays a list of all enquiries.
     *
     * @param enquiries The list of enquiries to be displayed.
     * @return true if there are enquiries to display, false if no enquiries exist.
     */
    public static boolean viewEnquiries(List<Enquiry> enquiries) {
        int count = 0;
        if (enquiries.size() > 0) {
            for (Enquiry enquiry : enquiries) {
                count++;
                System.out.println("------------------");
                System.out.println("(" + count + ")");
                System.out.println(enquiry);
            }
            return true;
        }
        System.out.println("No enquiries.");
        return false;
    }

    /**
     * Edits the enquiry by updating its text and appending "(Edited)" to the header
     * if not already appended.
     *
     * @param text The new text for the enquiry.
     */
    public void editEnquiry(String text) {
        if (!this.enquiryHeader.contains("Edited"))
            this.enquiryHeader += " (Edited)";
        this.enquiryText = text;
    }

    /**
     * Deletes the enquiry by removing it from both the applicant's and the
     * project's enquiry list,
     * and from the global data list.
     *
     * @param data The data object containing the list of all enquiries.
     */
    public void deleteEnquiry(Data data) {
        this.applicant.removeEnquiry(this);
        this.project.removeEnquiry(this);
        data.getEnquiryList().remove(this);
    }

    /**
     * Returns a string representation of the enquiry, including the header, text,
     * and reply (if any).
     *
     * @return A formatted string showing the enquiry header, text, and reply.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nEnquiry: ").append(this.enquiryHeader).append("\n");
        sb.append("Body:\n").append(this.enquiryText).append("\n");
        if (this.reply != null) {
            sb.append("Reply:\n").append(this.reply).append("\n");
        }
        return sb.toString();
    }
}

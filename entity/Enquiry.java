package entity;

import shared.Data;

import java.io.Serializable;
import java.util.List;

public class Enquiry implements Serializable {
    // Attributes
    private Applicant applicant;
    private Project project;
    private String enquiryHeader;
    private String enquiryText;
    private String reply;

    // Constructor
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
    public boolean isAnswered() {
        return !(this.reply == null);
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    // Methods

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

    public void editEnquiry(String text) {
        if (!this.enquiryHeader.contains("Edited"))
            this.enquiryHeader += " (Edited)";
        this.enquiryText = text;
    }

    public void deleteEnquiry(Data data) {
        this.applicant.removeEnquiry(this);
        this.project.removeEnquiry(this);
        data.getEnquiryList().remove(this);
    }

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

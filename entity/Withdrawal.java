package entity;

import java.io.Serializable;
import enums.Status;

public class Withdrawal implements Serializable{
    private Application application;
    private Applicant applicant;
    private Status status;

    public Withdrawal(Application application, Applicant applicant) {
        this.application = application;
        this.applicant = applicant;
        this.applicant.setWithdrawal(this);
        this.status = Status.Pending;
    }

    public Status getStatus() {
        return this.status;
    }

    public void approve() {
        this.status = Status.Successful;
        this.application.getApplicant().clearApplication();
    }

    public void reject() {
        this.status = Status.Unsuccessful;
    }
}

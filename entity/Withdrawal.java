package entity;

import java.io.Serializable;
import enums.Status;
import shared.Data;

public class Withdrawal implements Serializable {
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

    public void approve(Data data) {
        this.status = Status.Successful;
        this.application.getApplicant().clearApplication();
        data.getWithdrawals().remove(this);
    }

    public void reject(Data data) {
        this.status = Status.Unsuccessful;
        data.getWithdrawals().remove(this);
    }

    @Override
    public String toString() {
        return this.application.toString();
    }
}

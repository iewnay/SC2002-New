package entity;

import java.io.Serializable;
import enums.*;

public class Withdrawal implements Serializable{
    private Application application;
    private Status status;

    public Withdrawal(Application application) {
        this.application = application;
        application.getApplicant().setWithdrawal(this);
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

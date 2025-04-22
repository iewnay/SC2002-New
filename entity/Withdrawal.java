package entity;

import java.io.Serializable;
import enums.Status;
import shared.Data;

/**
 * A Withdrawal request from an applicant, with the associated application,
 * applicant, and status.
 * Implements Serializable for object serialization.
 */
public class Withdrawal implements Serializable {
    // Attributes
    private Application application;
    private Applicant applicant;
    private Status status;

    /**
     * Constructor to initialize a Withdrawal object with an application and
     * applicant.
     * The withdrawal status is set to Pending by default, and the applicant is
     * associated with the withdrawal.
     *
     * @param application the application associated with the withdrawal
     * @param applicant   the applicant requesting the withdrawal
     */
    public Withdrawal(Application application, Applicant applicant) {
        this.application = application;
        this.applicant = applicant;
        this.applicant.setWithdrawal(this);
        this.status = Status.Pending;
    }

    // Getters and Setters

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Methods

    /**
     * Approves the withdrawal and updates its status to Successful.
     * Removes the application from the applicant and removes the withdrawal from
     * the data.
     *
     * @param data the data object containing the list of withdrawals
     */
    public void approve(Data data) {
        this.status = Status.Successful;
        this.application.getApplicant().clearApplication();
        data.getWithdrawals().remove(this);
    }

    /**
     * Rejects the withdrawal and updates its status to Unsuccessful.
     * Removes the withdrawal from the data.
     *
     * @param data the data object containing the list of withdrawals
     */
    public void reject(Data data) {
        this.status = Status.Unsuccessful;
        data.getWithdrawals().remove(this);
    }

    /**
     * Returns a string representation of the withdrawal, specifically the
     * application associated with it.
     *
     * @return a string representation of the withdrawal
     */
    @Override
    public String toString() {
        return this.application.toString();
    }
}

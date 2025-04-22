package enums;

/**
 * Enum representing the status of an application or registration.
 * This enum defines the possible statuses:
 * {@link #Pending} - Indicates that application or registration is waiting to
 * be approved or rejected.
 * {@link #Successful} - Indicates that application or registration has been
 * approved successfully.
 * {@link #Unsuccessful} - Indicates that the application or registration has
 * been rejected.
 * {@link #Booked} - Indicates that application has been booked.
 */
public enum Status {
    Pending,
    Successful,
    Unsuccessful,
    Booked
}

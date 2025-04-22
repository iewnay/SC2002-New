package entity;

import java.io.Serializable;
import enums.MaritalStatus;

/**
 * Abstract class representing a user in the system.
 * Includes personal information and sorting preferences.
 * Acts as template for all user classes: Applicant, Officer, Manager.
 */
public abstract class User implements Serializable {

    public static final String fileName = null;

    // Attributes
    private String NRIC;
    private String password;
    private String name;
    private int age;
    private MaritalStatus maritalStatus;
    private int sortSetting; // 1: Name Alphabetical (Default), 2: Location, 3: Units Available, 4: Price
    private boolean reverseSort;

    // Constructor

    /**
     * Constructs a User with basic personal information and default settings.
     *
     * @param NRIC          The NRIC of the user.
     * @param name          The name of the user.
     * @param age           The age of the user.
     * @param maritalStatus The marital status of the user.
     */
    public User(String NRIC, String name, int age, MaritalStatus maritalStatus) {
        this.NRIC = NRIC;
        this.password = "password";
        this.name = name;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.sortSetting = 1;
        this.reverseSort = false;
    }

    // Getters and Setters
    public String getNRIC() {
        return this.NRIC;
    }

    public int getAge() {
        return this.age;
    }

    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public int getSortSetting() {
        return this.sortSetting;
    }

    public void setSortSetting(int setting) {
        this.sortSetting = setting;
    }

    public boolean getReverseSort() {
        return this.reverseSort;
    }

    // Methods

    /**
     * Toggles the reverse sort setting.
     */
    public void reverseSort() {
        this.reverseSort = !this.reverseSort;
    }

    /**
     * Resets sorting preferences to default.
     */
    public void clearSort() {
        this.sortSetting = 1;
        this.reverseSort = false;
    }

    /**
     * Attempts to log in the user with the given password.
     *
     * @param password The password to verify.
     * @return true if the password is correct, false otherwise.
     */
    public boolean login(String password) {
        if (password.equals(this.password)) {
            System.out.println("Logged in successfully. Welcome " + this.name + "!");
            return true;
        }
        return false;
    }

    /**
     * Changes the user's password if the old password matches.
     *
     * @param oldPassword The current password.
     * @param newPassword The new password to set.
     * @return true if the password was changed, false otherwise.
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the user is married.
     *
     * @return true if the marital status is Married, false otherwise.
     */
    public boolean isMarried() {
        return this.maritalStatus == MaritalStatus.Married;
    }

    /**
     * Returns the user's name as the default string representation.
     *
     * @return The name of the user.
     */
    @Override
    public String toString() {
        return toString(false);
    }

    /**
     * Returns a detailed or simple string representation of the user.
     *
     * @param showDetails If true, includes full user information; otherwise, just
     *                    the name.
     * @return The formatted string representation.
     */
    public String toString(boolean showDetails) {
        if (showDetails) {
            String s = "";
            s += "NRIC\t\t: " + this.NRIC.toUpperCase();
            s += "\nName\t\t: " + this.name;
            s += "\nAge\t\t: " + this.age;
            s += "\nMarital Status\t: " + this.maritalStatus;
            return s;
        }
        return this.name;
    }
}

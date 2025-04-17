package entity;

import java.io.Serializable;

import enums.MaritalStatus;

public abstract class User implements Serializable {
    public static final String fileName = null;
    // Parameters
    private String NRIC;
    private String password;
    private String name;
    private int age;
    private MaritalStatus maritalStatus;
    private int sortSetting; // 1: Name Alphabetical (Default), 2: Location, 3: Units Available, 4: Price
    private boolean reverseSort;

    // Constructor
    public User(String NRIC, String name, int age, MaritalStatus maritalStatus) {
        this.NRIC = NRIC;
        this.password = "password";
        this.name = name;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.sortSetting = 1;
        this.reverseSort = false;
    }

    // Getters Setters
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

    public void reverseSort() {
        this.reverseSort = !this.reverseSort;
    }

    public void clearSort() {
        this.sortSetting = 1;
        this.reverseSort = false;
    }

    // Methods
    public boolean login(String password) {
        if (password.equals(this.password)) {
            System.out.println("Logged in successfuly. Welcome " + this.name + "!");
            return true;
        }
        return false;
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            return true;
        } else {
            return false;
        }
    }

    public boolean isMarried() {
        return this.maritalStatus == MaritalStatus.Married;
    }

    public void deleteAccount() {
    }

    @Override
    public String toString() {
        return toString(false);
    }

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

package entity;

import java.io.Serializable;

import enums.*;

public class Unit implements Serializable{
    // Attributes
    private UnitType unitType;
    private int unitsAvailable;
    private double sellingPrice;

    // Constructor
    public Unit(UnitType unitType, int unitsAvailable, double sellingPrice) {
        this.unitType = unitType;
        this.unitsAvailable = unitsAvailable;
        this.sellingPrice = sellingPrice;
    }

    // Getters and Setters
    public UnitType getUnitType() {
        return this.unitType;
    }
    
    public int getUnitsAvailable() {
        return this.unitsAvailable;
    }
    
    public double getSellingPrice() {
        return this.sellingPrice;
    }

    public boolean decrementUnits() {
        if (this.unitsAvailable > 0) {
            this.unitsAvailable = this.unitsAvailable - 1;
            return true;
        }
        return false;
    }
}
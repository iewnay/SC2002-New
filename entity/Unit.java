package entity;

import java.io.Serializable;
import enums.UnitType;

/**
 * A Unit that is available for sale, with attributes such as unit type,
 * available units, and selling price.
 * Implements Serializable for object serialization.
 */
public class Unit implements Serializable {
    // Attributes
    private UnitType unitType;
    private int unitsAvailable;
    private double sellingPrice;

    /**
     * Constructor to initialize a Unit object with unit type, available units, and
     * selling price.
     *
     * @param unitType       the type of the unit
     * @param unitsAvailable the number of units available for sale
     * @param sellingPrice   the price of each unit
     */
    public Unit(UnitType unitType, int unitsAvailable, double sellingPrice) {
        this.unitType = unitType;
        this.unitsAvailable = unitsAvailable;
        this.sellingPrice = sellingPrice;
    }

    // Getters and Setters

    public UnitType getUnitType() {
        return this.unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public int getUnitsAvailable() {
        return this.unitsAvailable;
    }

    public void setUnitsAvailable(int unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }

    public double getSellingPrice() {
        return this.sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    // Methods

    /**
     * Decrements the number of available units by 1.
     * If there are no units available, the decrement operation is not performed.
     *
     * @return true if the unit was successfully decremented, false otherwise
     */
    public boolean decrementUnits() {
        if (this.unitsAvailable > 0) {
            this.unitsAvailable = this.unitsAvailable - 1;
            return true;
        }
        return false;
    }
}

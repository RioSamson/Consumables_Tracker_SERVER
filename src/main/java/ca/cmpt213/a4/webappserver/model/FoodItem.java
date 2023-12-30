package ca.cmpt213.a4.webappserver.model;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Subclass of Consumable object. Represents a food item (excludes drinks).
 * Stores unique food item attributes like weight. Customized toString that shows
 * it is a food item and displays the weight of the food item.
 * @author Rio Samson
 */
public class FoodItem extends Consumable {
    private double weight;

    /**
     * Constructor for the food item
     * @param name (String) name of the food item
     * @param notes (double) notes of the food item
     * @param price (double) price of the food item
     * @param expirationDate (LocalDateTime) expiration date of the food item
     * @param weight (double) weight of the food item
     */
    public FoodItem(String name, String notes, double price, LocalDateTime expirationDate, double weight) {
        super(name, notes, price, expirationDate);
        this.weight = weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    /**
     * toString: contains a long string the has all the basic information about the food including
     * expiration date, notes, price, name
     * @return String the has all the information about the food
     */
    @Override
    public String toString() {
        DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        long daysToExpire = this.daysTillExpire();
        StringBuilder result = new StringBuilder("");

        //append all the necessary information to the string builder
        result.append("Name: ").append(super.getName()).append("\nNotes: ").append(super.getNotes()).append("\n");
        result.append("Price: ").append(DECIMAL_FORMAT.format(super.getPrice())).append("\n");
        result.append("Weight: ").append(DECIMAL_FORMAT.format(weight)).append("\n");
        result.append("Expiry date: ").append(DATE_FORMATTER.format(super.getExpirationDate())).append('\n');

        //display different strings for different foods
        if (daysToExpire == 0) {
            result.append("This food item expires today.");
        } else if (daysToExpire > 0) {
            result.append("This food item will expire in ").append(daysToExpire).append(" day(s).");
        } else {
            result.append("This food item is expired for ").append(daysToExpire * (-1)).append(" day(s).");
        }
        return result.toString();
    }
}
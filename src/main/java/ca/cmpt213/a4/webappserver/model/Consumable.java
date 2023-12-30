package ca.cmpt213.a4.webappserver.model;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * This is the superclass Consumables, which has subclasses FoodItem and DrinkItem.
 * This class stores the common attributes of the food types like name, notes, price, expiration date.
 * @author Rio Samson
 */
public class Consumable implements Comparable<Consumable> {
    private String name;
    private String notes;
    private double price;
    private LocalDateTime expirationDate;

    /**
     * Constructor for the Consumables class.
     * @param name String name of the consumable item
     * @param notes String notes of the consumable item
     * @param price double price of the consumable item
     * @param expirationDate LocalDateTime expiry date of the consumable item
     */
    public Consumable(String name, String notes, double price, LocalDateTime expirationDate) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.expirationDate = expirationDate;
    }

    /**
     * getter method for the notes
     * @return String notes of the consumable item
     */
    public String getNotes() {
        return notes;
    }

    /**
     * getter method for the price
     * @return double price of the consumable item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gives the expiration date of the food iem
     * @return localDateTime date of the expiration
     */
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * Give the name of the food
     * @return string name of the food
     */
    public String getName() {
        return name;
    }

    /**
     * calculates the number of days till expiration
     * @return long days till expiry
     */
    public long daysTillExpire() {
        LocalDateTime dateNow = LocalDateTime.now().withHour(23).withMinute(59).withSecond(0).withNano(0);
        long numDays;
        numDays = dateNow.until(this.expirationDate, DAYS);
        return numDays;
    }

    /**
     * Method for setting the name
     * @param name name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method for setting the notes
     * @param notes the note to be set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Method for setting the price
     * @param price price to be set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Method to set the expiry date
     * @param expirationDate expiry date to be set
     */
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * This is an override comparable function to aid with comparing Consumable
     * objects. Such as in the case with sorting or any comparisons.
     * @param other - the object to compare with
     * @return positive int if bigger, 0 if same, negative int if smaller
     */
    @Override
    public int compareTo(Consumable other) {
        return expirationDate.compareTo(other.expirationDate);
    }

}

package ca.cmpt213.a4.webappserver.control;

import ca.cmpt213.a4.webappserver.model.Consumable;
import ca.cmpt213.a4.webappserver.model.ConsumableFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Manages the food objects and operations pertaining to organization of these objects
 * Most logic done in this class. Contains a list of food objects
 * methods to add, delete the list etc. according to the menu options
 * @author Rio SamsonC
 */
public class ConsumableManager implements Iterable<Consumable>{
    private List<Consumable> foods = new ArrayList<>();
    private List<Integer> foodTypes = new ArrayList<>();
    private List<Double> quantities = new ArrayList<>();
    private ConsumableFactory factory = new ConsumableFactory();

    private List<Integer> foodTypesExp = new ArrayList<>();
    private List<Double> quantitiesExp = new ArrayList<>();
    private List<Integer> foodTypesNonExp = new ArrayList<>();
    private List<Double> quantitiesNonExp = new ArrayList<>();
    private List<Integer> foodTypesWeek = new ArrayList<>();
    private List<Double> quantitiesWeek = new ArrayList<>();

    /**
     * basic empty constructor
     */
    public ConsumableManager() {
    }

    /**
     * basic empty constructor
     */
    public ConsumableManager(boolean isLoad) {
        this.load();
    }

    /**
     * Adds a new food item to the foods ArrayList in the right position: oldest to newest
     * @param food - the object food that is being added
     */
    public void add(Consumable food, int foodType, double quantity) {
        if (foods.isEmpty()) {
            foods.add(food);
            quantities.add(quantity);
            foodTypes.add(foodType);
        } else {
            int i = 0;
            //while loop to go through the list and add food to valid index
            //compares expiration dates
            boolean isDone = false;
            while(!isDone) {
                int compare = food.getExpirationDate().compareTo(foods.get(i).getExpirationDate());
                if (compare <= 0) {
                    foods.add(i, food);
                    quantities.add(i, quantity);
                    foodTypes.add(i, foodType);
                    isDone = true;
                } else if (i + 1 == foods.size()) {
                    foods.add(food);
                    quantities.add(quantity);
                    foodTypes.add(foodType);
                    isDone = true;
                }
                i++;
            }
        }
    }

    /**
     * @return true if foods list is empty, false otherwise
     */
    public boolean isEmpty() {
        return foods.isEmpty();
    }

    /**
     * @return size of list containing food
     */
    public int size() {
        return foods.size();
    }

    /**
     * Deletes the food object from foods array list
     * @param index of which food item to remove
     */
    public void delete(int index) {
        foods.remove(index);
        quantities.remove(index);
        foodTypes.remove(index);
    }

    /**
     * Returns the name of the food item requested from list
     * calls Food method to get its name
     * @param index of the food item in the list
     * @return string name of the food item
     */
    public String getName(int index) {
        return foods.get(index).getName();
    }

    /**
     * This method is called to know if the given item is expired
     * compared to today or not.
     * @param food the item being compared
     * @return true if expired, false if not expired
     */
    public boolean isExpired(Consumable food) {
        LocalDateTime dateNow = LocalDateTime.now();
        int compare = food.getExpirationDate().compareTo(dateNow);
        return compare <= 0;
    }

    /**
     * Makes an ArrayList of all the food oldest to newest
     * @return ArrayList of the foods
     */
    public ArrayList<Consumable> fullList() {
        ArrayList<Consumable> list = new ArrayList<>();
        for (Consumable food : foods) {
            list.add(food);
        }
        return list;
    }

    /**
     * This method gives a list of the quantities of each item
     * @return ArrayList</Double> of the quantity data
     */
    public ArrayList<Double> quantitiesList() {
        ArrayList<Double> list = new ArrayList<>();
        for (double value : quantities) {
            list.add(value);
        }
        return list;
    }

    /**
     * This method gives a list of the item types of each item
     * @return ArrayList</Integer> of the item type data
     */
    public ArrayList<Integer> typesList() {
        ArrayList<Integer> list = new ArrayList<>();
        for (Integer value : foodTypes) {
            list.add(value);
        }
        return list;
    }

    /**
     * Makes an ArrayList of all the food that has already expired excluding today. oldest to newest
     * calls food method to check if the food is expired
     * @return ArrayList of the expired foods
     */
    public ArrayList<Consumable> expiredList() {
        ArrayList<Consumable> expiryList = new ArrayList<>();
        foodTypesExp = new ArrayList<>();
        quantitiesExp = new ArrayList<>();
        int i = 0;
        for (Consumable food : foods) {
            if (isExpired(food)) {
                expiryList.add(food);
                foodTypesExp.add(foodTypes.get(i));
                quantitiesExp.add(quantities.get(i));
            }
            i++;
        }
        return expiryList;
    }

    /**
     * This method gives a list of the quantities of each expired item
     * @return ArrayList</Double> of the expired quantity data
     */
    public ArrayList<Double> expQuantitiesList() {
        expiredList();
        ArrayList<Double> list = new ArrayList<>();
        for (Double value : quantitiesExp) {
            list.add(value);
        }
        return list;
    }

    /**
     * This method gives a list of the item types of each expired item
     * @return ArrayList</Integer> of the expired item type data
     */
    public ArrayList<Integer> expTypesList() {
        expiredList();
        ArrayList<Integer> list = new ArrayList<>();
        for (Integer value : foodTypesExp) {
            list.add(value);
        }
        return list;
    }

    /**
     * Makes an ArrayList of all the foods that are not already expired including today. Oldest to newest
     * calls food method to check if the food is expired
     * @return ArrayList of all the nonExpired food
     */
    public ArrayList<Consumable> nonExpiredList() {
        ArrayList<Consumable> nonExpiryList = new ArrayList<>();
        foodTypesNonExp = new ArrayList<>();
        quantitiesNonExp = new ArrayList<>();
        int i = 0;
        for (Consumable food : foods) {
            if (!isExpired(food)) {
                nonExpiryList.add(food);
                foodTypesNonExp.add(foodTypes.get(i));
                quantitiesNonExp.add(quantities.get(i));
            }
            i++;
        }
        return nonExpiryList;
    }

    /**
     * This method gives a list of the quantities of each non expired item
     * @return ArrayList</Double> of the non expired quantity data
     */
    public ArrayList<Double> expNonQuantitiesList() {
        nonExpiredList();
        ArrayList<Double> list = new ArrayList<>();
        for (Double value : quantitiesNonExp) {
            list.add(value);
        }
        return list;
    }

    /**
     * This method gives a list of the item types of each non expired item
     * @return ArrayList</Integer> of the non expired item type data
     */
    public ArrayList<Integer> expNonTypesList() {
        nonExpiredList();
        ArrayList<Integer> list = new ArrayList<>();
        for (Integer value : foodTypesNonExp) {
            list.add(value);
        }
        return list;
    }

    /**
     * Makes an ArrayList of all the foods that will expire in the next 7 days inclusive.
     * calls food method to check how many days for food to expire
     * @return ArrayList of foods that expire in 7 days
     */
    public ArrayList<Consumable> expireInWeek() {
        ArrayList<Consumable> expireInWeekList = new ArrayList<>();
        foodTypesWeek = new ArrayList<>();
        quantitiesWeek = new ArrayList<>();
        int i = 0;
        for (Consumable food : foods) {
            if ((food.daysTillExpire() <= 7) && (food.daysTillExpire() >= 0)) {
                expireInWeekList.add(food);
                foodTypesWeek.add(foodTypes.get(i));
                quantitiesWeek.add(quantities.get(i));
            }
            i++;
        }
        return expireInWeekList;
    }

    /**
     * This method gives a list of the quantities of each week expired item
     * @return ArrayList</Double> of the week expired quantity data
     */
    public ArrayList<Double> weekQuantitiesList() {
        expireInWeek();
        ArrayList<Double> list = new ArrayList<>();
        for (Double value : quantitiesWeek) {
            list.add(value);
        }
        return list;
    }

    /**
     * This method gives a list of the item types of each week expired item
     * @return ArrayList</Integer> of the week expired item type data
     */
    public ArrayList<Integer> weekTypesList() {
        expireInWeek();
        ArrayList<Integer> list = new ArrayList<>();
        for (Integer value : foodTypesWeek) {
            list.add(value);
        }
        return list;
    }

    /**
     * Saves the whole FoodManager object as a json file to save the data stored in the foods list.
     * Will be read when reopened to load the data from json file
     */
    public void saveFoods() {
        Gson myGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter,
                                      LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }
                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }
                }).create();

        String json = myGson.toJson(this);

        try {
            //write json string to a file to save all the progress
            File file = new File("./JSON.json");
            FileWriter fWriter;
            fWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fWriter);
            printWriter.println(json);
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error: Could not save to json file!");
        }
    }

    /**
     * Saves the array of foodTypes as a JSON string to be uploaded
     * again. Has information to create new consumables.
     */
    public void saveFoodTypes() {
        Gson myGson = new Gson();

        String json = myGson.toJson(foodTypes);

        try {
            //write json string to a file to save all the progress
            File file = new File("./foodTypes.json");
            FileWriter fWriter;
            fWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fWriter);
            printWriter.println(json);
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error: Could not save to json file!");
        }
    }

    /**
     * Saves the array of foodQuantities as a JSON string to be uploaded
     * again. Has information to create new consumables.
     */
    public void saveQuantities() {
        Gson myGson = new Gson();

        String json = myGson.toJson(quantities);

        try {
            //write json string to a file to save all the progress
            File file = new File("./foodQuantities.json");
            FileWriter fWriter;
            fWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fWriter);
            printWriter.println(json);
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error: Could not save to json file!");
        }
    }

    /**
     * Loads ConsumableManager class from last save and updates the foods list
     * imports information from json file containing saved information.
     * Also loads the array of foodTypes, and array of foodQuantities.
     * With this info, repopulates the food array with consumables
     */
    public void load() {
        Gson myGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter,
                                      LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }
                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }
                }).create();

        //read the json file to update the information from last save
        try {
            File input = new File("./JSON.json");
            Scanner scan = new Scanner(input);
            String scanned = scan.nextLine();

            File inputType = new File("./foodTypes.json");
            Scanner scanType = new Scanner(inputType);
            String scannedType = scanType.nextLine();

            File inputQuantity = new File("./foodQuantities.json");
            Scanner scanQuantity = new Scanner(inputQuantity);
            String scannedQuantity = scanQuantity.nextLine();

            ConsumableManager load = myGson.fromJson(scanned, ConsumableManager.class);
            Type typesType = new TypeToken<ArrayList<Integer>>(){}.getType();
            Type quantitiesType = new TypeToken<ArrayList<Double>>(){}.getType();
            List<Integer> types = myGson.fromJson(scannedType, typesType);
            List<Double> quantitiesLoad = myGson.fromJson(scannedQuantity, quantitiesType);

            foods = new ArrayList<>();
            List<Consumable> loadedItems = load.foods;
            int i = 0;
            for (Consumable items: loadedItems) {
                int foodType = types.get(i);
                double quantity = quantitiesLoad.get(i);
                Consumable consumable = factory.getInstance(foodType, items.getName(),
                        items.getNotes(), items.getPrice(), items.getExpirationDate(), quantity);
                this.add(consumable, foodType, quantity);
                i++;
            }

        } catch (FileNotFoundException e) {
        }
    }

    /**
     * Iterator to go through the food objects in the foods list
     * @return an iterator for the list
     */
    @Override
    public Iterator<Consumable> iterator() {
        return foods.iterator();
    }
}

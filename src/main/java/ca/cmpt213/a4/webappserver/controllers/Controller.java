package ca.cmpt213.a4.webappserver.controllers;

import ca.cmpt213.a4.webappserver.control.ConsumableManager;
import ca.cmpt213.a4.webappserver.model.Consumable;
import ca.cmpt213.a4.webappserver.model.DrinkItem;
import ca.cmpt213.a4.webappserver.model.FoodItem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * This is the Controller class that is used to handle all the https requests
 * sent by the clients and does the appropriate functions for each command.
 */
@RestController
public class Controller {
    private ConsumableManager manager = new ConsumableManager(true);

    /**
     * This method lets client know if the server is running
     * @return String on if the system is up
     */
    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    public String getPing() {
        return "System is up!";
    }

    /**
     * This method adds the food item given by the client into the manager.
     * @param foodItem food item added
     * @param weight weight of the item
     * @return returns the updated list
     */
    @PostMapping("/addFood/{quantity}")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Consumable> addFoodItem(@RequestBody FoodItem foodItem,
                                @PathVariable("quantity") double weight) {
        manager.add(foodItem, 1, weight);
        return manager.fullList();
    }

    /**
     * This method adds the drink item given by the client into the manager.
     * @param drinkItem food item added
     * @param volume volume of the item
     * @return returns the updated list
     */
    @PostMapping("/addDrink/{quantity}")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Consumable> addDrinkItem(@RequestBody DrinkItem drinkItem,
                                @PathVariable("quantity") double volume) {
        manager.add(drinkItem, 2, volume);
        return manager.fullList();
    }

    /**
     * This method removes the drink item given by the client from the manager.
     * @param index item removed
     * @return returns the updated list
     */
    @PostMapping("/removeItem/{index}")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Consumable> removeItem(@PathVariable("index") int index) {
        manager.delete(index);
        return manager.fullList();
    }

    /**
     * This method gives the list of all consumable items
     * @return ArrayList</Consumable> of all the items
     */
    @GetMapping("/listAll")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Consumable> getAll() {
        return manager.fullList();
    }

//    @GetMapping("/listAllFoodItems")
//    @ResponseStatus(HttpStatus.OK)
//    public ArrayList<Consumable> getAllFoodItems() {
//        ArrayList<Consumable> fullList = manager.fullList();
//        ArrayList<Consumable> foodList = new ArrayList<>();
//        for(Consumable consumable: fullList) {
//            if(consumable instanceof FoodItem) {
//                foodList.add(consumable);
//            }
//        }
//        return foodList;
//    }

    /**
     * This method gives the list of expired consumable items
     * @return ArrayList</Consumable> of the expired items
     */
    @GetMapping("/listExpired")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Consumable> getExpired() {
        return manager.expiredList();
    }

    /**
     * This method gives the list of non expired consumable items
     * @return ArrayList</Consumable> of the non expired items
     */
    @GetMapping("/listNonExpired")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Consumable> getNotExpired() {
        return manager.nonExpiredList();
    }

    /**
     * This method gives the list of week expired consumable items
     * @return ArrayList</Consumable> of the week expired items
     */
    @GetMapping("/listExpiringIn7Days")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Consumable> getSoonExpiring() {
        return manager.expireInWeek();
    }

    /**
     * This method is called by the client to indicate exit status of client
     * The server is then updated on all the changes made in the JSON save.
     */
    @GetMapping("/exit")
    @ResponseStatus(HttpStatus.OK)
    public void exitCommand() {
        manager.saveFoods();
        manager.saveFoodTypes();
        manager.saveQuantities();
    }

    //extra methods for the client side - helps to display the right to-string when needed
    //The data these methods return are the type of consumable items they are and their quantities
    /**
     * This method is called by client to get list of all quantities
     * @return ArrayList of all quantities
     */
    @GetMapping("/getQuantities")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Double> getQuantityList() {
        return manager.quantitiesList();
    }

    /**
     * This method is called by client to get list of all item types
     * @return ArrayList of item all types
     */
    @GetMapping("/getTypes")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Integer> getTypesList() {
        return manager.typesList();
    }

    /**
     * This method is called by client to get list of expired quantities
     * @return ArrayList of expired quantities
     */
    @GetMapping("/getExpQuantities")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Double> getExpQuantityList() {
        return manager.expQuantitiesList();
    }

    /**
     * This method is called by client to get list of expired item types
     * @return ArrayList of expired item types
     */
    @GetMapping("/getExpTypes")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Integer> getExpTypesList() {
        return manager.expTypesList();
    }

    /**
     * This method is called by client to get list of non expired quantities
     * @return ArrayList of non expired quantities
     */
    @GetMapping("/getNonExpQuantities")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Double> getNonExpQuantityList() {
        return manager.expNonQuantitiesList();
    }

    /**
     * This method is called by client to get list of non expired item types
     * @return ArrayList of non expired item types
     */
    @GetMapping("/getNonExpTypes")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Integer> expTypesList() {
        return manager.expNonTypesList();
    }

    /**
     * This method is called by client to get list of week expired quantities
     * @return ArrayList of week expired quantities
     */
    @GetMapping("/getWeekExpQuantities")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Double> getWeekExpQuantities() {
        return manager.weekQuantitiesList();
    }

    /**
     * This method is called by client to get list of week expired item types
     * @return ArrayList of week expired item types
     */
    @GetMapping("/geWeekExpTypes")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Integer> geWeekExpTypes() {
        return manager.weekTypesList();
    }


    //exception handling
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid ID.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void invalidIdExceptionHandler() {

    }

    //throw new IllegalArgumentException();
}

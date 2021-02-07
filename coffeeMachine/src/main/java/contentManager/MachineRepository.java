package contentManager;

import requestDtos.Beverage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MachineRepository {
    private static Map<String, Beverage> beverages = new ConcurrentHashMap<>();

    /**
     * This function returns the Map for beverages possible in the machine
     * @return
     */
    public static Map<String, Beverage> getBeverages() {
        return beverages;
    }

    /**
     * This function adds any beverage to the machine on fly
     * @param name
     * @param beverage
     */
    public static void addBeverage(String name, Beverage beverage) {
        beverages.put(name, beverage);
    }
}

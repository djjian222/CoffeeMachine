package contentManager;

import requestDtos.Beverage;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ContentManager {

    private static Map<String, Integer> machineContent = new ConcurrentHashMap<>();

    /**
     * To add the ingredient quantity to the machine
     * @param ingredient
     * @param quantity
     */
    public static void addIngredientToMachine(String ingredient, Integer quantity) {
        Integer existingQuantity = machineContent.get(ingredient);

        if(existingQuantity == null) {
            machineContent.put(ingredient, quantity);
            return;
        }
        machineContent.put(ingredient, existingQuantity+quantity);
    }

    /**
     * To get the machineContent map
     * @return
     */
    public static Map<String, Integer> getMachineContent() {
        return machineContent;
    }

    /**
     * To get the list of ingredients which are present in machine
     * @return
     */
    public static Set<String> getIngredients() {
        return machineContent.keySet();
    }

    /**
     * To get the quantity for any ingredient
     * @param ingredient
     * @return
     */
    public static Integer getQuantity(String ingredient) {
        if(machineContent.get(ingredient) != null) {
            return machineContent.get(ingredient);
        }
        return null;
    }

    /**
     * To reduce the quantity whenever any drink is successfully prepared
     * @param ingredient
     * @param quantity
     */
    public static synchronized void reduceContent(String ingredient, Integer quantity) {
        int existingQuantity = machineContent.get(ingredient);
        machineContent.put(ingredient, existingQuantity-quantity);
    }

    /**
     * Function to check if the ingredient quantity is low for any drink possible in the machine
     * @param ingredient
     * @return boolean
     */
    public static boolean checkIfIngredientLow(String ingredient) {
        Map<String, Beverage> beverages = MachineRepository.getBeverages();

        Integer currQuantity = ContentManager.getQuantity(ingredient);

        for(Beverage b : beverages.values()) {
            if(b.getIngredients().get(ingredient) != null && b.getIngredients().get(ingredient) > currQuantity) {
                return true;
            }
        }
        return false;
    }
}

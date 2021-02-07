package operations;

import requestDtos.Beverage;
import contentManager.ContentManager;
import contentManager.MachineRepository;

import java.util.Map;


public class BeverageMaker implements Runnable{
    private String beverageName;

    public BeverageMaker(String beverageName) {
        this.beverageName = beverageName;
    }

    /**
     * This method is responsible to check if it is possible to dispense the asked beverage
     * @return boolean which denotes if beverage is available
     */
    public boolean makeBeverage() {
        Map<String, Beverage> beverageMap = MachineRepository.getBeverages();

        if(beverageMap.get(beverageName) == null) {
            System.out.println(beverageName + " Option not available!!!");
            return false;
        }

        Beverage beverage = beverageMap.get(beverageName);
        Map<String, Integer> ingredients = beverage.getIngredients();
        for(Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            if(ContentManager.getQuantity(entry.getKey()) == null ||
                    ContentManager.getQuantity(entry.getKey()) < entry.getValue()) {
                System.out.println(beverageName + " cannot be prepared as "+ entry.getKey() + " is not available!!\n");
                return false;
            }
        }

        for(Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            ContentManager.reduceContent(entry.getKey(), entry.getValue());
        }
        System.out.println(beverageName + " is ready for you!!\n");
        return true;
    }

    @Override
    public void run() {
        makeBeverage();
    }
}

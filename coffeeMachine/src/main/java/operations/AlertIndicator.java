package operations;

import contentManager.ContentManager;

import java.util.Set;

/**
 * This class is responsible to check for the ingredient and to refill if any ingredient is low
 */
public class AlertIndicator implements Runnable {
    Set<String> ingredients;

    public AlertIndicator(Set<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public void run() {
        while(true) {
            for(String s : ingredients) {
                if(ContentManager.checkIfIngredientLow(s)) {
                    synchronized (System.out) {
                        System.out.println(s + " is low in quantity, needs refill!!");
                        ContentManager.addIngredientToMachine(s, 500);
                        System.out.println(s + " is now added back!!");
                    }

                }
            }
        }
    }
}

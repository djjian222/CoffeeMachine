package requestDtos;

import java.util.Map;

public class Beverage {
    private String name;
    private Map<String, Integer> ingredients;

    public Beverage(String name, Map<String, Integer> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }
}

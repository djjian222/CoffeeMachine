package requestDtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Map;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName(value = "machine")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Machine {
    @JsonProperty("outlets")
    private Outlets outlets;

    @JsonProperty("beverages")
    private Map<String, Map<String, Integer>> beverages;

    @JsonProperty("total_items_quantity")
    private Map<String, Integer> totalItemsQuantity;

    public Map<String, Map<String, Integer>> getBeverages() {
        return beverages;
    }

    public Map<String, Integer> getTotalItemsQuantity() {
        return totalItemsQuantity;
    }

    public int getOutlets() {
        return outlets.numberOfOutlets;
    }
}

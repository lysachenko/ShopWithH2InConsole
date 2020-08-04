package model;

import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {

    private long id;
    private Map<Product, Integer> positionMap;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<Product, Integer> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<Product, Integer> positionMap) {
        this.positionMap = positionMap;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" + positionMap.entrySet().stream()
                .map(productIntegerEntry ->
                        "\n\t Product: " + productIntegerEntry.getKey().toString()
                                + ", amount: " + productIntegerEntry.getValue().toString())
                .collect(Collectors.joining())
                + '}';
    }
}

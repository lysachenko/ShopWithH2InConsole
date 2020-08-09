package model;

import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {

    private final User user;
    private volatile Map<Product, Integer> positionMap;

    public ShoppingCart(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
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

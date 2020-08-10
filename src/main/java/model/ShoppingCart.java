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

    private float getTotalSumInCart() {
        return (float) positionMap
                .entrySet()
                .stream()
                .mapToDouble(positionEntry -> positionEntry.getKey().getPrice() * positionEntry.getValue())
                .sum();
    }

    @Override
    public String toString() {

        return "********************************************\n" +
                "ShoppingCart:" + positionMap.entrySet().stream()
                .map(productIntegerEntry ->
                        "\n\tProduct: " + productIntegerEntry.getKey().toString()
                                + ", ordered quantity: " + productIntegerEntry.getValue().toString() + "\n")
                .collect(Collectors.joining())
                + "\nTotal sum = " + getTotalSumInCart()
                + "\n********************************************";
    }
}

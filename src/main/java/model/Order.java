package model;

import java.util.Map;
import java.util.stream.Collectors;

public class Order {
    private long id;
    private User user;
    private OrderStatus orderStatus;
    private Map<Product, Integer> positionMap;

    public Order() {
    }

    public Order(long id, User user, OrderStatus orderStatus, Map<Product, Integer> positionMap) {
        this.id = id;
        this.user = user;
        this.orderStatus = orderStatus;
        this.positionMap = positionMap;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Map<Product, Integer> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<Product, Integer> positionMap) {
        this.positionMap = positionMap;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", orderStatus=" + orderStatus +
                ", \npositions: " +
                positionMap.entrySet().stream()
                        .map(productIntegerEntry ->
                                "\n\t Product: " + productIntegerEntry.getKey().toString()
                                        + ", amount: " + productIntegerEntry.getValue().toString())
                        .collect(Collectors.joining())
                + '}';
    }
}

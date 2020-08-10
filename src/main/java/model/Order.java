package model;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Order {
    private long id;
    private User user;
    private OrderStatus status;
    private Map<Product, Integer> positionMap;

    public Order() {
    }

    public Order(long id, User user, OrderStatus status, Map<Product, Integer> positionMap) {
        this.id = id;
        this.user = user;
        this.status = status;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Map<Product, Integer> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<Product, Integer> positionMap) {
        this.positionMap = positionMap;
    }

    private float getTotalSumInOrder() {
        return (float) positionMap
                .entrySet()
                .stream()
                .mapToDouble(positionEntry -> positionEntry.getKey().getPrice() * positionEntry.getValue())
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "********************************************\n" +
                "Order " +
                "id=" + id +
                "\nuser: id=" + user.getId() + ", username=" + user.getUsername() +
                "\norderStatus=" + status +
                "\npositions: " +
                positionMap.entrySet().stream()
                        .map(productIntegerEntry ->
                                "\n\tProduct: " + productIntegerEntry.getKey().toString()
                                        + ", ordered quantity: " + productIntegerEntry.getValue().toString() + "\n")
                        .collect(Collectors.joining())
                + "\nTotal order sum = " + getTotalSumInOrder()
                + "\n********************************************";
    }
}

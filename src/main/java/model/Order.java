package model;

import java.util.Map;

public class Order {
    private long id;
    private long userId;
    private OrderStatus orderStatus;
    private Map<Product,Integer> positionMap;

    public Order(long id, long userId, OrderStatus orderStatus, Map<Product, Integer> positionMap) {
        this.id = id;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.positionMap = positionMap;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
}

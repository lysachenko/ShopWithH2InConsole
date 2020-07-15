package service;

import model.Order;

import java.util.List;

public interface OrderService {

    void save(Order order);

    void update(Order order);

    void delete(Order order);

    Order findById(long id);

    List<Order> findOrdersByUserId(long id);

    List<Order> findAll();
}

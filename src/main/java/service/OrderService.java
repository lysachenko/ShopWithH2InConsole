package service;

import model.Order;
import model.User;

import java.util.List;

public interface OrderService {

    void save(Order order);

    void update(Order order);

    void delete(Order order);

    Order findById(long id);

    List<Order> findOrdersByUser(User user);

    List<Order> findAll();
}

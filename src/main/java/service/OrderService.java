package service;

import model.Order;
import model.Product;
import model.User;

import java.util.List;
import java.util.Map;

public interface OrderService {

    void createOrder(User user, Map<Product, Integer> positionMap);

    void changeOrderStatus(Order order, String orderStatus);

    void save(Order order);

    void update(Order order);

    void delete(Order order);

    Order findById(long id);

    List<Order> findOrdersByUser(User user);

    List<Order> findAll();
}

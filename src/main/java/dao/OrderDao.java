package dao;

import model.Order;

import java.util.List;

public interface OrderDao {

    void save(Order order);

    void update(Order order);

    void delete(Order order);

    Order getById(long id);

    List<Order> getOrdersByUserId(long id);

    List<Order> getAll();
}

package service.impl;

import dao.OrderDao;
import dao.impl.OrderDaoImpl;
import model.Order;
import model.OrderStatus;
import model.Product;
import model.User;
import service.OrderService;

import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao = new OrderDaoImpl();

    @Override
    public void createOrder(User user, Map<Product, Integer> positionMap) {
        Order order = new Order();
        order.setUser(user);
        order.setPositionMap(positionMap);
        order.setStatus(OrderStatus.PRE_CHECK_OUT);
        orderDao.save(order);
    }

    @Override
    public void changeOrderStatus(Order order, String orderStatus) {
        if (OrderStatus.PRE_CHECK_OUT.toString().equals(orderStatus)
                || OrderStatus.CHECKED_OUT.toString().equals(orderStatus)
                || OrderStatus.SENT.toString().equals(orderStatus)
                || OrderStatus.DONE.toString().equals(orderStatus)
                || OrderStatus.REJECTED.toString().equals(orderStatus)
        ) {
            order.setStatus(OrderStatus.valueOf(orderStatus));
            orderDao.update(order);
        } else {
            System.out.println("Incorrect order status!");
        }
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }

    @Override
    public Order findById(long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findOrdersByUser(User user) {
        return orderDao.findOrdersByUser(user);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public double positionCalculate(Product product, int amount) {
        return product.getPrice() * amount;
    }
}

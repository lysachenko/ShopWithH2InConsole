package service;

import dao.OrderDao;
import dao.OrderDaoIml;
import model.Order;
import model.Product;
import model.User;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static final OrderService orderService = new OrderServiceImpl();

    private final OrderDao orderDao = OrderDaoIml.getInstance();

    private OrderServiceImpl() {
    }

    public static OrderService getInstance() {
        return orderService;
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

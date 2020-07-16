package dao;

import model.Order;
import model.OrderStatus;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class OrderDaoIml implements OrderDao {

    private static final OrderDao orderDao = new OrderDaoIml();
    private static final ProductDao productDao = ProductDaoImpl.getInstance();
    private static final UserDao userDao = UserDaoIml.getInstance();
    private static final Map<Long, Order> orderMap = new TreeMap<>();

    static {
        orderMap.put(1L,new Order(1,userDao.findById(2), OrderStatus.DONE, Map.of(
            productDao.findById(1), 10,
            productDao.findById(13), 8,
            productDao.findById(21), 15
        )));
        orderMap.put(2L,new Order(2,userDao.findById(3), OrderStatus.SENT, Map.of(
            productDao.findById(18), 11,
            productDao.findById(17), 9
        )));
        orderMap.put(3L,new Order(3,userDao.findById(2), OrderStatus.PRE_CHECKOUT, Map.of(
            productDao.findById(16),5
        )));
        orderMap.put(4L,new Order(4,userDao.findById(3), OrderStatus.CHECKED_OUT, Map.of(
            productDao.findById(14),5,
            productDao.findById(21),8
        )));
        orderMap.put(5L,new Order(5,userDao.findById(3), OrderStatus.REJECTED, Map.of(
                productDao.findById(15),5
        )));
    }

    private OrderDaoIml() {
    }

    public static OrderDao getInstance() {
        return orderDao;
    }

    @Override
    public void save(Order order) {
        orderMap.put(order.getId(), order);
    }

    @Override
    public void update(Order order) {
        orderMap.put(order.getId(), order);
    }

    @Override
    public void delete(Order order) {
        orderMap.remove(order.getId());
    }

    @Override
    public Order findById(long id) {
        return orderMap.get(id);
    }

    @Override
    public List<Order> findOrdersByUser(User user) {
        return orderMap.values().stream()
                .filter(order -> order.getUser().equals(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orderMap.values());
    }
}

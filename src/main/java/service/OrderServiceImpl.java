package service;

import dao.OrderDao;
import dao.ProductDao;
import dao.impl.OrderDaoImpl;
import dao.impl.ProductDaoImpl;
import model.Order;
import model.OrderStatus;
import model.Product;
import model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao = new OrderDaoImpl();
    private final ProductDao productDao = new ProductDaoImpl();
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void create(User user) {
        Order order = new Order();
        order.setUser(user);
        Map<Product, Integer> productIntegerMap = new HashMap<>();
        boolean want = true;

        while (want) {

            String productId;
            do {
                System.out.print("Enter id of product what you want to buy: ");
                productId = scanner.next();
                if (productDao.findById(Long.parseLong(productId)) == null) {
                    System.out.println("This product does not exist! Try again.");
                }
            } while (productDao.findById(Long.parseLong(productId)) == null);

            String amount;
            do {
                System.out.print("Enter amount: ");
                amount = scanner.next();
                if (Integer.parseInt(amount) <= 0
                        && productDao.findById(Long.parseLong(productId)).getAmount() < Integer.parseInt(amount)) {
                    System.out.println("Incorrect amount! Try again.");
                }
            } while (Integer.parseInt(amount) <= 0);
            productIntegerMap.put(productDao.findById(Long.parseLong(productId)), Integer.parseInt(amount));
            System.out.print("Anything else? (Y - yes, N - no) : ");
            String choice = scanner.next();
            want = choice.equals("Y") || choice.equals("y");
        }

        order.setPositionMap(productIntegerMap);
        order.setOrderStatus(OrderStatus.PRE_CHECKOUT);
        orderDao.save(order);
    }

    @Override
    public void changeOrderStatus(String orderStatus) {
        String orderId;
        do {
            System.out.println("Enter order ID to change status: ");
            orderId = scanner.next();
            if (orderDao.findById(Long.parseLong(orderId)) == null) {
                System.out.println("Incorrect order! Try again.");
            }
        } while (orderDao.findById(Long.parseLong(orderId)) == null);
        Order order = orderDao.findById(Integer.parseInt(orderId));

        if (OrderStatus.PRE_CHECKOUT.toString().equals(orderStatus)
                || OrderStatus.CHECKED_OUT.toString().equals(orderStatus)
                || OrderStatus.SENT.toString().equals(orderStatus)
                || OrderStatus.DONE.toString().equals(orderStatus)
                || OrderStatus.REJECTED.toString().equals(orderStatus)
        ) {
            order.setOrderStatus(OrderStatus.valueOf(orderStatus));
        } else {
            System.out.println("Incorrect order status!");
        }
        orderDao.update(order);
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

package view.impl;


import model.Order;
import model.OrderStatus;
import model.Product;
import model.User;
import service.*;
import view.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserMainMenu implements Menu {

    private UserService userService = new UserServiceImpl();
    private OrderService orderService = new OrderServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    private String[] items = {
            "1.Show products",
            "2.Find product by name",
            "3.Create order",
            "0. Exit"};
    private Scanner scanner = new Scanner(System.in);
    private User user;

    public UserMainMenu(User user) {
        this.user = userService.findById(user.getId());
    }

    @Override
    public void show() {
        while (true) {
            showItems(items);
            System.out.println("-------------");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showProducts();
                    break;
                case 2:
                    showProductsByName();
                    break;
                case 3:
                    createOrder();
                    break;
                case 0:
                    exit();
                    break;
            }
        }
    }

    private void showProductsByName() {
        System.out.print("Enter name of product: ");
        String productName = scanner.next();
        System.out.print("Products with same name: ");
        productService.findByName(productName).forEach(System.out::println);
    }

    private void showProducts() {
        System.out.println("Product list:");
        productService.findAll().forEach(System.out::println);
    }

    private void createOrder() {
        Order order = new Order();
        order.setUser(user);
        Map<Product, Integer> productIntegerMap = new HashMap<>();
        boolean want = true;

        while (want) {
            System.out.print("Enter id of product what you want to buy: ");
            long productId = scanner.nextLong();
            System.out.print("Enter amount: ");
            Integer amount = scanner.nextInt();
            productIntegerMap.put(productService.findById(productId), amount);
            System.out.print("Anything else? (Y - yes, N - no) : ");
            String choice = scanner.next();
            want = choice.equals("Y") || choice.equals("y");
        }

        order.setPositionMap(productIntegerMap);
        order.setOrderStatus(OrderStatus.SENT);
        orderService.save(order);
    }

    @Override
    public void exit() {
        new LoginMenu().show();
    }
}

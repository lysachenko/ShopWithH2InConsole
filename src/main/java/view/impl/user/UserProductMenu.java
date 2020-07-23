package view.impl.user;

import model.User;
import service.OrderService;
import service.OrderServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;
import view.Menu;

import java.util.Scanner;

public class UserProductMenu implements Menu {

    private OrderService orderService = new OrderServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    private String[] items = {
            "1.Show products",
            "2.Find product by name",
            "3.Create order",
            "0. Exit"};
    private Scanner scanner = new Scanner(System.in);
    private User user;

    public UserProductMenu(User user) {
        this.user = user;
    }

    @Override
    public void show() {
        while (true) {
            showItems(items);
            System.out.println("-------------");
            System.out.print("Enter your choice: ");

            String choice = scanner.next();

            switch (choice) {
                case "1":
                    showProducts();
                    break;
                case "2":
                    showProductsByName();
                    break;
                case "3":
                    createOrder();
                    break;
                case "0":
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
        orderService.create(user);
    }

    //@Override
    public void exit() {
        new UserMainMenu(user).show();
    }
}

package view.impl.user;

import model.Product;
import model.User;
import service.OrderService;
import service.OrderServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;
import util.ScannerUtil;
import view.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserProductMenu implements Menu {

    private OrderService orderService = new OrderServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    private String[] items = {
            "1. Show products",
            "2. Find product by name",
            "3. Create order",
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

            int choice = ScannerUtil.getInt();

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
                    return;
            }
        }
    }

    private void showProducts() {
        System.out.println("Product list:");
        productService.findAll().forEach(System.out::println);
    }

    private void showProductsByName() {
        System.out.print("Enter name of product: ");
        String productName = scanner.next();
        System.out.print("Products with same name: ");
        productService.findByName(productName).forEach(System.out::println);
    }

    private void createOrder() {
        Map<Product, Integer> positionMap = new HashMap<>();
        boolean isOneMore = true;

        showProducts();
        while (isOneMore) {
            long productId;
            int amount;
            Product product;
            do {
                System.out.print("Enter ID of product what you want to buy: ");
                productId = ScannerUtil.getLong();
                product = productService.findById(productId);
                if (product == null) {
                    System.out.println("This product does not exist! Try again.");
                }
            } while (productService.findById(productId) == null);

            do {
                System.out.print("Enter amount: ");
                amount = ScannerUtil.getInt();
                if (amount <= 0 || product.getAmount() < amount) {
                    System.out.println("Incorrect amount! Try again.");
                }
            } while (amount <= 0 || product.getAmount() < amount);
            positionMap.put(product, amount);
            //Уменьшение количества товара на указанное пользователём количество
            product.setAmount(product.getAmount() - amount);
            productService.update(product);

            System.out.print("Anything else? (Y - yes, (another key) - no) : ");
            String choice = scanner.next();
            isOneMore = choice.equals("Y") || choice.equals("y");
        }
        orderService.createOrder(user, positionMap);
    }
}

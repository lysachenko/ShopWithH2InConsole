package view.impl.user;

import model.Product;
import model.User;
import service.ProductService;
import service.ShoppingCartService;
import service.impl.ProductServiceImpl;
import service.impl.ShoppingCartServiceImpl;
import util.ScannerUtil;
import view.Menu;

import java.util.List;
import java.util.Scanner;

public class UserProductMenu implements Menu {

    private final ProductService productService = new ProductServiceImpl();
    private final ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
    private final String[] items = {
            "1. Show products",
            "2. Find product by name",
            "3. Add product to cart",
            "0. Exit"};
    private final Scanner scanner = new Scanner(System.in);
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
                    addProductToShoppingCart();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void addProductToShoppingCart() {
        boolean isOneMore = true;
        long productId;
        int amount;
        Product product = null;

        showProducts();
        while (isOneMore) {
            System.out.print("Enter ID of product: ");
            productId = ScannerUtil.getLong();
            product = productService.findById(productId);
            if (product == null) {
                System.out.println("This product does not exist! Try again.");
                return;
            }

            do {
                System.out.print("Enter amount: ");
                amount = ScannerUtil.getInt();
                if (amount <= 0 || product.getAmount() < amount) {
                    System.out.println("Incorrect amount! Try again.");
                }
            } while (amount <= 0 || product.getAmount() < amount);

            shoppingCartService.addProduct(user, product, amount);

            System.out.print("Anything else? (Y - yes, (another key) - no) : ");
            String choice = scanner.next();
            isOneMore = choice.equals("Y") || choice.equals("y");
        }
        System.out.println("Product " + product.getName() + " added to cart!");
    }

    private void showProducts() {
        System.out.println("Product list:");
        List<Product> productList = productService.findAll();
        if (productList.isEmpty()) {
            System.out.println("Product list is empty!");
        } else {
            productList.forEach(System.out::println);
        }
    }

    private void showProductsByName() {
        System.out.print("Enter name of product: ");
        String productName = scanner.next();
        System.out.print("Products with same name: ");
        List<Product> productList = productService.findByName(productName);
        if (productList.isEmpty()) {
            System.out.println("No matches!");
        } else {
            productList.forEach(System.out::println);
        }
    }
}

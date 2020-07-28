package view.impl.admin;

import model.Product;
import service.ProductService;
import service.ProductServiceImpl;
import util.ScannerUtil;
import view.Menu;

import java.util.Scanner;

public class AdminProductMenu implements Menu {

    private ProductService productService = new ProductServiceImpl();
    private String[] items = {
            "1. Show all products",
            "2. Find products by name",
            "3. Add product",
            "4. Update product",
            "5. Delete product",
            "0. Exit"};
    private Scanner scanner = new Scanner(System.in);

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
                    createProduct();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void showProducts() {
        productService.findAll().forEach(System.out::println);
    }

    private void showProductsByName() {
        System.out.print("Enter product name: ");
        String productName = scanner.next();
        productService.findByName(productName).forEach(System.out::println);
    }

    private void createProduct() {
        Product product = new Product();

        System.out.println("Create product!");
        System.out.print("Enter product name: ");
        product.setName(scanner.nextLine());

        System.out.print("Enter product description: ");
        product.setDescription(scanner.nextLine());
        product.setPrice(enterProductPrice());
        product.setAmount(enterProductAmount());

        productService.save(product);
        System.out.println("Product saved!");
    }

    private void updateProduct() {
        long productId;
        Product product;
        showProducts();
        do {
            System.out.print("Enter product ID: ");
            productId = ScannerUtil.getLong();
            product = productService.findById(productId);
            if (product == null) {
                System.out.println("Incorrect product ID! Try again.");
            }
        } while (product == null);

        boolean isOneMore = true;

        while (isOneMore) {
            System.out.print("What you want to update? ");
            System.out.println("\n1. Product name" +
                    "\n2. Product description" +
                    "\n3. Product price" +
                    "\n4. Product amount in stock");
            System.out.print("enter your choice: ");
            int choiceId = ScannerUtil.getInt();
            switch (choiceId) {
                case 1:
                    updateProductName(product);
                    break;
                case 2:
                    updateProductDescription(product);
                    break;
                case 3:
                    updateProductPrice(product);
                    break;
                case 4:
                    updateProductAmount(product);
                    break;
            }
            System.out.print("Anything else? (Y - yes, N - no): ");
            String choice = scanner.nextLine();
            isOneMore = choice.equals("Y") || choice.equals("y");
        }
    }

    private void updateProductName(Product product) {
        System.out.print("Enter product name: ");
        scanner.nextLine();
        product.setName(scanner.nextLine());
        productService.update(product);
    }

    private void updateProductDescription(Product product) {
        System.out.print("Enter product description: ");
        product.setDescription(scanner.nextLine());
        productService.update(product);
    }

    private void updateProductPrice(Product product) {
        product.setPrice(enterProductPrice());
        productService.update(product);
    }


    private void updateProductAmount(Product product) {
        product.setAmount(enterProductAmount());
        productService.update(product);
    }

    private float enterProductPrice() {
        float price;
        do {
            System.out.print("Enter product price: ");
            price = ScannerUtil.getFloat();
            if (price <= 0) {
                System.out.println("Incorrect price! Try again.");
            }
        } while (price <= 0);
        return price;
    }

    private int enterProductAmount() {
        int amount;
        do {
            System.out.print("Enter product amount: ");
            amount = ScannerUtil.getInt();
            if (amount < 0) {
                System.out.println("Incorrect price! Try again.");
            }
        } while (amount < 0);
        return amount;
    }
}

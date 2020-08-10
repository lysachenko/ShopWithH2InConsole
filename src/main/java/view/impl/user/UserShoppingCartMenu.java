package view.impl.user;

import model.Product;
import model.ShoppingCart;
import model.User;
import service.OrderService;
import service.ProductService;
import service.ShoppingCartService;
import service.impl.OrderServiceImpl;
import service.impl.ProductServiceImpl;
import service.impl.ShoppingCartServiceImpl;
import util.ScannerUtil;
import view.Menu;

import java.util.Scanner;

public class UserShoppingCartMenu implements Menu {

    private final OrderService orderService = new OrderServiceImpl();
    private final ProductService productService = new ProductServiceImpl();
    private final ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
    private final String[] items = {
            "1. Show products in cart",
            "2. Remove product from cart ",
            "3. Update product amount",
            "4. Create order",
            "0. Exit"};
    private final Scanner scanner = new Scanner(System.in);
    private User user;

    public UserShoppingCartMenu(User user) {
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
                    removeProductFromCart();
                    break;
                case 3:
                    updateProductAmount();
                    break;
                case 4:
                    createOrder();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void updateProductAmount() {
        System.out.println("Update product amount!");
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByUser(user);

        if (shoppingCart.getPositionMap().isEmpty()) {
            System.out.println("Cart is empty! Add product to cart, please!");
            return;
        }
        showProducts();
        long productId;
        Product product;
        System.out.print("Enter product id: ");
        productId = ScannerUtil.getLong();
        product = productService.findById(productId);

        if (product == null || !shoppingCart.getPositionMap().containsKey(product)) {
            System.out.println("This product does not in shopping cart! Try again.");
            return;
        }

        int amount;
        do {
            System.out.print("Enter new amount: ");
            amount = ScannerUtil.getInt();
            if (amount <= 0 || product.getAmount() < amount) {
                System.out.println("Incorrect amount! Try again.");
            }
        } while (amount <= 0 || product.getAmount() < amount);

        shoppingCartService.updateProductAmount(user, product, amount);

        System.out.println("Product amount updated!");
    }

    private void showProducts() {
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByUser(user);

        if (shoppingCart.getPositionMap().isEmpty()) {
            System.out.println("Cart id empty!");
        } else {
            System.out.println(shoppingCart.toString());
        }
    }

    private void removeProductFromCart() {
        System.out.println("Deleting products from cart!");
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByUser(user);

        if (shoppingCart.getPositionMap().isEmpty()) {
            System.out.println("Cart is empty! Add product to cart, please!");
            return;
        }
        showProducts();
        Product product;
        System.out.print("Enter product id: ");
        product = productService.findById(ScannerUtil.getLong());

        if (product == null || !shoppingCart.getPositionMap().containsKey(product)) {
            System.out.println("This product does not in shopping cart! Try again.");
            return;
        }

        shoppingCartService.deleteProduct(user, product);

        System.out.println("Product " + product.getName() + " removed from cart!");
    }

    private void createOrder() {
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByUser(user);

        if (shoppingCart.getPositionMap().isEmpty()) {
            System.out.println("Cart is empty! Add product to cart, please!");
            return;
        }

        System.out.println("Do you want to create order with which positions? ");
        System.out.println(shoppingCart.toString());

        System.out.print("Enter \"Y\" - to confirm or any key to exit this menu: ");
        String answer = scanner.nextLine();
        if ("Y".equals(answer) || "y".equals(answer)) {
            //Reducing the quantity of goods by the quantity specified by the user
            shoppingCart.getPositionMap()
                    .forEach((product, amount) -> {
                        product.setAmount(product.getAmount() - amount);
                        productService.update(product);
                    });

            orderService.createOrder(user, shoppingCart.getPositionMap());
            shoppingCartService.clearProductList(shoppingCart);
            System.out.println("Order created!");
        } else {
            System.out.println("Exit from creating order!");
        }
    }
}

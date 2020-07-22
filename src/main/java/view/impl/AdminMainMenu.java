package view.impl;

import model.User;
import service.*;
import view.Menu;

import java.util.Scanner;

public class AdminMainMenu implements Menu {

    private UserService userService = new UserServiceImpl();
    private OrderService orderService = new OrderServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    private String[] items = {
            "1.Show orders",
            "2.Show users",
            "3.Block user",
            "4.Unblock user",

            "0. Exit"};
    private Scanner scanner = new Scanner(System.in);
    private User user;

    public AdminMainMenu(User user) {
        this.user = user;
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
                    showOrders();
                    break;
                case 2:
                    showUsers();
                    break;
                case 3:
                    blockUser();
                    break;
                case 4:
                    unBlockUser();
                    break;
                case 0:
                    exit();
                    break;
            }
        }
    }

    private void showUsers() {
        System.out.println("List of users: ");
        userService.findAll().forEach(System.out::println);
    }

    private void blockUser() {
        System.out.print("Enter username to block: ");
        String username = scanner.next();
        User user = userService.findByName(username);
        if (user != null) {
            userService.blockUser(user.getId());
        }
        System.out.print("User is blocked: ");
    }

    private void unBlockUser() {
        System.out.print("Enter username to unblock: ");
        String username = scanner.next();
        User user = userService.findByName(username);
        if (user != null) {
            userService.unBlockUser(user.getId());
        }
        System.out.print("User is unblocked: ");
    }

    private void showOrders() {
        System.out.println("Order list:");
        orderService.findAll().forEach(System.out::println);
    }

    @Override
    public void exit() {
        new LoginMenu().show();
    }
}

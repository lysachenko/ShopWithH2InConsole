package view.impl;


import model.UserRole;
import service.UserService;
import service.impl.UserServiceImpl;
import util.ScannerUtil;
import view.Menu;
import view.impl.admin.AdminMainMenu;
import view.impl.user.UserMainMenu;

import java.util.Scanner;

public class LoginMenu implements Menu {

    private UserService userService = new UserServiceImpl();
    private String[] items = {"1. Login", "2. Register", "0. Exit"};
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
                    loginSubMenu();
                    break;
                case 2:
                    registerSubMenu();
                    break;
                case 0:
                    exit();
                    break;
            }
        }
    }

    public void exit() {
        System.exit(0);
    }

    private void loginSubMenu() {
        System.out.println("Please enter to shop");
        System.out.print("Input login: ");
        String login = scanner.nextLine();

        System.out.print("Input password: ");
        String password = scanner.nextLine();

        if (userService.login(login, password) && userService.findByName(login).isActive()) {
            if (userService.findByName(login).getRole().equals(UserRole.CUSTOMER)) {
                new UserMainMenu(userService.findByName(login)).show();
            }
            if (userService.findByName(login).getRole().equals(UserRole.ADMIN)) {
                new AdminMainMenu(userService.findByName(login)).show();
            }
        } else {
            System.out.println("Wrong username/password!");
        }
    }

    private void registerSubMenu() {
        System.out.println("Registration!");
        System.out.print("Input login: ");
        String login = scanner.nextLine();

        System.out.print("Input password: ");
        String password = scanner.nextLine();

        if (userService.register(login, password)) {
            System.out.println("You registered!");
        } else {
            System.out.println("Registration fail!");
        }
    }
}

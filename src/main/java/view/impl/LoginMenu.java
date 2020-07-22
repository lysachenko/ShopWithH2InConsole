package view.impl;


import model.UserRole;
import service.UserService;
import service.UserServiceImpl;
import view.Menu;

import java.util.Scanner;

public class LoginMenu implements Menu {

    private UserService userService = new UserServiceImpl();
    private String[] items = {"1.Login", "2.Register", "0. Exit"};
    private Scanner scanner;

    @Override
    public void show() {
        while (true) {
            showItems(items);
            System.out.println("-------------");
            System.out.print("Enter your choice: ");

            scanner = new Scanner(System.in);

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    loginSubMenu(scanner);
                    break;
                case 2:
                    registerSubMenu(scanner);
                    break;
                case 0:
                    exit();
                    break;
            }
        }
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    private void loginSubMenu(Scanner scanner) {
        System.out.println("Please enter to shop");
        System.out.print("input login: ");
        String login = scanner.next();

        System.out.print("input password: ");
        String password = scanner.next();

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

    private void registerSubMenu(Scanner scanner) {
        System.out.println("Registration!");
        System.out.print("input login: ");
        String login = scanner.next();

        System.out.print("input password: ");
        String password = scanner.next();

        if (userService.register(login, password)) {
            System.out.println("You registered!");
        } else {
            System.out.println("Register fail!");
        }
    }
}

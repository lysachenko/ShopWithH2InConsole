package view.impl.user;


import model.User;
import service.UserService;
import service.UserServiceImpl;
import view.Menu;
import view.impl.LoginMenu;

import java.util.Scanner;

public class UserMainMenu implements Menu {

    private UserService userService = new UserServiceImpl();
    private String[] items = {
            "1.Product menu",
            "2.Order menu",
            "0.Exit"};
    private Scanner scanner = new Scanner(System.in);
    private User user;

    public UserMainMenu(User user) {
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
                    new UserProductMenu(user).show();
                    break;
                case "2":
                    new UserOrderMenu(user).show();
                    break;
                case "0":
                    //exit();
                    return;
            }
        }
    }

    //@Override
    public void exit() {
        new LoginMenu().show();
    }
}

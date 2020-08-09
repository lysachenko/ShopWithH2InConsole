package view.impl.user;

import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.ScannerUtil;
import view.Menu;

import java.util.Scanner;

public class UserProfileMenu implements Menu {

    private final UserService userService = new UserServiceImpl();
    private final Scanner scanner = new Scanner(System.in);
    private final String[] items = {
            "1. Show my profile",
            "2. Change password",
            "3. Deactivate account",
            "0. Exit"};
    private User user;

    public UserProfileMenu(User user) {
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
                    showUserProfile();
                    break;
                case 2:
                    changePassword();
                    break;
                case 3:
                    deactivateAccount();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void showUserProfile() {
        System.out.println(user.toString());
    }

    private void changePassword() {
        System.out.println("Changing password!");
        System.out.print("Enter old password: ");
        String oldPassword = scanner.nextLine();
        if (oldPassword.equals(user.getPassword())) {
            System.out.print("Enter new password: ");
            user.setPassword(scanner.nextLine());
            userService.update(user);
            System.out.println("Password updated!");
        } else {
            System.out.println("Wrong password!");
        }
    }

    private void deactivateAccount() {
        user.setActive(false);
        userService.update(user);
    }
}

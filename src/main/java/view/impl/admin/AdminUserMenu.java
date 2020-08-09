package view.impl.admin;

import model.User;
import model.UserRole;
import service.UserService;
import service.impl.UserServiceImpl;
import util.ScannerUtil;
import view.Menu;

import java.util.List;
import java.util.Scanner;

public class AdminUserMenu implements Menu {

    private final UserService userService = new UserServiceImpl();
    private final String[] items = {
            "1. Show users",
            "2. Block user",
            "3. Unblock user",
            "4. Change user role",
            "5. Change user password",
            "0. Exit"};
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        while (true) {
            showItems(items);
            System.out.println("-------------");
            System.out.print("Enter your choice: ");

            int choice = ScannerUtil.getInt();

            switch (choice) {
                case 1:
                    showUsers();
                    break;
                case 2:
                    blockUser();
                    break;
                case 3:
                    unBlockUser();
                    break;
                case 4:
                    changeUserRole();
                    break;
                case 5:
                    changeUserPassword();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void changeUserPassword() {
        System.out.println("Changing password!");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        User user = userService.findByName(username);
        if (user != null) {
            System.out.print("Enter new password: ");
            user.setPassword(scanner.nextLine());
            userService.update(user);
            System.out.println("User password updated!");
        } else {
            System.out.println("User with username " + username + " does not exist!");
        }
    }

    private void changeUserRole() {
        System.out.print("Enter username to change role: ");
        String username = scanner.nextLine();
        User user = userService.findByName(username);
        if (user != null) {
            int roleId;
            do {
                System.out.println("Role list: "
                        + "\n 1:" + UserRole.ADMIN.toString()
                        + "\n 2:" + UserRole.CUSTOMER.toString()
                );

                System.out.print("Enter your choice: ");
                roleId = ScannerUtil.getInt();
                switch (roleId) {
                    case 1:
                        user.setRole(UserRole.ADMIN);
                        break;
                    case 2:
                        user.setRole(UserRole.CUSTOMER);
                        break;
                }
                if (roleId != 1 && roleId != 2) {
                    System.out.println("Incorrect choice! Try again.");
                }
            } while (roleId != 1 && roleId != 2);
            userService.update(user);
            System.out.println("User status changed!");
        } else {
            System.out.println("User not found!");
        }
    }

    private void showUsers() {
        System.out.println("List of users: ");
        List<User> userList = userService.findAll();
        if (userList.isEmpty()) {
            System.out.println("User list is empty!");
        } else {
            userList.forEach(System.out::println);
        }
    }

    private void blockUser() {
        System.out.print("Enter username to block: ");
        String username = scanner.nextLine();
        User user = userService.findByName(username);
        if (user != null) {
            userService.blockUser(user.getId());
            System.out.println("User is blocked!");
        } else {
            System.out.println("User not found!");
        }
    }

    private void unBlockUser() {
        System.out.print("Enter username to unblock: ");
        String username = scanner.nextLine();
        User user = userService.findByName(username);
        if (user != null) {
            userService.unBlockUser(user.getId());
            System.out.println("User is unblocked!");
        } else {
            System.out.println("User not found!");
        }
    }
}

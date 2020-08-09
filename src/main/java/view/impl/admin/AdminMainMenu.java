package view.impl.admin;

import model.User;
import util.ScannerUtil;
import view.Menu;

public class AdminMainMenu implements Menu {

    private final String[] items = {
            "1. Show my profile",
            "2. User menu",
            "3. Order menu",
            "4. Product menu",
            "0. Exit"};
    private User user;

    public AdminMainMenu(User user) {
        this.user = user;
    }

    private void showUserProfile() {
        System.out.println(user.toString());
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
                    new AdminUserMenu().show();
                    break;
                case 3:
                    new AdminOrderMenu().show();
                    break;
                case 4:
                    new AdminProductMenu().show();
                    break;
                case 0:
                    return;
            }
        }
    }
}

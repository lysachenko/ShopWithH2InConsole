package view.impl.user;


import model.User;
import util.ScannerUtil;
import view.Menu;

public class UserMainMenu implements Menu {

    private String[] items = {
            "1. Profile menu",
            "2. Product menu",
            "3. Order menu",
            "0. Exit"};
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

            int choice = ScannerUtil.getInt();

            switch (choice) {
                case 1:
                    new UserProfileMenu(user).show();
                    break;
                case 2:
                    new UserProductMenu(user).show();
                    break;
                case 3:
                    new UserOrderMenu(user).show();
                    break;
                case 0:
                    return;
            }
        }
    }
}

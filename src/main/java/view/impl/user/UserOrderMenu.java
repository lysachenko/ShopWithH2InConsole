package view.impl.user;

import model.OrderStatus;
import model.User;
import service.OrderService;
import service.OrderServiceImpl;
import view.Menu;

import java.util.Scanner;

public class UserOrderMenu implements Menu {

    private OrderService orderService = new OrderServiceImpl();
    private String[] items = {
            "1.Show my orders",
            "2.Reject order",
            "0.Exit"};
    private Scanner scanner = new Scanner(System.in);
    private User user;

    public UserOrderMenu(User user) {
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
                    showMyOrders();
                    break;
                case "2":
                    rejectOrder();
                    break;
                case "0":
                    return;
                //break;
            }
        }
    }

    /*@Override
    public void exit() {
        return;
    }*/

    private void rejectOrder() {
        orderService.changeOrderStatus(OrderStatus.REJECTED.toString());
    }

    private void showMyOrders() {
        System.out.println("My orders:");
        orderService.findOrdersByUser(user).forEach(System.out::println);
    }
}

package view.impl.user;

import model.Order;
import model.OrderStatus;
import model.User;
import service.OrderService;
import service.OrderServiceImpl;
import util.ScannerUtil;
import view.Menu;

public class UserOrderMenu implements Menu {

    private OrderService orderService = new OrderServiceImpl();
    private String[] items = {
            "1. Show my orders",
            "2. Reject order",
            "0. Exit"};
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

            int choice = ScannerUtil.getInt();

            switch (choice) {
                case 1:
                    showMyOrders();
                    break;
                case 2:
                    rejectOrder();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void rejectOrder() {
        long orderId;
        Order order;
        do {
            showMyOrders();
            System.out.println("Enter order ID to change status: ");
            orderId = ScannerUtil.getLong();
            order = orderService.findById(orderId);
            if (order == null) {
                System.out.println("Incorrect order! Try again.");
            }
        } while (order == null);
        orderService.changeOrderStatus(order, OrderStatus.REJECTED.toString());
    }

    private void showMyOrders() {
        System.out.println("My orders:");
        orderService.findOrdersByUser(user).forEach(System.out::println);
    }
}

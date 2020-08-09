package view.impl.admin;

import model.Order;
import model.OrderStatus;
import service.OrderService;
import service.impl.OrderServiceImpl;
import util.ScannerUtil;
import view.Menu;

import java.util.List;

public class AdminOrderMenu implements Menu {

    private final OrderService orderService = new OrderServiceImpl();
    private final String[] items = {
            "1. Show orders",
            "2. Change order status",
            "0. Exit"};

    @Override
    public void show() {
        while (true) {
            showItems(items);
            System.out.println("-------------");
            System.out.print("Enter your choice: ");

            int choice = ScannerUtil.getInt();

            switch (choice) {
                case 1:
                    showOrders();
                    break;
                case 2:
                    changeOrderStatus();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void showOrders() {
        System.out.println("Order list:");
        List<Order> orderList = orderService.findAll();
        if (orderList.isEmpty()) {
            System.out.println("Order list is empty!");
        } else {
            orderList.forEach(System.out::println);
        }
    }

    private void changeOrderStatus() {
        long orderId;
        int statusId;
        Order order;

        System.out.println("Change order status!");
        do {
            System.out.print("Enter order ID: ");
            orderId = ScannerUtil.getLong();
            order = orderService.findById(orderId);
            if (order == null) {
                System.out.println("Incorrect order id! Try again.");
            }
        } while (order == null);

        String status = null;
        do {
            System.out.println("Order status list: "
                    + "\n 1:" + OrderStatus.PRE_CHECK_OUT.toString()
                    + "\n 2:" + OrderStatus.CHECKED_OUT.toString()
                    + "\n 3:" + OrderStatus.SENT.toString()
                    + "\n 4:" + OrderStatus.DELIVERED.toString()
                    + "\n 5:" + OrderStatus.DONE.toString()
                    + "\n 6:" + OrderStatus.REJECTED.toString()
            );

            System.out.print("Enter your choice: ");
            statusId = ScannerUtil.getInt();
            switch (statusId) {
                case 1:
                    status = OrderStatus.PRE_CHECK_OUT.toString();
                    break;
                case 2:
                    status = OrderStatus.CHECKED_OUT.toString();
                    break;
                case 3:
                    status = OrderStatus.SENT.toString();
                    break;
                case 4:
                    status = OrderStatus.DELIVERED.toString();
                    break;
                case 5:
                    status = OrderStatus.DONE.toString();
                    break;
                case 6:
                    status = OrderStatus.REJECTED.toString();
                    break;
            }
            if (status == null) {
                System.out.println("Incorrect choice! Try again.");
            }
        } while (status == null);
        orderService.changeOrderStatus(order, status);
        System.out.println("Order status changed!");
    }
}

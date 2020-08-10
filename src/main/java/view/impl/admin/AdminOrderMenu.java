package view.impl.admin;

import model.Order;
import model.OrderStatus;
import model.Purchase;
import service.OrderService;
import service.PurchaseService;
import service.impl.OrderServiceImpl;
import service.impl.PurchaseServiceImpl;
import util.ScannerUtil;
import view.Menu;

import java.util.List;

public class AdminOrderMenu implements Menu {

    private final OrderService orderService = new OrderServiceImpl();
    private final PurchaseService purchaseService = new PurchaseServiceImpl();
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
        System.out.println(order);

        String status = null;

        System.out.println("Order status list: "
                + "\n 1:" + OrderStatus.CHECKED_OUT.toString()
                + "\n 2:" + OrderStatus.SENT.toString()
                + "\n 3:" + OrderStatus.DELIVERED.toString()
                + "\n 4:" + OrderStatus.DONE.toString()
                + "\n 5:" + OrderStatus.REJECTED.toString()
        );

        System.out.print("Enter your choice: ");
        statusId = ScannerUtil.getInt();
        switch (statusId) {
            case 1:
                if (order.getStatus().equals(OrderStatus.CHECKED_OUT)) {
                    System.out.println("Order is already " + OrderStatus.CHECKED_OUT.toString());
                    break;
                }
                if (order.getStatus().equals(OrderStatus.SENT)
                        || order.getStatus().equals(OrderStatus.DELIVERED)
                        || order.getStatus().equals(OrderStatus.DONE)
                ) {
                    System.out.println("You cannot change status to previous stage!");
                    break;
                }
                status = OrderStatus.CHECKED_OUT.toString();
                break;
            case 2:
                if (order.getStatus().equals(OrderStatus.SENT)) {
                    System.out.println("Order is already " + OrderStatus.SENT.toString());
                    break;
                }
                if (order.getStatus().equals(OrderStatus.DELIVERED)
                        || order.getStatus().equals(OrderStatus.DONE)
                ) {
                    System.out.println("You cannot change status to previous stage!");
                    break;
                }
                status = OrderStatus.SENT.toString();
                createPurchaseToUser(order);
                break;
            case 3:
                if (order.getStatus().equals(OrderStatus.DELIVERED)) {
                    System.out.println("Order is already " + OrderStatus.DELIVERED.toString());
                    createPurchaseToUser(order);
                    break;
                }
                if (order.getStatus().equals(OrderStatus.DONE)) {
                    System.out.println("You cannot change status to previous stage!");
                    break;
                }
                status = OrderStatus.DELIVERED.toString();
                break;
            case 4:
                if (order.getStatus().equals(OrderStatus.DONE)) {
                    System.out.println("Order is already " + OrderStatus.DONE.toString());
                    createPurchaseToUser(order);
                    break;
                }
                status = OrderStatus.DONE.toString();
                break;
            case 5:
                if (order.getStatus().equals(OrderStatus.REJECTED)) {
                    System.out.println("Order is already " + OrderStatus.REJECTED.toString());
                    createPurchaseToUser(order);
                    break;
                }
                status = OrderStatus.REJECTED.toString();
                break;
        }
        if (status == null) {
            System.out.println("Incorrect choice! Try again.");
        } else {
            orderService.changeOrderStatus(order, status);
            System.out.println("Order status changed!");
        }
    }

    private void createPurchaseToUser(Order order) {
        Purchase purchase = new Purchase();
        purchase.setUser(order.getUser());
        purchase.setPositionMap(order.getPositionMap());
        purchaseService.save(purchase);
    }
}

package view.impl.user;

import model.Order;
import model.OrderStatus;
import model.User;
import service.OrderService;
import service.ProductService;
import service.impl.OrderServiceImpl;
import service.impl.ProductServiceImpl;
import util.ScannerUtil;
import view.Menu;

import java.util.List;

public class UserOrderMenu implements Menu {

    private final OrderService orderService = new OrderServiceImpl();
    private final ProductService productService = new ProductServiceImpl();
    private final String[] items = {
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

        showMyOrders();
        System.out.print("Enter order ID to change status: ");
        orderId = ScannerUtil.getLong();
        order = orderService.findById(orderId);

        if (order == null) {
            System.out.println("Incorrect order! Try again.");
            return;
        }

        if (order.getStatus().equals(OrderStatus.PRE_CHECK_OUT)
                || order.getStatus().equals(OrderStatus.CHECKED_OUT)
        ) {
            //Увеличение при отмене заказа
            order.getPositionMap().forEach((product, amount) -> {
                        product.setAmount(product.getAmount() + amount);
                        productService.update(product);
                    }
            );
            orderService.changeOrderStatus(order, OrderStatus.REJECTED.toString());
            System.out.println("Order rejected!");
        } else if (order.getStatus().equals(OrderStatus.REJECTED)) {
            System.out.println("Order already rejected!");
        } else {
            System.out.println("You cannot reject your order at this stage!");
            System.out.println("Please call the shop administrator to resolve the issue.");
        }
    }

    private void showMyOrders() {
        System.out.println("My orders:");
        List<Order> orderList = orderService.findOrdersByUser(user);
        if (orderList.isEmpty()) {
            System.out.println("Order list is empty!");
        } else {
            orderService.findOrdersByUser(user).forEach(System.out::println);
        }
    }
}

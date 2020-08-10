package view.impl.user;

import model.Purchase;
import model.User;
import service.ProductService;
import service.PurchaseService;
import service.ShoppingCartService;
import service.impl.ProductServiceImpl;
import service.impl.PurchaseServiceImpl;
import service.impl.ShoppingCartServiceImpl;
import util.ScannerUtil;
import view.Menu;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserPurchaseMenu implements Menu {

    private final String regEx1 = "^[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}$";
    private final String regEx2 = "^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$";
    private final String regEx3 = "^[0-9]{16}$";

    private final PurchaseService purchaseService = new PurchaseServiceImpl();
    private final ProductService productService = new ProductServiceImpl();
    private final ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
    private final String[] items = {
            "1. Show my purchases",
            //"2. Add card for paying",
            "2. Pay for purchase",
            "0. Exit"};
    private final Scanner scanner = new Scanner(System.in);
    private User user;

    public UserPurchaseMenu(User user) {
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
                    showPurchasesByUser();
                    break;
                /*case 2:
                    addCardForPaying();
                    break;*/
                case 2:
                    payForPurchase();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void payForPurchase() {
        System.out.println("Unpaid purchases:");
        List<Purchase> purchases = getUnpaidPurchases();
        if (purchases.isEmpty()) {
            System.out.println("You paid for all purchases!");
            return;
        }
        purchases.forEach(System.out::println);

        System.out.print("Enter purchase id what you want to pay: ");
        long purchaseId = ScannerUtil.getLong();

        Purchase purchase;
        if (!purchases.contains(purchaseService.findById(purchaseId))) {
            System.out.println("Purchase with id: " + purchaseId + " does not exist in list! Try again.");
            return;
        }
        purchase = purchases.get(purchases.indexOf(purchaseService.findById(purchaseId)));

        if (purchase.getCardNumber() == null) {
            String cardNumber = enterCardNumber();

            if (cardNumber != null) {
                purchase.setCardNumber(cardNumber);
                purchaseService.update(purchase);
                System.out.println("Card added successfully!");
            } else {
                System.out.println("Card did not added!");
            }
        }

        System.out.println("Confirm sum of purchase.");
        if (confirmPurchaseTotalSum(purchase)) {
            System.out.println("You paid purchase successfully!");
        } else {
            System.out.println("You exit because entered the wrong sum!");
        }
    }

    private boolean confirmPurchaseTotalSum(Purchase purchase) {
        System.out.print("Total sum: " + purchase.getTotalSum() + ". If you agree, write the sum (-1 to exit): ");
        float sum = ScannerUtil.getFloat();

        if (sum == purchase.getTotalSum()) {
            purchase.setPayed(true);
            purchaseService.update(purchase);
            return true;
        } else {
            return false;
        }
    }

    private List<Purchase> getUnpaidPurchases() {
        return purchaseService.findPurchasesByUser(user)
                .stream()
                .filter(purchase -> !purchase.isPayed())
                .collect(Collectors.toList());
    }

    private String enterCardNumber() {
        String cardNumber;
        do {
            System.out.print("Enter card number or \"0\" to exit: ");
            cardNumber = scanner.next();
            if (cardNumber.equals("0")) {
                return null;
            }
            if (!cardNumber.matches(regEx1) && !cardNumber.matches(regEx2) && !cardNumber.matches(regEx3)) {
                System.out.println("Invalid card number! Try again.");
            }
        } while (!cardNumber.matches(regEx1) && !cardNumber.matches(regEx2) && !cardNumber.matches(regEx3));
        return cardNumber;
    }

    private void showPurchasesByUser() {
        System.out.println("Your purchases:");
        List<Purchase> purchases = purchaseService.findPurchasesByUser(user);
        if (purchases.isEmpty()) {
            System.out.println("You haven't made any purchases!");
        } else {
            purchases.forEach(System.out::println);
        }
    }
}

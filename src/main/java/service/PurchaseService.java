package service;

import model.Purchase;
import model.User;

import java.util.List;

public interface PurchaseService {

    void save(Purchase purchase);

    void update(Purchase purchase);

    void delete(Purchase purchase);

    List<Purchase> findAll();

    Purchase findById(long id);

    List<Purchase> findPurchasesByUser(User user);
}

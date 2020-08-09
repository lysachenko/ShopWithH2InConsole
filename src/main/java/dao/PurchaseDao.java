package dao;

import model.Purchase;
import model.User;

import java.util.List;

public interface PurchaseDao {

    void save(Purchase purchase);

    void update(Purchase purchase);

    void delete(Purchase purchase);

    List<Purchase> findAll();

    Purchase findById(long id);

    List<Purchase> findByUser(User user);
}

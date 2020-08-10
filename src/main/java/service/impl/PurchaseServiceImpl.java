package service.impl;

import dao.PurchaseDao;
import dao.impl.PurchaseDaoImpl;
import model.Purchase;
import model.User;
import service.PurchaseService;

import java.util.List;

public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseDao purchaseDao = new PurchaseDaoImpl();

    @Override
    public void save(Purchase purchase) {
        purchaseDao.save(purchase);
    }

    @Override
    public void update(Purchase purchase) {
        purchaseDao.update(purchase);
    }

    @Override
    public void delete(Purchase purchase) {
        purchaseDao.delete(purchase);
    }

    @Override
    public List<Purchase> findAll() {
        return purchaseDao.findAll();
    }

    @Override
    public Purchase findById(long id) {
        return purchaseDao.findById(id);
    }

    @Override
    public List<Purchase> findPurchasesByUser(User user) {
        return purchaseDao.findByUser(user);
    }
}

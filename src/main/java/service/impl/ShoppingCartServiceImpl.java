package service.impl;

import dao.ShoppingCartDao;
import dao.impl.ShoppingCartDaoImpl;
import model.Product;
import model.ShoppingCart;
import model.User;
import service.ShoppingCartService;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final Lock readLock = readWriteLock.readLock();

    private final ShoppingCartDao shoppingCartDao = new ShoppingCartDaoImpl();

    @Override
    public void addProduct(User user, Product product, Integer amount) {
        writeLock.lock();
        try {
            ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartByUser(user);
            shoppingCart.getPositionMap().put(product, amount);
            shoppingCartDao.update(shoppingCart);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void deleteProduct(User user, Product product) {
        writeLock.lock();
        try {
            ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartByUser(user);
            shoppingCart.getPositionMap().remove(product);
            shoppingCartDao.update(shoppingCart);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void updateProductAmount(User user, Product product, Integer amount) {
        writeLock.lock();
        try {
            ShoppingCart shoppingCart = shoppingCartDao.findShoppingCartByUser(user);
            shoppingCart.getPositionMap().replace(product, amount);
            shoppingCartDao.update(shoppingCart);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void save(ShoppingCart shoppingCart) {
        writeLock.lock();
        try {
            shoppingCartDao.save(shoppingCart);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        writeLock.lock();
        try {
            shoppingCartDao.update(shoppingCart);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void clearProductList(ShoppingCart shoppingCart) {
        writeLock.lock();
        try {
            shoppingCartDao.deleteProductList(shoppingCart);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ShoppingCart findShoppingCartByUser(User user) {
        readLock.lock();
        try {
            return shoppingCartDao.findShoppingCartByUser(user);
        } finally {
            readLock.unlock();
        }
    }
}

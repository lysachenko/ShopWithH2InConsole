package service;

import model.Product;
import model.ShoppingCart;
import model.User;

public interface ShoppingCartService {

    void addProduct(User user, Product product, Integer amount);

    void deleteProduct(User user, Product product);

    void updateProductAmount(User user, Product product, Integer amount);

    void save(ShoppingCart shoppingCart);

    void update(ShoppingCart shoppingCart);

    void clearProductList(ShoppingCart shoppingCart);

    ShoppingCart findShoppingCartByUser(User user);
}

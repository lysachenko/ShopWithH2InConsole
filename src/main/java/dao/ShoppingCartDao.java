package dao;

import model.ShoppingCart;
import model.User;

public interface ShoppingCartDao {

    void save(ShoppingCart shoppingCart);

    void update(ShoppingCart shoppingCart);

    void deleteProductList(ShoppingCart shoppingCart);

    ShoppingCart findShoppingCartByUser(User user);
}

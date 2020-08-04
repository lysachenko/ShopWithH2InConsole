package dao;

import model.ShoppingCart;
import model.User;

import java.util.List;

public interface ShoppingCartDao {

    void save(ShoppingCart shoppingCart);

    void update(ShoppingCart shoppingCart);

    void delete(ShoppingCart shoppingCart);

    List<ShoppingCart> findShoppingCartByUser(User user);
}

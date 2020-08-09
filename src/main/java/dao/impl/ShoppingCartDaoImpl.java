package dao.impl;

import dao.ProductDao;
import dao.ShoppingCartDao;
import model.Product;
import model.ShoppingCart;
import model.User;
import util.H2JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartDaoImpl implements ShoppingCartDao {

    private final ProductDao productDao = new ProductDaoImpl();

    private static final String INSERT_PRODUCT_TO_CART =
            "INSERT INTO cart_product_list (user_cart_id, product_id, amount) VALUES (?, ?, ?);";

    private static final String GET_USER_CART =
            "select user_cart_id from SHOPPING_CARTS where user_cart_id = ?;";

    private static final String GET_CART_PRODUCT_LIST =
            "select product_id, amount from cart_product_list where user_cart_id = ?;";

    private static final String DELETE_CART_PRODUCT_LIST_BY_USER_ID =
            "delete from cart_product_list where user_cart_id = ?;";

    @Override
    public void save(ShoppingCart shoppingCart) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_TO_CART)
        ) {
            shoppingCart.getPositionMap()
                    .forEach((product, amount) -> {
                        try {
                            preparedStatement.setLong(1, shoppingCart.getUser().getId());
                            preparedStatement.setLong(2, product.getId());
                            preparedStatement.setLong(3, amount);
                            preparedStatement.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        deleteCartProductList(shoppingCart);
        save(shoppingCart);
    }

    @Override
    public void deleteCartProductList(ShoppingCart shoppingCart) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CART_PRODUCT_LIST_BY_USER_ID)
        ) {
            preparedStatement.setLong(1, shoppingCart.getUser().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ShoppingCart findShoppingCartByUser(User user) {
        ShoppingCart shoppingCart = null;
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_CART)
        ) {
            preparedStatement.setLong(1, user.getId());
            ResultSet rs1 = preparedStatement.executeQuery();

            while (rs1.next()) {
                shoppingCart = new ShoppingCart(user);
                shoppingCart.setPositionMap(getPositionMapFromDB(shoppingCart));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingCart;
    }

    private Map<Product, Integer> getPositionMapFromDB(ShoppingCart shoppingCart) {
        Map<Product, Integer> positionMap = null;

        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CART_PRODUCT_LIST)
        ) {
            preparedStatement.setLong(1, shoppingCart.getUser().getId());
            ResultSet rs = preparedStatement.executeQuery();

            positionMap = new HashMap<>();
            while (rs.next()) {
                positionMap.put(productDao.findById(rs.getLong("product_id")), rs.getInt("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positionMap;
    }
}

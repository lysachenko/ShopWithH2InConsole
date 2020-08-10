package dao.impl;

import dao.ProductDao;
import dao.PurchaseDao;
import dao.UserDao;
import model.Product;
import model.Purchase;
import model.User;
import util.H2JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseDaoImpl implements PurchaseDao {

    private final UserDao userDao = new UserDaoImpl();
    private final ProductDao productDao = new ProductDaoImpl();

    private static final String INSERT_PURCHASE =
            "INSERT INTO PURCHASES (USER_ID, CARD_NUMBER, TOTAL_SUM, IS_PAYED) VALUES (?, ?, ?, ?);";
    private static final String INSERT_PURCHASE_PRODUCT_LIST =
            "INSERT INTO PURCHASE_PRODUCT_LIST (PURCHASE_ID, PRODUCT_ID, AMOUNT) VALUES (?, ?, ?);";

    private static final String GET_ALL_PURCHASE =
            "select id, user_id, CARD_NUMBER, TOTAL_SUM, IS_PAYED from PURCHASES;";
    private static final String GET_PURCHASE_BY_ID =
            "select id, user_id, CARD_NUMBER, TOTAL_SUM, IS_PAYED from PURCHASES where id = ?;";
    private static final String GET_PURCHASE_BY_USERNAME =
            "select id, user_id, CARD_NUMBER, TOTAL_SUM, IS_PAYED from PURCHASES where user_id = ?;";
    private static final String GET_PRODUCT_LIST_BY_PURCHASE_ID =
            "select product_id, amount from PURCHASE_PRODUCT_LIST where PURCHASE_ID = ?;";

    private static final String UPDATE_PURCHASE_BY_ID =
            "update PURCHASES set USER_ID = ?, CARD_NUMBER = ?, TOTAL_SUM = ?, IS_PAYED = ? where id = ?;";

    private static final String DELETE_PURCHASE_BY_ID =
            "delete from PURCHASES where ID = ?;";
    private static final String DELETE_PRODUCT_LIST_BY_PURCHASE_ID =
            "delete from PURCHASE_PRODUCT_LIST where PURCHASE_ID = ?;";

    @Override
    public void save(Purchase purchase) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PURCHASE, new String[]{"id"})
        ) {
            preparedStatement.setLong(1, purchase.getUser().getId());
            preparedStatement.setString(2, purchase.getCardNumber());
            preparedStatement.setFloat(3, purchase.getTotalSum());
            preparedStatement.setBoolean(4, purchase.isPayed());
            preparedStatement.executeUpdate();

            ResultSet gk = preparedStatement.getGeneratedKeys();
            if (gk.next()) {
                purchase.setId(gk.getLong("id"));
            }
            insertProductListToDB(purchase);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Purchase purchase) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PURCHASE_BY_ID)
        ) {
            preparedStatement.setLong(1, purchase.getUser().getId());
            preparedStatement.setString(2, purchase.getCardNumber());
            preparedStatement.setFloat(3, purchase.getTotalSum());
            preparedStatement.setBoolean(4, purchase.isPayed());
            preparedStatement.setLong(5, purchase.getId());

            deleteProductListFromDB(purchase);
            insertProductListToDB(purchase);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Purchase purchase) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PURCHASE_BY_ID)
        ) {
            deleteProductListFromDB(purchase);
            preparedStatement.setLong(1, purchase.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Purchase> findAll() {
        List<Purchase> purchases = new ArrayList<>();
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PURCHASE)
        ) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Purchase purchase = new Purchase();
                purchase.setId(rs.getLong("id"));
                purchase.setUser(userDao.findById(rs.getLong("user_id")));
                purchase.setCardNumber(rs.getString("card_number"));
                purchase.setTotalSum(rs.getFloat("total_sum"));
                purchase.setPayed(rs.getBoolean("is_payed"));
                purchase.setPositionMap(getPositionMapFromDB(purchase));

                purchases.add(purchase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchases;
    }

    @Override
    public Purchase findById(long id) {
        Purchase purchase = null;
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PURCHASE_BY_ID)
        ) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                purchase = new Purchase();
                purchase.setId(rs.getLong("id"));
                purchase.setUser(userDao.findById(rs.getLong("user_id")));
                purchase.setCardNumber(rs.getString("card_number"));
                purchase.setTotalSum(rs.getFloat("total_sum"));
                purchase.setPayed(rs.getBoolean("is_payed"));
                purchase.setPositionMap(getPositionMapFromDB(purchase));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchase;
    }

    @Override
    public List<Purchase> findByUser(User user) {
        List<Purchase> purchases = new ArrayList<>();
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PURCHASE_BY_USERNAME)
        ) {
            preparedStatement.setLong(1, user.getId());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Purchase purchase = new Purchase();
                purchase.setId(rs.getLong("id"));
                purchase.setUser(userDao.findById(rs.getLong("user_id")));
                purchase.setCardNumber(rs.getString("card_number"));
                purchase.setTotalSum(rs.getFloat("total_sum"));
                purchase.setPayed(rs.getBoolean("is_payed"));
                purchase.setPositionMap(getPositionMapFromDB(purchase));

                purchases.add(purchase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchases;
    }

    private Map<Product, Integer> getPositionMapFromDB(Purchase purchase) {
        Map<Product, Integer> positionMap = new HashMap<>();
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_LIST_BY_PURCHASE_ID)
        ) {
            preparedStatement.setLong(1, purchase.getId());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                positionMap.put(productDao.findById(rs.getLong("product_id")), rs.getInt("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positionMap;
    }

    private void insertProductListToDB(Purchase purchase) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PURCHASE_PRODUCT_LIST)
        ) {
            purchase.getPositionMap()
                    .forEach((product, amount) -> {
                        try {
                            preparedStatement.setLong(1, purchase.getId());
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

    private void deleteProductListFromDB(Purchase purchase) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_LIST_BY_PURCHASE_ID)
        ) {
            preparedStatement.setLong(1, purchase.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

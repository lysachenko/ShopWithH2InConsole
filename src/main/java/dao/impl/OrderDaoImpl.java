package dao.impl;

import dao.OrderDao;
import dao.ProductDao;
import dao.UserDao;
import model.Order;
import model.OrderStatus;
import model.Product;
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

public class OrderDaoImpl implements OrderDao {

    private final UserDao userDao = new UserDaoImpl();
    private final ProductDao productDao = new ProductDaoImpl();

    private static final String INSERT_ORDER =
            "INSERT INTO orders (user_id, order_status) VALUES (?, ?);";
    private static final String INSERT_PRODUCT_LIST =
            "INSERT INTO order_product_list (order_id, product_id, amount) VALUES (?, ?, ?);";

    private static final String GET_ALL_ORDERS =
            "select id, user_id, order_status from orders;";
    private static final String GET_ORDER_BY_ID =
            "select id, user_id, order_status from orders where id = ?;";
    private static final String GET_ORDERS_BY_USERNAME =
            "select id, user_id, order_status from orders where user_id = ?;";
    private static final String GET_PRODUCT_LIST_BY_ORDER_ID =
            "select product_id, amount from order_product_list where order_id = ?;";

    private static final String DELETE_ORDER_BY_ID =
            "delete from orders where id = ?;";
    private static final String DELETE_PRODUCT_LIST_BY_ORDER_ID =
            "delete from order_product_list where order_id = ?;";

    private static final String UPDATE_ORDER_BY_ID =
            "update orders set user_id = ?, order_status = ? where id = ?;";

    @Override
    public void save(Order order) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER, new String[]{"id"})
        ) {
            preparedStatement.setLong(1, order.getUser().getId());
            preparedStatement.setString(2, String.valueOf(order.getStatus()));
            preparedStatement.executeUpdate();

            ResultSet gk = preparedStatement.getGeneratedKeys();
            if (gk.next()) {
                order.setId(gk.getLong("id"));
            }
            insertProductListToDB(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Order order) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_BY_ID)
        ) {
            preparedStatement.setLong(1, order.getUser().getId());
            preparedStatement.setString(2, String.valueOf(order.getStatus()));
            preparedStatement.setLong(3, order.getId());

            deleteProductListFromDB(order);
            insertProductListToDB(order);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Order order) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_BY_ID)
        ) {
            deleteProductListFromDB(order);
            preparedStatement.setLong(1, order.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order findById(long id) {
        Order order = null;
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID)
        ) {
            preparedStatement.setLong(1, id);
            ResultSet rs1 = preparedStatement.executeQuery();

            while (rs1.next()) {
                order = new Order();
                order.setId(rs1.getLong("id"));
                order.setUser(userDao.findById(rs1.getLong("user_id")));
                order.setStatus(OrderStatus.valueOf(rs1.getString("order_status")));
                order.setPositionMap(getPositionMapFromDB(order));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Order> findOrdersByUser(User user) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_BY_USERNAME)
        ) {
            preparedStatement.setLong(1, user.getId());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setUser(userDao.findById(rs.getLong("user_id")));
                order.setStatus(OrderStatus.valueOf(rs.getString("order_status")));
                order.setPositionMap(getPositionMapFromDB(order));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS)
        ) {
            ResultSet rs1 = preparedStatement.executeQuery();

            while (rs1.next()) {
                Order order = new Order();
                order.setId(rs1.getLong("id"));
                order.setUser(userDao.findById(rs1.getLong("user_id")));
                order.setStatus(OrderStatus.valueOf(rs1.getString("order_status")));
                order.setPositionMap(getPositionMapFromDB(order));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private void insertProductListToDB(Order order) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_LIST)
        ) {
            order.getPositionMap()
                    .forEach((product, amount) -> {
                        try {
                            preparedStatement.setLong(1, order.getId());
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

    private Map<Product, Integer> getPositionMapFromDB(
            Order order
    ) {
        Map<Product, Integer> positionMap = new HashMap<>();
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_LIST_BY_ORDER_ID)
        ) {
            preparedStatement.setLong(1, order.getId());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                positionMap.put(productDao.findById(rs.getLong("product_id")),
                        rs.getInt("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positionMap;
    }

    private void deleteProductListFromDB(Order order) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_LIST_BY_ORDER_ID)
        ) {
            preparedStatement.setLong(1, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
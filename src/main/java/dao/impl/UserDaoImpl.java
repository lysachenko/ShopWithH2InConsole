package dao.impl;

import dao.UserDao;
import model.User;
import model.UserRole;
import util.H2JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final String INSERT_USER = "INSERT INTO users" +
            "  (username, password, role, is_active) VALUES " +
            " (?, ?, ?, ?);";
    private static final String INSERT_USER_SOPPING_CART =
            "INSERT INTO shopping_carts (user_cart_id) VALUES (?);";

    private static final String GET_ALL_USERS = "select id, username, password, role, is_active from users;";
    private static final String GET_USER_BY_ID =
            "select id, username, password, role, is_active from users where id = ?;";
    private static final String GET_USER_BY_USERNAME =
            "select id, username, password, role, is_active from users where username = ?;";

    private static final String UPDATE_USER_BY_ID =
            "update users set username = ?, password = ?, role = ?, is_active = ? where id = ?;";

    private static final String DELETE_USER_BY_ID = "delete from users where id = ?;";
    private static final String DELETE_USER_SHOPPING_CART =
            "delete from shopping_carts where user_cart_id = ?;";

    @Override
    public void save(User user) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, new String[]{"id"});
             PreparedStatement preparedStatement2 = connection.prepareStatement(INSERT_USER_SOPPING_CART)
        ) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, String.valueOf(user.getRole()));
            preparedStatement.setBoolean(4, true);
            preparedStatement.execute();

            ResultSet gk = preparedStatement.getGeneratedKeys();
            if (gk.next()) {
                preparedStatement2.setLong(1, gk.getLong("id"));
                preparedStatement2.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_BY_ID)
        ) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, String.valueOf(user.getRole()));
            preparedStatement.setBoolean(4, user.isActive());
            preparedStatement.setLong(5, user.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SHOPPING_CART);
             PreparedStatement preparedStatement2 = connection.prepareStatement(DELETE_USER_BY_ID)
        ) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement2.execute();

            preparedStatement2.setLong(1, user.getId());
            preparedStatement2.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findByName(String username) {
        User user = null;
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_USERNAME)
        ) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));
                user.setActive(rs.getBoolean("is_active"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findById(long id) {
        User user = null;
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)
        ) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));
                user.setActive(rs.getBoolean("is_active"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = H2JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS)
        ) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));
                user.setActive(rs.getBoolean("is_active"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
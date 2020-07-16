package dao.impl;

import dao.UserDao;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final String INSERT_USERS_SQL = "INSERT INTO users" +
            "  (id, username, password, role, is_active) VALUES " +
            " (?, ?, ?, ?, ?);";

    private static final String GETBYID = "select id,name,email,country,password from users where id =?";
    private static final String GETALL = "select id,name,email,country,password from users";

    @Override
    public void save(User user) {
       /* System.out.println(INSERT_USERS_SQL);
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)
        ) {
            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "Roman");
            preparedStatement.setString(3, "roman@gmail.com");
            preparedStatement.setString(4, "UA");
            preparedStatement.setString(5, "secret");

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }*/
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User findByName(String name) {
        return null;
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}

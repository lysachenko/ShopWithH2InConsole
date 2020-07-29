package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserInserter {

    private static final String insertTestUsers =
            "insert into users select * from (\n" +
                    "select 1, 'admin', 'adminpass', 'ADMIN', true union\n" +
                    "select 2, 'user1', 'user1', 'CUSTOMER', true union\n" +
                    "select 3, 'user2', 'user2', 'CUSTOMER', false \n" +
                    ") x where not exists(select * from users);";

    public void insert() {
        insertTestValues();
    }

    private static void insertTestValues() {
        try (Connection connection = H2JDBCUtil.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.execute(insertTestUsers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

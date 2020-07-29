package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductInserter {

    private static final String insertTestProducts =
            "insert into products select * from (\n" +
                    "select 1, 'apple', 'apple desc', 15.0, 403 union\n" +
                    "select 2, 'apricot', 'apricot desc', 37.0, 238 union\n" +
                    "select 3, 'avocado', 'avocado desc', 43.0, 1200 union \n" +
                    "select 4, 'banana', 'banana desc', 18.5, 431 union \n" +
                    "select 5, 'kiwi', 'kiwi desc', 73.2, 321 union \n" +
                    "select 6, 'mango', 'mango desc', 102.7, 623 union \n" +
                    "select 7, 'orange', 'orange desc', 12.9, 543 union \n" +
                    "select 8, 'papaya', 'papaya desc', 132.2, 217 union \n" +
                    "select 9, 'peach', 'peach desc', 23.0, 84 union \n" +
                    "select 10, 'pineapple', 'pineapple desc', 54.0, 732 union \n" +
                    "select 11, 'plum', 'plum desc', 14.0, 1102 union \n" +
                    "select 12, 'lemon', 'lemon desc', 65.3, 712 \n" +
                    ") x where not exists(select * from products);";

    public void insert() {
        insertTestProducts();
    }

    private static void insertTestProducts() {
        try (Connection connection = H2JDBCUtil.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.execute(insertTestProducts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

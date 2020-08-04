package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderInserter {

    private final String insertTestOrders =
            "insert into ORDERS select * from (\n" +
                    "select 1, 2, 'PRE_CHECKOUT'union \n" +
                    "select 2, 2, 'CHECKOUT'union \n" +
                    "select 3, 3, 'PRE_CHECKOUT'\n" +
                    ") x where not exists(select * from ORDERS);";
    private final String insertTestProductList =
            "insert into ORDER_PRODUCT_LIST select * from (\n" +
                    "select 1, 2, 100 union\n" +
                    "select 1, 1, 50 union\n" +
                    "select 1, 3, 300 union\n" +
                    "select 2, 4, 70 union\n" +
                    "select 2, 5, 34 union\n" +
                    "select 3, 6, 47 union\n" +
                    "select 3, 7, 232 union\n" +
                    "select 3, 8, 12 union\n" +
                    "select 3, 10, 410\n" +
                    ") x where not exists(select * from ORDER_PRODUCT_LIST);";

    public void insert() {
        insertTestValues();
    }

    private void insertTestValues() {
        try (Connection connection = H2JDBCUtil.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.execute(insertTestOrders);
            statement.execute(insertTestProductList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

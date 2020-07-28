package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductInserter {

    private static final String insertApple =
            "merge into PRODUCTS(ID, NAME, DESCRIPTION, PRICE, AMOUNT) " +
                    "key(ID) values (1, 'apple', 'apple desc', 15.0, 403)";
    private static final String insertApricot =
            "merge into PRODUCTS(ID, NAME, DESCRIPTION, PRICE, AMOUNT) " +
                    "key(ID) values (2, 'apricot', 'apricot desc', 37.0, 238)";
    private static final String insertAvocado =
            "merge into PRODUCTS(ID, NAME, DESCRIPTION, PRICE, AMOUNT) " +
                    "key(ID) values (3, 'avocado', 'avocado desc', 43.0, 1200)";
    private static final String insertBanana =
            "merge into PRODUCTS(ID, NAME, DESCRIPTION, PRICE, AMOUNT) " +
                    "key(ID) values (4, 'banana', 'banana desc', 18.5, 431)";
    private static final String insertKiwi =
            "merge into PRODUCTS(ID, NAME, DESCRIPTION, PRICE, AMOUNT) " +
                    "key(ID) values (5, 'kiwi', 'kiwi desc', 73.2, 321)";
    private static final String insertMango =
            "merge into PRODUCTS(ID, NAME, DESCRIPTION, PRICE, AMOUNT) " +
                    "key(ID) values (6, 'mango', 'mango desc', 102.7, 623)";
    private static final String insertOrange =
            "merge into PRODUCTS(ID, NAME, DESCRIPTION, PRICE, AMOUNT) " +
                    "key(ID) values (7, 'orange', 'orange desc', 12.9, 543)";
    private static final String insertPapaya =
            "merge into PRODUCTS(ID, NAME, DESCRIPTION, PRICE, AMOUNT) " +
                    "key(ID) values (8, 'papaya', 'papaya desc', 132.2, 217)";
    private static final String insertPeach =
            "merge into PRODUCTS(ID, NAME, DESCRIPTION, PRICE, AMOUNT) " +
                    "key(ID) values (9, 'peach', 'peach desc', 23.0, 842)";
    private static final String insertPineapple =
            "merge into PRODUCTS(ID, NAME, DESCRIPTION, PRICE, AMOUNT) " +
                    "key(ID) values (10, 'pineapple', 'pineapple desc', 54.0, 732)";
    private static final String insertPlum =
            "merge into PRODUCTS(ID, NAME, DESCRIPTION, PRICE, AMOUNT) " +
                    "key(ID) values (11, 'plum', 'plum desc', 14.0, 1102)";
    private static final String insertLemon =
            "merge into PRODUCTS(ID, NAME, DESCRIPTION, PRICE, AMOUNT) " +
                    "key(ID) values (12, 'lemon', 'lemon desc', 65.3, 712)";

    public void insert() {
        insertTestProducts();
    }

    private static void insertTestProducts() {
        try (Connection connection = H2JDBCUtil.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.execute(insertApple);
            statement.execute(insertApricot);
            statement.execute(insertAvocado);
            statement.execute(insertBanana);
            statement.execute(insertKiwi);
            statement.execute(insertMango);
            statement.execute(insertOrange);
            statement.execute(insertPapaya);
            statement.execute(insertPeach);
            statement.execute(insertPineapple);
            statement.execute(insertPlum);
            statement.execute(insertLemon);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

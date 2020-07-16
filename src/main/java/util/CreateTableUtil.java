package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableUtil {

    private static final String createTableUsers =
            "create table if not exists users ("
                    + "id integer primary key,"
                    + " username varchar(20),"
                    + " password varchar(20),"
                    + " role varchar(20),"
                    + " is_active boolean);";

    private static final String createTableProduct =
            "create table if not exists products ("
                    + "id integer primary key,"
                    + " name varchar(20),"
                    + " description varchar(20),"
                    + " price float,"
                    + " amount float);";

    private static final String createTableOrder =
            "create table if not exists orders ("
                    + "id integer primary key,"
                    + " user_id integer,"
                    + " order_status varchar(20),"
                    + " foreign key (user_id) references users(id));";

    private static final String createTableProductList =
            "create table if not exists product_list ("
                    + "order_id integer,"
                    + " product_id integer,"
                    + " amount integer,"
                    + " foreign key (order_id) references orders(id),"
                    + " foreign key (product_id) references products(id),"
                    + " primary key (order_id, product_id));";

    public void run() {
        createTables();
    }

    private static void createTables() {
        System.out.println(createTableUsers);
        System.out.println(createTableProduct);
        System.out.println(createTableOrder);
        System.out.println(createTableProductList);
        try (Connection connection = H2JDBCUtil.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.execute(createTableUsers);
            statement.execute(createTableProduct);
            statement.execute(createTableOrder);
            statement.execute(createTableProductList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

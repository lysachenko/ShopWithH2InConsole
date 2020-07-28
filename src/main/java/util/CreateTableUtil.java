package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableUtil {

    private static final String createTableUsers =
            "create table if not exists users ("
                    + "id integer primary key auto_increment,"
                    + " username varchar(20),"
                    + " password varchar(20),"
                    + " role varchar(20),"
                    + " is_active boolean);";

    private static final String createTableProduct =
            "create table if not exists products ("
                    + "id integer primary key auto_increment,"
                    + " name varchar(20),"
                    + " description varchar(20),"
                    + " price float,"
                    + " amount float);";

    private static final String createTableOrder =
            "create table if not exists orders ("
                    + "id integer primary key auto_increment,"
                    + " user_id integer,"
                    + " order_status varchar(20),"
                    + " foreign key (user_id) references users(id) on delete cascade);";

    private static final String createTableProductList =
            "create table if not exists product_list ("
                    + "order_id integer,"
                    + " product_id integer,"
                    + " amount integer,"
                    + " foreign key (order_id) references orders(id) on delete cascade,"
                    + " foreign key (product_id) references products(id) on delete cascade,"
                    + " primary key (order_id, product_id));";

    private static final String insertAdminUser =
            "merge into users(ID, USERNAME, PASSWORD, ROLE, IS_ACTIVE)" +
                    "key(ID) values (1, 'admin', 'admin', 'ADMIN', true)";

    public void run() {
        create();
    }

    private static void create() {
        try (Connection connection = H2JDBCUtil.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.execute(createTableUsers);
            statement.execute(createTableProduct);
            statement.execute(createTableOrder);
            statement.execute(createTableProductList);
            statement.execute(insertAdminUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

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

    private static final String createTableOrderProductList =
            "create table if not exists order_product_list ("
                    + "order_id integer,"
                    + " product_id integer,"
                    + " amount integer,"
                    + " foreign key (order_id) references orders(id) on delete cascade,"
                    + " foreign key (product_id) references products(id) on delete cascade,"
                    + " primary key (order_id, product_id));";

    private static final String createTableShoppingCart =
            "create table if not exists shopping_carts ("
                    + " user_cart_id integer primary key,"
                    + " foreign key (user_cart_id) references users(id) on delete cascade);";

    private static final String createTableCartProductList =
            "create table if not exists cart_product_list ("
                    + "user_cart_id integer,"
                    + " product_id integer,"
                    + " amount integer,"
                    + " foreign key (user_cart_id) references shopping_carts(user_cart_id) on delete cascade,"
                    + " foreign key (product_id) references products(id) on delete cascade,"
                    + " primary key (user_cart_id, product_id));";

    private static final String createTablePurchase =
            "create table if not exists purchases ("
                    + " id integer primary key,"
                    + " user_id integer,"
                    + " card_number varchar(20),"
                    + " total_sum float,"
                    + " foreign key (user_id) references users(id) on delete cascade);";

    private static final String createTablePurchaseProductList =
            "create table if not exists purchase_product_list ("
                    + "purchase_id integer,"
                    + " product_id integer,"
                    + " amount integer,"
                    + " foreign key (purchase_id) references purchases(id) on delete cascade,"
                    + " foreign key (product_id) references products(id) on delete cascade,"
                    + " primary key (purchase_id, product_id));";

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
            statement.execute(createTableOrderProductList);
            statement.execute(createTableShoppingCart);
            statement.execute(createTableCartProductList);
            statement.execute(createTablePurchase);
            statement.execute(createTablePurchaseProductList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

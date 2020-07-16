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
                    + " isActive boolean);";

    private static final String createTableProduct =
            "create table if not exists products ("
                    + "id integer primary key,"
                    + " name varchar(20),"
                    + " price float,"
                    + " amount float);";

    private static final String createTableOrder =
            "create table orders ("
                    + "id integer primary key,"
                    + " user_id integer,"
                    + " order_status varchar(20));";

    //Запускать для проверки создания таблиц
    public static void main(String[] args) {
        createTables();
    }

    public static void createTables() {
        System.out.println(createTableUsers);
        System.out.println(createTableProduct);
        System.out.println(createTableOrder);
        try (Connection connection = H2JDBCUtil.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.execute(createTableUsers);
            statement.execute(createTableProduct);
            statement.execute(createTableOrder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

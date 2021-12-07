import util.CreateTableUtil;
import util.OrderInserter;
import util.ProductInserter;
import util.UserInserter;
import view.impl.LoginMenu;

public class Main {

    public static void main(String[] args) {
        new CreateTableUtil().run();
        new UserInserter().insert();
        new ProductInserter().insert();
        new OrderInserter().insert();
        new LoginMenu().show();
    }
}

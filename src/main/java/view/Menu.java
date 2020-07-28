package view;

public interface Menu {
    void show();

    default void showItems(String[] items) {
        for (String item : items) {
            System.out.println("-------------");
            System.out.println(item);
        }
    }
}

package model;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Purchase {

    private long id;
    private User user;
    private Map<Product, Integer> positionMap;
    private String cardNumber;
    private float totalSum;
    private boolean isPayed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Product, Integer> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<Product, Integer> positionMap) {
        this.positionMap = positionMap;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public void setTotalSum(float totalSum) {
        this.totalSum = totalSum;
    }

    public float getTotalSum() {
        return (float) positionMap.entrySet()
                .stream()
                .mapToDouble(productAmountEntry ->
                        productAmountEntry.getKey().getPrice() * productAmountEntry.getValue())
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return id == purchase.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "********************************************\n"
                + "Purchase: " +
                "id=" + id +
                "\nuser: id=" + user.getId() + ", username=" + user.getUsername() +
                "\npositions:" + positionMap.entrySet()
                .stream()
                .map(productIntegerEntry ->
                        "\n\tProduct: " + productIntegerEntry.getKey().toString()
                                + ", ordered quantity: " + productIntegerEntry.getValue().toString() + "\n")
                .collect(Collectors.joining()) +
                "\ncardNumber='" + cardNumber + '\'' +
                "\ntotalSum=" + totalSum +
                "\nisPayed=" + isPayed
                + "\n********************************************";
    }
}

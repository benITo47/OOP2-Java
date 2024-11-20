import java.util.*;

public class User {
    private String id;
    private String name;
    private UserType type;
    private List<LibraryItem> borrowedItems;
    private double balance;

    public enum UserType {
        STUDENT, TEACHER
    }

    public User(String id, String name, UserType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.borrowedItems = new ArrayList<>();
        this.balance = 0.0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserType getType() {
        return type;
    }

    public List<LibraryItem> getBorrowedItems() {
        return borrowedItems;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public void addBorrowedItem(LibraryItem item) {
        borrowedItems.add(item);
    }

    public void removeBorrowedItem(LibraryItem item) {
        borrowedItems.remove(item);
    }

    public void showUserBalance() {
            System.out.println(getName() + " balance: " + getBalance() + " PLN.");
    }
}

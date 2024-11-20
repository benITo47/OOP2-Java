import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Library {
    private final Map<String, LibraryItem> items;
    private final Map<String, User> users;

    /**
     * Constructor
     */
    public Library() {
        items = new HashMap<>();
        users = new HashMap<>();
        loadBooks();
        loadJournals();
        loadFilms();
    }


    /**
     * Method is responsible for loading Journals from CSV file
     */
    public void loadJournals() {

        String line;
        String separator = ";";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("jlist.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            br.readLine();
            int skipper = 0;
            while (skipper < 50 && (line = br.readLine()) != null) {
                skipper++;
            }
            int i = 0;
            while (i < 100) {
                line = br.readLine();
                String[] fields = line.split(separator);
                if (fields.length >= 7) {
                    String id = "J" + i;
                    String title = fields[0];
                    String eISSN = fields[3];
                    String publisher = fields[4];
                    String latestIssueDate = fields[6];

                    Journal journal = new Journal(id, title, eISSN, publisher, latestIssueDate);
                    items.put(id, journal);

                    i++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Method is responsible for loading Books from CSV file
     */
    public void loadBooks() {

        String line;
        String separator = ";";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("books.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            br.readLine();

            int skipper = 0;
            while (skipper < 50 && (line = br.readLine()) != null) {
                skipper++;
            }
            int i = 0;
            while (i < 100) {
                line = br.readLine();
                String[] fields = line.split(separator);
                if (fields.length >= 5) {
                    String id = "B" + i;
                    String title = fields[0];
                    String author = fields[1];
                    String genre = fields[2];
                    String publisher = fields[4];

                    Book book = new Book(id, title, author, genre, publisher);
                    items.put(id, book);

                    i++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Method is responsible for loading Films from CSV file
     */
    public void loadFilms() {

        String line;
        String separator = ";";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("movies.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            br.readLine();

            int skipper = 0;
            while (skipper < 50 && (line = br.readLine()) != null) {
                skipper++;
            }
            int i = 0;
            while (i < 100) {
                line = br.readLine();
                String[] fields = line.split(separator);
                if (fields.length >= 9) {
                    String id = "F" + i;
                    String title = fields[1];
                    String director = fields[4];
                    String runTime = fields[7];
                    String rating = fields[8];

                    Film film = new Film(id, title, director, Integer.parseInt(runTime), Double.parseDouble(rating));
                    items.put(id, film);

                    i++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Method for appending user to Library user base
     * @param user - User that is ought to be a client of library
     */
    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    /**
     * Method responsible for adding a single LibraryItem into the dataset;
     * @param item  LibrayItem -> Book/Journal/Film
     */
    public void addItem(LibraryItem item) {
        items.put(item.getId(), item);
    }

    /**
     * Method responsible for borrowing the book to a User;
     * Method attaches a book to user's 'account'
     * Method sets the items borrowDate to passed date, and sets item's isBorrowed flag to true;
     * @param itemId - id of the item that is being borrowed
     * @param userId - id of the user borrowing the item;
     * @param borrowDate - date of transaction
     */
    public void borrowItem(String itemId, String userId, LocalDate borrowDate) {
        LibraryItem item = items.get(itemId);
        User user = users.get(userId);
        if (item == null || user == null) {
            System.out.println("Item or User not found.");
            return;
        }
        if (item.isBorrowed()) {
            System.out.println("Item not available.");
            return;
        }
        if (user.getType() == User.UserType.STUDENT) {
            if (item instanceof Journal) {
                item.setBorrowPeriod(3);
            }
            if (item instanceof Film && user.getBorrowedItems().stream().anyMatch(i -> i instanceof Film)) {
                System.out.println("Student can borrow only one film.");
                return;
            }
            if (user.getBorrowedItems().size() >= 3) {
                System.out.println("Student can borrow a maximum of 3 items.");
                return;
            }
        }
        if (user.getType() == User.UserType.TEACHER) {
            if (item instanceof Journal) {
                item.setBorrowPeriod(7);
            }

        }

        item.setBorrowed(true);
        item.setBorrowedDate(borrowDate);
        user.addBorrowedItem(item);
        System.out.println(user.getName() + " borrowed " + item.getTitle());
    }

    /**
     * Method responsible for handling returns of items.
     * Method detaches book from user's 'account'
     * Method resets the borrowDate for item to null, and set's down isBorrowed flag to false;
     * Method calculates fine if item is OVERDUE, and adds the charge to users account;
     * @param itemId - Id of an Item that is being returned;
     * @param userId - Id of user that is returning the book;
     * @param returnDate -- date of transaction;
     */
    public void returnItem(String itemId, String userId, LocalDate returnDate) {
        LibraryItem item = items.get(itemId);
        User user = users.get(userId);

        if (item == null || user == null) {
            System.out.println("Item or User not found.");
            return;
        }

        if (!user.getBorrowedItems().contains(item)) {
            System.out.println("Item not borrowed by user.");
            return;
        }


        long daysOverdue = item.daysOverdue(returnDate);

        double fine = item.computeFine(returnDate);

        item.setBorrowed(false);
        item.setBorrowedDate(null);
        user.removeBorrowedItem(item);
        if (daysOverdue <= 0) {
            System.out.println("Item returned on time.");
        }

        if (daysOverdue > 0) {
            user.addBalance(-fine);
            System.out.println("Overdue: " + daysOverdue + " days. Fine: " + fine + " PLN.");
        }
    }



    public Map<String, LibraryItem> getItems() {
        return items;
    }
}




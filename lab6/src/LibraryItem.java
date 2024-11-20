import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class LibraryItem {
    private final String id;
    private final String title;
    private LocalDate borrowDate;
    private boolean isBorrowed;
    protected int borrowPeriod;

    public LibraryItem(String id, String title, int borrowPeriod) {
        this.id = id;
        this.title = title;
        this.isBorrowed = false;
    }

    /**
     * Method checks if the borrowed LibraryItem is over due date
     * @return bool
     */
    public boolean isOverdue() {
        return daysOverdue(LocalDate.now()) > 0;
    }

    /**
     * Calculates the amount of days over the return date
     * @param returnDate - date at which book was returned
     * @return long - number of days over due
     */
    public long daysOverdue(LocalDate returnDate) {
        if (!isBorrowed || borrowDate == null) {
            return 0;
        }

        LocalDate dueDate = borrowDate.plusDays(borrowPeriod);

        if (returnDate.isBefore(dueDate)) {
            return 0;
        }

        long overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        return overdueDays;
    }

    /**
     * Abstract method to implement for each subclass
     * @return daily Fee amount for late return
     */
    public abstract double getFineRate();

    /**
     * Cmputes the fine
     * @param currentDate -- Date
     * @return Fine amount
     */
    public double computeFine(LocalDate currentDate) {
        return daysOverdue(currentDate) * getFineRate();
    }


    //Getters -  - - - - - -- - - - - - - - - -
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public LocalDate getBorrowedDate() {
        return borrowDate;

    }

    public int getBorrowPeriod() {
        return borrowPeriod;
    }


    //Setters -  - - - - - -- - - - - - - - - -
    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowDate = borrowedDate;
    }

    public void setBorrowPeriod(int borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }


}





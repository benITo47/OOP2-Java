import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private Library library;
    private User student;
    private User teacher;

    @BeforeEach
    void setUp() {
        library = new Library();

        // Dodaj użytkowników
        student = new User("S001", "John Doe", User.UserType.STUDENT);
        teacher = new User("T001", "Jane Smith", User.UserType.TEACHER);
        library.addUser(student);
        library.addUser(teacher);

        // Dodaj przedmioty
        library.addItem(new Book("B001", "Effective Java", "Joshua Bloch", "Programming", "Pearson"));
        library.addItem(new Film("F001", "Inception", "Christopher Nolan", 148, 9.0));
        library.addItem(new Journal("J001", "Nature", "1234-5678", "Nature Publishing", "2024-01-01"));
    }

    @Test
    void testStudentBorrowBook() {
        library.borrowItem("B001", student.getId(), LocalDate.now());
        assertTrue(library.getItems().get("B001").isBorrowed());
        assertEquals(1, student.getBorrowedItems().size());
    }

    @Test
    void testStudentBorrowMultipleItems() {
        library.borrowItem("B001", student.getId(), LocalDate.now());
        library.borrowItem("J001", student.getId(), LocalDate.now());

        assertEquals(2, student.getBorrowedItems().size());
        assertTrue(library.getItems().get("B001").isBorrowed());
        assertTrue(library.getItems().get("J001").isBorrowed());
    }

    @Test
    void testStudentBorrowMoreThanLimit() {
        library.borrowItem("B001", student.getId(), LocalDate.now());
        library.borrowItem("J001", student.getId(), LocalDate.now());
        library.borrowItem("F001", student.getId(), LocalDate.now());
        library.borrowItem("B002", student.getId(), LocalDate.now()); // Nie powinno się udać

        assertEquals(3, student.getBorrowedItems().size());
    }

    @Test
    void testStudentBorrowMultipleFilms() {
        library.borrowItem("F001", student.getId(), LocalDate.now());
        library.borrowItem("F002", student.getId(), LocalDate.now()); // Nie powinno się udać

        assertEquals(1, student.getBorrowedItems().size());
    }

    @Test
    void testTeacherBorrowItem() {
        library.borrowItem("B001", teacher.getId(), LocalDate.now());
        library.borrowItem("J001", teacher.getId(), LocalDate.now());

        assertEquals(2, teacher.getBorrowedItems().size());
    }

    @Test
    void testReturnItemOnTime() {
        library.borrowItem("B001", student.getId(), LocalDate.now());
        library.returnItem("B001", student.getId(), LocalDate.now().plusDays(2));

        assertFalse(library.getItems().get("B001").isBorrowed());
        assertEquals(0, student.getBorrowedItems().size());
        assertEquals(0, student.getBalance());
    }

    @Test
    void testReturnItemOverdue() {
        library.borrowItem("B001", student.getId(), LocalDate.now());
        library.returnItem("B001", student.getId(), LocalDate.now().plusDays(30)); // Overdue

        assertFalse(library.getItems().get("B001").isBorrowed());
        assertTrue(student.getBalance() > 0);
    }

    @Test
    void testStudentFineCalculation() {
        library.borrowItem("J001", student.getId(), LocalDate.now());
        library.returnItem("J001", student.getId(), LocalDate.now().plusDays(5)); // 2 dni opóźnienia

        assertEquals(2 * 2.0, student.getBalance()); // Kara 2 PLN za każdy dzień
    }

    @Test
    void testTeacherNoFine() {
        library.borrowItem("J001", teacher.getId(), LocalDate.now());
        library.returnItem("J001", teacher.getId(), LocalDate.now().plusDays(7));

        assertEquals(0, teacher.getBalance());
    }

    @Test
    void testBorrowUnavailableItem() {
        library.borrowItem("B001", student.getId(), LocalDate.now());
        library.borrowItem("B001", teacher.getId(), LocalDate.now());

        assertEquals(1, student.getBorrowedItems().size());
        assertEquals(0, teacher.getBorrowedItems().size());
    }
}

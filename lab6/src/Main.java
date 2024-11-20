import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        User student = new User("S001", "John Doe", User.UserType.STUDENT);
        User teacher = new User("T001", "Jane Smith", User.UserType.TEACHER);

        library.addUser(student);
        library.addUser(teacher);




        System.out.println("Student tries to borrow items:");
        library.borrowItem("B1", student.getId(), LocalDate.now());
        library.borrowItem("F1", student.getId(), LocalDate.now());
        library.borrowItem("B2", student.getId(), LocalDate.now());
        library.borrowItem("J1", student.getId(), LocalDate.now());


        System.out.println("\nTeacher tries to borrow items:");
        library.borrowItem("J5", teacher.getId(), LocalDate.now());
        library.borrowItem("F7", teacher.getId(), LocalDate.now());
        library.borrowItem("B9", teacher.getId(), LocalDate.now());

        System.out.println("\nStudent returns items:");
        library.returnItem("B1", student.getId(), LocalDate.now().plusDays(6));
        library.returnItem("F1", student.getId(), LocalDate.now().plusDays(9));
        library.returnItem("B2", student.getId(), LocalDate.now().plusDays(2));

        student.showUserBalance();

        System.out.println("\nTeacher returns items:");
        library.returnItem("B002", teacher.getId(), LocalDate.now());
        library.returnItem("F002", teacher.getId(), LocalDate.now().plusDays(7));
        library.returnItem("J002", teacher.getId(), LocalDate.now().plusDays(2));

        teacher.showUserBalance();

        System.out.println("\nStudent tries to borrow another film:");
        library.borrowItem("F001", student.getId(), LocalDate.now());
    }
}

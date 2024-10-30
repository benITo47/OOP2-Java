import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    static ArrayList<Person> people = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.println("What is your name?");
        String scanneed = scanner.nextLine();
        StringBuffer name = new StringBuffer(scanneed);

        System.out.println("What is your street addres:");
        scanneed = scanner.nextLine();
        StringBuffer street = new StringBuffer(scanneed);

        System.out.println("What is your home number:");
        int number = scanner.nextInt();

        Person person = new Person(name, street, number);
        people.add(person);

        System.out.println("Your details\n");
        person.printDetails();

        System.out.println("Performing a shallow copy\n");
        Person personCopy = new Person(person);
        people.add(personCopy);

        System.out.println("Performing a deep copy\n");
        Person personDeepCopy = new Person(person, true);
        people.add(personDeepCopy);

        System.out.println("All living instances of people\n\n");
        for (Person p : people) {
            p.printDetails();
        }

        System.out.println("\nChanging addres in shallow copy\n");
        personCopy.setNumber(21);
        personCopy.setStreet(new StringBuffer("Poczty Gdanskiej w Gdansku w Krakowie"));

        for (Person p : people) {
            p.printDetails();
        }


        System.out.println("\nChanging addres in deep copy\n");
        personDeepCopy.setNumber(37);
        personDeepCopy.setStreet(new StringBuffer("Wiejska"));

        for (Person p : people) {
            p.printDetails();
        }
    }

}
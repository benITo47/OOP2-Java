public class Person {
    private StringBuffer name; //Imie i Nazwisko
    private StringBuffer street;
    private int number;

    Person(StringBuffer name, StringBuffer street, int number) {
        setName(name);
        setStreet(street);
        setNumber(number);
    }

    Person(Person other) {
        this(other, false);
    }

    Person(Person other, boolean deep) {
        if (deep) {
            this.name = new StringBuffer(other.getName());
            this.street = new StringBuffer(other.getStreet());
        } else {
            this.name = other.name;
            this.street = other.street;
        }

        this.number = other.getNumber();


    }

    void printDetails() {
        System.out.println("-----------------------------");
        System.out.println("Name: " + name);
        System.out.println("Address: " + street + " " + number);
        System.out.println("-----------------------------");
    }


    //Getters
    StringBuffer getName() {
        return name;
    }

    StringBuffer getStreet() {
        return street;
    }

    int getNumber() {
        return number;
    }

    //Setters
    void setName(StringBuffer name) {
        this.name = name;
    }

    void setStreet(StringBuffer street) {
        this.street = street;
    }

    void setNumber(int number) {
        this.number = number;
    }


}


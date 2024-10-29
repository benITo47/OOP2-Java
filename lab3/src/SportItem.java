
public class SportItem {

    private final double price;
    private final int category;
    private final String name;

    public SportItem(double price, int category, String name) {

        this.price = price;
        this.category = category;
        this.name = name;
    }

    public static void printCategories() {
        String categoryName="";
        for (int i = 1; i < 5; i++) {
            categoryName = switch (i) {
                case 1 -> "Soccer";
                case 2 -> "Basketball";
                case 3 -> "Tennis";
                case 4 -> "Swimming";
                default -> categoryName;
            };
            System.out.println(i + ". " + categoryName);
        }
    }
        public double getPrice() {
            return price;
        }

        public void printItem(){
            System.out.println("------------------------------");
            System.out.println("Name: " + name);
            System.out.println("Price: " + price);
            System.out.println("Category: " + category);
            System.out.println("------------------------------");
        }
    }

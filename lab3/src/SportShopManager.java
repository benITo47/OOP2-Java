import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class SportShopManager {

    private static final ArrayList<SportItem> itemsArray = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("Hello and welcome to our Sport Shop Manager!");

        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        System.out.println("Please Specify the amount of gear you would like to add to the basket:");

        int amount = scanner.nextInt();

        for (int i = 1; i <= amount; i++) {
            scanner.nextLine(); // Added here so thath the scanner can read the newline character after the int number
            System.out.println("Please enter the item name:");
            String name = scanner.nextLine();

            System.out.println("Excellent, now please provide price for the item:");
            double price = scanner.nextDouble();


            System.out.println("Terrific, now please chose a category of the product, by inputting a number:");

            boolean isCategorValid = false;
            int category = 1;
            while (!isCategorValid) {

                SportItem.printCategories();
                category = scanner.nextInt();
                switch (category) {
                    case 1:
                        System.out.println("Kicking it into gear!");
                        isCategorValid = true;
                        break;
                    case 2:
                        System.out.println(" Nothing but net!");
                        isCategorValid = true;
                        break;
                    case 3:
                        System.out.println("Game, set, match!");
                        isCategorValid = true;
                        break;
                    case 4:
                        System.out.println("Dive into excellence!");
                        isCategorValid = true;
                        break;
                    default:
                        System.out.println("Invalid category - choose a valid category!");
                }
            }
            itemsArray.add(new SportItem(price, category, name));
            System.out.println("\nAdded " + name + " to the list!\n");
            if (i != amount) {
                System.out.println("-----------------------Add next item!---------------------");
            }



        }
        scanner.close();
        System.out.println("\nList of added items:\n");
        for (SportItem item : itemsArray) {
            item.printItem();
        }

        System.out.println("Total price of all items including discounts: " + CountFinalPrice());
    }

    public static double CountFinalPrice() {
        double sumCost = 0;
        for (SportItem item : itemsArray) {
            sumCost += item.getPrice();
        }
        if (sumCost > 200.00) {
            return sumCost * 0.85;
        } else if (sumCost > 100.00 && sumCost < 200.00) {
            return sumCost * 0.9;
        } else {
            return sumCost;
        }
    }

}
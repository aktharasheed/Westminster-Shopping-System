import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
interface ShoppingManager{


}
public class WestminsterShoppingManager  implements ShoppingManager {
    private static ArrayList<Product> products = new ArrayList<>(50);

    public WestminsterShoppingManager () {

        products = new ArrayList<>();
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Westminster Shopping Manager");
        Scanner input = new Scanner(System.in);
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager(); // Create an instance
        boolean status = false;

        while (!status) {
            try {
                String line = "\n-------------------------------------------------\nPlease select an option:\n1) Add new product\n2) Delete a product\n3) Print the list of products\n4) Save to a file\n5) Load data\n6) Open GUI\n0) Quit\n-------------------------------------------------\nEnter option: ";
                System.out.print(line);
                int num = input.nextInt();
                input.nextLine(); // Consume the newline character
                switch (num) {
                    case 0 -> {
                        status = true;
                        System.exit(0);
                    }
                    case 1 -> add_product();
                    case 2 -> delete_product();
                    case 3 -> print_products();
                    case 4 -> save();
                    case 5 -> load();
                    case 6 -> new GUI(shoppingManager); // Pass the instance to the constructor
                }
            } catch (InputMismatchException var10) {
                System.out.println("Enter a valid number");
                input.nextLine(); // Consume the newline character
            }
        }
    }

    public static void add_product() {
        Scanner input = new Scanner(System.in);

        if (products.size() >= 50) {
            System.out.println("Cannot add more products. Maximum limit (50) reached.");
            return;
        }

        while (true) {
            try {
                System.out.print("Product ID: ");
                String product_id = input.nextLine();

                boolean isDuplicate = products.stream().anyMatch(p -> p != null && product_id.equals(p.getProduct_id()));

                if (isDuplicate) {
                    System.out.println("Product with the same ID already exists. Please enter a unique product ID.");
                } else {
                    String product_name;
                    int available_items;
                    int price;
                    String brand;
                    String size;
                    int warranty;
                    String colour;
                    int option;

                    // Product Name
                    System.out.print("Product Name: ");
                    product_name = input.nextLine();

                    // Available Items
                    while (true) {
                        try {
                            System.out.print("Available Items: ");
                            available_items = input.nextInt();
                            input.nextLine(); // Consume the newline character
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number of available items.");
                            input.nextLine();
                        }
                    }

                    // Price
                    while (true) {
                        try {
                            System.out.print("Price: ");
                            price = input.nextInt();
                            input.nextLine(); // Consume the newline character
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid price.");
                            input.nextLine();
                        }
                    }

                    // Product Type
                    while (true) {
                        System.out.println("Option 1- Electronics\nOption 2- Clothing");
                        System.out.print("Option: ");
                        option = input.nextInt();

                        if (option == 1) {
                            // Electronics
                            System.out.print("Brand: ");
                            brand = input.next();

                            // Warranty
                            while (true) {
                                try {
                                    System.out.print("Warranty Period: ");
                                    warranty = input.nextInt();
                                    input.nextLine(); // Consume the newline character
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Please enter a valid warranty period.");
                                    input.nextLine();
                                }
                            }
                            size = ""; // Set size to an empty string for electronics
                            colour = ""; // Set colour to an empty string for electronics
                            break;
                        } else if (option == 2) {
                            // Clothing
                            System.out.print("Size: ");
                            size = input.next();
                            System.out.print("Colour: ");
                            colour = input.next();
                            brand = ""; // Set brand to an empty string for clothing
                            warranty = 0; // Set warranty to 0 for clothing
                            break;
                        } else {
                            System.out.println("Enter a valid option\n");
                        }
                    }

                    // Create a new Product object
                    Product newProduct;
                    if (option == 1) {
                        newProduct = new Electronics(product_id, product_name, available_items, price, brand, warranty);
                    } else {
                        newProduct = new Clothing(product_id, product_name, available_items, price, size, colour);
                    }

                    // Add the new product to the ArrayList
                    products.add(newProduct);

                    System.out.println("Product added successfully.");
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid Product ID.");
                input.nextLine();
            }
        }
    }

    public static void delete_product() {
        Scanner input = new Scanner(System.in);
        if (products.isEmpty()) {
            System.out.println("No products to delete. List is empty.");
            return;
        }
        boolean status = false;
        System.out.println("Current Products: ");
        print_products();
        System.out.println("Enter the Product ID to delete: ");
        String product_id = input.nextLine();
        for (Product product : products) {
            if (product != null && product.getProduct_id().equals(product_id)) {
                status = true;
                products.remove(product);
                System.out.println("Product deleted successfully.");
                break;
            }
        }
        if (!status) {
            System.out.println("Product with the given ID not found. Deletion failed.");
        }
    }

    public static void print_products() {
        if (products.isEmpty()) {
            System.out.println("No products in the system.");
            return;
        }

        // Filter out null elements
        List<Product> nonNullProducts = products.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Product::getProduct_id))
                .toList();

        System.out.println("List of Products:");

        for (Product product : nonNullProducts) {
            System.out.println("Product ID: " + product.getProduct_id());
            System.out.println("Product Name: " + product.getProduct_name());
            System.out.println("Available Items: " + product.getAvailable_items());
            System.out.println("Price: " + product.getPrice());

            if (product instanceof Electronics electronicsProduct) {
                System.out.println("Category: Electronics");
                System.out.println("Brand: " + electronicsProduct.getBrand());
                System.out.println("Warranty: " + electronicsProduct.getWarranty() + " months");
            } else if (product instanceof Clothing clothingProduct) {
                System.out.println("Category: Clothing");
                System.out.println("Size: " + clothingProduct.getSize());
                System.out.println("Colour: " + clothingProduct.getColour());
            }

            System.out.println("-----------------------------------");
        }
    }

    public static void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("products.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(products);
            out.close();
            fileOut.close();
            System.out.println("Products saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    private static void load() {
        try {
            FileInputStream fileIn = new FileInputStream("products.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            products = (ArrayList<Product>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Products loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }


    public List<Product> getProducts() {
        return products;
    }
}

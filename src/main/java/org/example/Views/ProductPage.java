package org.example.Views;

import org.example.Controllers.MainController;
import org.example.Models.Cart;
import org.example.Models.Category;
import org.example.Models.Product;
import org.example.Repositories.CartRepo;
import org.example.Repositories.CategoriesRepo;
import org.example.Repositories.ProductRepo;

import java.util.*;

public class ProductPage implements Page {
    private Scanner scanner = new Scanner(System.in);
    private CategoriesRepo categoriesRepo;
    private ProductRepo productRepo;
    private CartRepo cartRepo;
    List<Product> products = new ArrayList<>();
    List<Category> categories = new ArrayList<>();
    Cart cart;
    Map<Long, Integer> currentCart = new HashMap<Long, Integer>();

    public ProductPage() {
        categoriesRepo = new CategoriesRepo();
        productRepo = new ProductRepo();
        cartRepo = CartRepo.getInstance();
        cart = new Cart();
        currentCart = cart.getCart();
        // Eagerly fetch data
        getData();
    }

    public void show() {
        boolean running = true;

        while (running) {
            printMenu();
            System.out.println("Please enter your choice");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    printData();
                    break;
                case "2":
                    buyProductFlow();
                    break;
                case "3":
                    System.out.println("Current cart: " + currentCart);
                    break;
                case "4":
                    System.out.println("Go to checkout page");
                    cartRepo.setCart(cart);
                    MainController.setCurrentPage("checkout");
                    return;
                case "0":
                    System.out.println("Exiting product page");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

    }

    private void buyProductFlow() {
        boolean isMoveOn = false;
        currentCart = cart.getCart();
        while (!isMoveOn) {
            System.out.println("Please enter the product id you want to buy:");
            System.out.println("Press 0 if you want to return to Product Page");
            int productId = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline
            if (productId == 0) {
                System.out.println("Back to product page");
                isMoveOn = true;
                return;
            } else {
                // the buying flow
                // check product exists
                // if yes add to cart with quantity, if not back to the buying flow
                Product product = products.stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null);
                if (product == null) {
                    System.out.println("Product not found");
                    continue;
                } else {
                    System.out.println("Enter quantity:");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // consume leftover newline
                    if (product.getQuantity() < quantity || quantity < 0) {
                        System.out.println("Invalid quantity");
                        continue;
                    } else {
                        // Add to cart
                        currentCart.put(product.getProductId(), currentCart.getOrDefault(product.getProductId(), 0) + quantity);
                        continue;
                    }
                }

            }
        }


    }

    public void printMenu() {
        System.out.println("------------- The Product page -----------");
        System.out.println("You can choose to view or buy product on different categories");
        System.out.println("1. To View all categories and products");
        System.out.println("2. To Buy a product");
        System.out.println("3. To View your Cart");
        System.out.println("4. To Checkout a cart");
    }

    public void printData() {
        for (Category category : categories) {
            System.out.printf("%s. %s : \n", category.id(), category.name());
            products.stream().filter(product -> Objects.equals(product.getCategoryId(), category.id()))
                    .forEach(System.out::println);
        }
    }

    public void clearData() {
        categoriesRepo.clear();
        productRepo.clear();
    }

    public void getData() {
        categoriesRepo.get();
        categories = categoriesRepo.getCategories();

        productRepo.get();
        products = productRepo.getProducts();
    }

    public void getCategories() {
        categoriesRepo.getCategories();
    }
    public void getProducts() {
        productRepo.getProducts();
    }


}

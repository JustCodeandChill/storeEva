package org.example.Views;

import org.example.Controllers.MainController;
import org.example.Models.Cart;
import org.example.Models.Product;
import org.example.Repositories.CartRepo;
import org.example.Repositories.ProductRepo;
import org.example.Utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CheckoutPage implements Page{
    Scanner scanner = new Scanner(System.in);
    CartRepo cartRepo;
    ProductRepo productRepo;
    List<Product> products;
    Cart cart;

    // This data is persisted from Product Page
    public CheckoutPage() {
        cartRepo = CartRepo.getInstance();
        cart = cartRepo.getCart();
        productRepo = new ProductRepo();
    }

    @Override
    public void show() {
        System.out.println("--------------- Checkout page ---------------");
        System.out.println("1. View your order");
        System.out.println("2. Pay");
        int choice = scanner.nextInt();
        scanner.nextLine();
        products = getProductsFromCart();
        this.cart = cartRepo.getCart();

        switch (choice) {
            case 1:
                printOrder();
                break;
            case 2:
                checkout();
                break;
            default:
                System.out.println("Invalid choice. Back to Product Page");
                MainController.setCurrentPage("product");
        }
    }

    private void checkout() {
        System.out.println("--------------- Enter your card information ---------------");
        System.out.println("The sample card number is 123, the card CVV: 123, or ('4111111111111111', '123')");
        String cardNumber = "";
        String cardCVV = "";

        boolean isValidCard = false;
        while (!isValidCard) {
            System.out.println("Enter your card number:");
            cardNumber = scanner.nextLine();
            System.out.println("Enter your card CVV:");
            cardCVV = scanner.nextLine();
            boolean isValidCardNumber = isValidCreditCard(cardNumber);
            boolean isValidCVV =  isValidCvvFormat(cardCVV);
            isValidCard = isValidCardNumber && isValidCVV;
            if (!isValidCard) {
                System.out.println("Invalid card number or cvv format. Please try again.");
            }
        }


        try (Connection connection = ConnectionUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM card WHERE card_number = ?");
            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String number = resultSet.getString("card_number");
                String cvv = resultSet.getString("card_cvv");

                if (cvv.equalsIgnoreCase(cardCVV)) {
                    System.out.println("You have successfully checked out your card.");
                    // update database
                    deductProductsFromDB(cartRepo.getCart());
                    cartRepo.getCart().getCart().clear();

                    // redirect to homepage
                    MainController.setCurrentPage("product");
                }

            } else {
                System.out.println("Invalid card number or CVV. Unable to authenticate");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void printOrder() {
        System.out.println("--------------- Your order ---------------");
        System.out.println(products);
        int total = 0;

        for (Product product : products) {
            System.out.println(product.getName());
            int price = product.getPrice();
            int quantity = cart.getCart().get(product.getProductId());
            int basePrice = price * quantity;
            double tax = (double) product.getTaxPercent() / 100;
            int taxPrice = (int) (tax * basePrice);
            int totalPrice = basePrice + taxPrice;
            total += totalPrice;
            System.out.println("Base price: " + basePrice);
            System.out.println("Tax price: " + taxPrice);

        }

        System.out.println("Total price: " + total);
    }

    public List<Product> getProductsFromCart() {
        Cart cart = cartRepo.getCart();
        Set<Long> productIds = cart.getCart().keySet();
        if (productIds.isEmpty()) return Collections.emptyList();

        String placeholders = productIds.stream()
                .map(id -> "?")
                .reduce((a, b) -> a + "," + b)
                .orElse("");

        String sql = "SELECT * FROM products WHERE product_id IN (" + placeholders + ")";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            int index = 1;
            for (Long id : productIds) {
                stmt.setLong(index++, id);
            }

            ResultSet rs = stmt.executeQuery();
            List<Product> productsInCart = new ArrayList<>();
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getLong("product_id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getInt("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setTaxPercent(rs.getInt("tax_percent"));
                p.setCategoryId(rs.getLong("category_id"));
                productsInCart.add(p);
            }

            return productsInCart;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isValidCreditCard(String cc) {
        String regex = "^(?:4[0-9]{12}(?:[0-9]{3})?" +
                "|5[1-5][0-9]{14}" +
                "|3[47][0-9]{13}" +
                "|3(?:0[0-5]|[68][0-9])[0-9]{11}" +
                "|6(?:011|5[0-9]{2})[0-9]{12}" +
                "|(?:2131|1800|35\\d{3})\\d{11})$";

        return cc.matches(regex) || cc.matches("123");
    }

    public boolean isValidCvvFormat(String cvv) {
        if (cvv == null) return false;
        return cvv.matches("^[0-9]{3,4}$");
    }

    public void deductProductsFromDB(Cart cart) {
        System.out.println(cart);
        String sql = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            Map<Long, Integer> items = cart.getCart();
            for (Map.Entry<Long, Integer> entry : items.entrySet()) {
                long productId = entry.getKey();
                int purchasedQty = entry.getValue();

                stmt.setInt(1, purchasedQty);
                stmt.setLong(2, productId);
                stmt.addBatch();
            }

            int[] updatedRows = stmt.executeBatch();
            System.out.println("Updated quantities for " + updatedRows.length + " products.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update product quantities in DB", e);
        }
    }
}

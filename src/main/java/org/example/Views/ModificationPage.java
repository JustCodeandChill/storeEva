package org.example.Views;

import org.example.Controllers.MainController;
import org.example.Controllers.SecurityService;
import org.example.Securities.SecurityContext;
import org.example.Utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ModificationPage implements Page{
    SecurityContext currentSecurityContext;
    Scanner scanner = new Scanner(System.in);

    public ModificationPage() {
        currentSecurityContext =  SecurityContext.getInstance();
    }

    @Override
    public void show() {
        if (!checkAuthentication()) {
            MainController.setCurrentPage("login");
            return;
        }

        printMenu();

        String role = currentSecurityContext.getRole();
        if (role.equals("admin")) {
            adminFlow();
        } else if (role.equals("manager")) {
            managerFlow();
        }
    }

    private void managerFlow() {
        System.out.println("No functionalities for manager right now");
    }

    public void adminFlow() {
        System.out.println("1. Add new product");
        System.out.println("2. Delete product");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice == 1) {
            System.out.print("Enter product name: ");
            String productName = scanner.nextLine();

            System.out.print("Enter product price: ");
            int price = scanner.nextInt();

            System.out.print("Enter product quantity: ");
            int quantity = scanner.nextInt();

            System.out.print("Enter tax percent (default 5): ");
            int taxPercent = scanner.nextInt();

            System.out.print("Enter category ID: ");
            long categoryId = scanner.nextLong();
            scanner.nextLine(); // consume newline

            addProduct(productName, price, quantity, taxPercent, categoryId);
        }
        else if (choice == 2) {
            System.out.print("Enter the Product ID to delete: ");
            long productId = scanner.nextLong();
            scanner.nextLine(); // consume newline

            deleteProductById(productId);
        }

    }

    private void addProduct(String name, int price, int quantity, int taxPercent, long categoryId) {
        String sql = "INSERT INTO products (name, price, tax_percent, quantity, category_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setInt(2, price);
            stmt.setInt(3, taxPercent);
            stmt.setInt(4, quantity);
            stmt.setLong(5, categoryId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Product added successfully!");
            } else {
                System.out.println("Failed to add product.");
            }

        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    private void deleteProductById(long productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, productId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println(" Product with ID " + productId + " deleted successfully.");
            } else {
                System.out.println(" No product found with ID " + productId + ".");
            }

        } catch (SQLException e) {
            System.out.println(" Error deleting product: " + e.getMessage());
        }
    }

    public boolean checkAuthentication() {
        currentSecurityContext =  SecurityContext.getInstance();
        System.out.println("Logged in as " + currentSecurityContext.getUsername() + " roles" + currentSecurityContext.getRole());

        if (currentSecurityContext == null
                || !currentSecurityContext.isAuthenticated()
                || !(currentSecurityContext.getRole().equalsIgnoreCase("admin")
                || currentSecurityContext.getRole().equalsIgnoreCase("manager"))) {
            System.out.println("You are not authorized to perform this action.");
            return false;
        }

        return true;
    }

    public void printMenu() {
        System.out.println("--------------- Modification Page ------------------");
        System.out.println("Only Admin role is allowed to modify all the product. Manager only allow to modify price");
    }
}

package org.example.Views;

import org.example.Controllers.MainController;
import org.example.Utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RegisterPage implements Page {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        System.out.println("--------------- Welcome to the Register Page --------------------");
        System.out.println("We will ask for username and password to create a new Account");

        try (Connection connection = ConnectionUtils.getConnection()) {
            String username = getUsername();
            String password = getPassword();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO account.users (username, password) VALUES (?,?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully logged in as " + username + ". Moved to Login Page");
                MainController.go("login");
            } else {
                System.out.println("Failed to log in as " + username + ". Please try again");
                show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }

    public String getUsername() {
        String result = "";
        while (true) {
            try (Connection connection = ConnectionUtils.getConnection()) {
                System.out.println("Please enter your username: ");
                String username = scanner.nextLine();

                // Check username exists or not
                PreparedStatement ps = connection.prepareStatement("select * from account.users where username = ?");
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    System.out.println("Username already exists!. Please enter a different username");
                    continue;
                } else {
                    result = username;
                    break;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {

            }
        }
        return result;
    }

    public String getPassword() {
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();
        return password;
    }
}

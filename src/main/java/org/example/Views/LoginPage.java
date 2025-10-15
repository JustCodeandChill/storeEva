package org.example.Views;

import org.example.Controllers.MainController;
import org.example.Controllers.SecurityService;
import org.example.Securities.SecurityContext;
import org.example.Utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LoginPage implements Page {
    Scanner scanner = new Scanner(System.in);
    private boolean isMoveOn = false;
    private String nextPage;
    SecurityService securityService;

    public LoginPage() {
        securityService = new SecurityService();
    }

    public void show() {
        isMoveOn = false;
        while (!isMoveOn) {
            login();
        }
//        MainController.go("home"); to test authentication, do this later
        MainController.setCurrentPage(nextPage);
    }

    public void login() {
        System.out.println("------------ This is the Login Page --------------");
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        System.out.println("Checking username and password from database");

        try (Connection connection = ConnectionUtils.getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT password, roles FROM account.users WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Invalid username or password! Back to Login Page");
                isMoveOn = false;
            }
            else {
                String databasePassword = rs.getString("password");
                String role = rs.getString("roles");

                if (password.equals(databasePassword)) {
                    System.out.println("Successfully logged in as " + username + ". Moved to Next Page");
                    // authorization
                    SecurityContext.getInstance().setAuthentication(username, role);
                    if (role.equals("user")) {
                        nextPage = "product";
                    } else if (role.equals("admin")) {
                        System.out.println("welcome admin");
                        nextPage = "modify";
                    }

                    isMoveOn = true;
                } else {
                    System.out.println("Invalid username or password! Back to Login Page");
                    isMoveOn = false;
                }
            }

        } catch (Exception e) {
            System.out.println("Connection Failed! Check output console");
            System.out.println(e);

        } finally {
        }
    }
}

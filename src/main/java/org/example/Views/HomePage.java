package org.example.Views;

import org.example.Controllers.MainController;
import org.example.Controllers.SecurityService;
import org.example.Securities.SecurityContext;
import org.example.Utils.SecurityUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HomePage implements Page {
    Scanner scanner = new Scanner(System.in);
    Set<String> options;
    String nextPage = "";
    SecurityService securityService;
    SecurityUtils securityUtils;

    public HomePage() {
        options = new HashSet<>(Arrays.asList("yes", "y", "Yes", "no", "n", "No"));
        securityService = new SecurityService();
        securityUtils = new SecurityUtils();
    }

    public void show() {
        if (securityUtils.isAuthenticated()) {
            System.out.println("Redirecting to product page");
            MainController.go("product");
        }

        String decision="";
        boolean isMoveOn = false;

        while (!isMoveOn) {
            printMenu();
            decision = scanner.nextLine();

            // return to homepage if decision is invalid
            if (!options.contains(decision)) {
                isMoveOn = false;
                continue;
            }

            if (isNewUser(decision)) {
                System.out.println("Move you to registration page");
                nextPage = "register";
                isMoveOn = true;
                break;
            }
            else if (isCurrentUser(decision)) {
                System.out.println("Move you to Login page");
                nextPage = "login";
                isMoveOn = true;
                break;
            }
        }


        if (isMoveOn) {
            MainController.go(nextPage);
        }
    }

    private void printMenu() {
        System.out.println("Welcome to the Home Page");
        System.out.println("Are you a new user. Type in the console: yes or no?");
    }

    private boolean isNewUser(String s) {
        return s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y");
    }

    private boolean isCurrentUser(String s) {
        return !isNewUser(s);
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public void checkAuthenticationAndRedirect() {
        SecurityContext ctx = securityService.getCurrentSecurityContext();
        if (ctx.isAuthenticated()) {
            System.out.println("You are authenticated as " + ctx.getUsername() + " Redirect to memer page");
        } else  {
            System.out.println("You are not authenticated");
        }
    }
}

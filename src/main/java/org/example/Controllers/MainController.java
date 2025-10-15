package org.example.Controllers;

import org.example.Securities.SecurityContext;
import org.example.Views.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    public static String currentPage;
    public HomePage homePage;
    public LoginPage loginPage;
    public RegisterPage registerPage;
    public ProductPage productPage;
    public CheckoutPage checkoutPage;
    public ModificationPage modificationPage;
    private static SecurityService  securityService;


    public MainController() {
        securityService = new SecurityService();
        homePage = new HomePage();
        loginPage = new LoginPage();
        registerPage = new RegisterPage();
        productPage = new ProductPage();
        checkoutPage = new CheckoutPage();
        modificationPage = new ModificationPage();
    }

    public static String getCurrentPage() {
        return currentPage;
    }

    public static void setCurrentPage(String currentPage) {
        MainController.currentPage = currentPage;
    }

    public static void go(String page) {
        MainController.currentPage = page;
    }

    public void control() {
        if (currentPage.equalsIgnoreCase("home")) {
            homePage.show();
        } else if (currentPage.equalsIgnoreCase("login")) {
            loginPage.show();
        } else if (currentPage.equalsIgnoreCase("register")) {
            registerPage.show();
        } else if (currentPage.equalsIgnoreCase("product")) {
            productPage.show();
        } else if (currentPage.equalsIgnoreCase("checkout")) {
            checkoutPage.show();
        } else if (currentPage.equalsIgnoreCase("modify")) {
            modificationPage.show();
        }

    }

    public void run() {
        try {
            while (true) {
                control();
            }
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

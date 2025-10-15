package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.Controllers.MainController;
import org.example.Utils.ConnectionUtils;
import org.example.Views.HomePage;
import org.example.Views.LoginPage;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public MainController mainController;

    public static void main(String[] args) {


//        try {
//            Connection connection = ConnectionUtils.getConnection();
//
//            Statement statement = connection.createStatement();
//            ResultSet result = statement.executeQuery("select * from account.users");
//
//            while (result.next()) {
//                System.out.println(result.getString("username") + result.getString("password"));
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        MainController mainController = new MainController();
        MainController.setCurrentPage("home");
        mainController.run();

//            LoginPage loginPage = new LoginPage();
//            loginPage.show();
    }


}
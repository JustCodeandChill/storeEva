package org.example.Repositories;

import org.example.Models.Product;
import org.example.Utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepo {
    List<Product> products = new ArrayList<>();

    public ProductRepo() {
    }

    public void clear() {
        products.clear();
    }

    public void get() {
        try (Connection connection = ConnectionUtils.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                Integer quantity = resultSet.getInt("quantity");
                Integer price = resultSet.getInt("price");
                Integer tax = resultSet.getInt("tax_percent");
                Long categoryId = resultSet.getLong("category_id");


                Product product = new Product();
                product.setProductId(productId);
                product.setName(name);
                product.setQuantity(quantity);
                product.setPrice(price);
                product.setTaxPercent(tax);
                product.setCategoryId(categoryId);
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            throw new RuntimeException(e);
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

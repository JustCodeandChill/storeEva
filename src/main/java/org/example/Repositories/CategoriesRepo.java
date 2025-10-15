package org.example.Repositories;

import org.example.Models.Category;
import org.example.Utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriesRepo {
    List<Category> categories = new ArrayList<>();

    public CategoriesRepo() {
    }
    public void clear() {
        categories.clear();
    }
    public void get() {
        try (Connection connection = ConnectionUtils.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM categories");
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Category category = new Category(id,name);
                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            throw new RuntimeException(e);
        }
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}

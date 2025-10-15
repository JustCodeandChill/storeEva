package org.example.Models;

import java.util.Objects;

public class Product {
    private  Long productId;
    private  String name;
    private  Integer price;
    private  Integer quantity;
    private  Integer taxPercent;
    private  Long categoryId;

    public Product(Long productId, String name, Integer price, Integer quantity, Integer taxPercent, Long categoryId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.taxPercent = taxPercent;
        this.categoryId = categoryId;
    }

    public Product() {
    }

    public Long productId() {
        return productId;
    }

    public String name() {
        return name;
    }

    public Integer price() {
        return price;
    }

    public Integer quantity() {
        return quantity;
    }

    public Integer taxPercent() {
        return taxPercent;
    }

    public Long categoryId() {
        return categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(Integer taxPercent) {
        this.taxPercent = taxPercent;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Product) obj;
        return Objects.equals(this.productId, that.productId) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.price, that.price) &&
                Objects.equals(this.quantity, that.quantity) &&
                Objects.equals(this.taxPercent, that.taxPercent) &&
                Objects.equals(this.categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, price, quantity, taxPercent, categoryId);
    }

    @Override
    public String toString() {
        return "Product[" +
                "productId=" + productId + ", " +
                "name=" + name + ", " +
                "price=" + price + ", " +
                "quantity=" + quantity + ", " +
                "taxPercent=" + taxPercent + ", " +
                "categoryId=" + categoryId + ']';
    }
};

package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private Integer stock = 0;

    @Column(nullable = false)
    private Double price = 0.0;

    @Column(name = "discount_type", nullable = false)
    private String discountType = "NONE";

    @Transient
    private Double discountedPrice;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "detail_id", referencedColumnName = "id")
    private ProductDetail detail = new ProductDetail();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String category, String brand, Integer stock, Double price, String discountType) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.stock = stock;
        this.price = price;
        this.discountType = discountType;
    }

    public void setDetail(ProductDetail detail) {
        this.detail = detail;
        if (detail != null) {
            detail.setProduct(this);
        }
    }

    public void addReview(Review review) {
        if (review != null) {
            this.reviews.add(review);
            review.setProduct(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public ProductDetail getDetail() {
        return detail;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        if (reviews != null) {
            for (Review review : reviews) {
                review.setProduct(this);
            }
        }
    }
}

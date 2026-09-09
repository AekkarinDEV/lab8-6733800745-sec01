package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import com.example.demo.model.Review;
import com.example.demo.repository.ProductRepository;
import com.example.demo.strategy.DiscountContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final DiscountContext discountContext;

    public ProductService(ProductRepository productRepository, DiscountContext discountContext) {
        this.productRepository = productRepository;
        this.discountContext = discountContext;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            double discounted = discountContext.applyDiscount(product.getPrice(), product.getDiscountType());
            product.setDiscountedPrice(discounted);
        }
        return products;
    }

    public Product getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        double discounted = discountContext.applyDiscount(product.getPrice(), product.getDiscountType());
        product.setDiscountedPrice(discounted);
        return product;
    }

    public Product saveProduct(Product product) {
        if (product.getDetail() != null) {
            product.getDetail().setProduct(product);
        }

        if (product.getReviews() != null && !product.getReviews().isEmpty()) {
            List<Review> validReviews = new ArrayList<>();
            for (Review review : product.getReviews()) {
                if (review.getReviewer() != null && !review.getReviewer().trim().isEmpty()) {
                    review.setProduct(product);
                    if (review.getReviewDate() == null) {
                        review.setReviewDate(LocalDate.now());
                    }
                    validReviews.add(review);
                }
            }
            product.setReviews(validReviews);
        }

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);

        existing.setName(updatedProduct.getName());
        existing.setCategory(updatedProduct.getCategory());
        existing.setBrand(updatedProduct.getBrand());
        existing.setStock(updatedProduct.getStock());
        existing.setPrice(updatedProduct.getPrice());
        existing.setDiscountType(updatedProduct.getDiscountType());

        if (updatedProduct.getDetail() != null) {
            ProductDetail existingDetail = existing.getDetail();
            if (existingDetail == null) {
                existingDetail = new ProductDetail();
                existingDetail.setProduct(existing);
                existing.setDetail(existingDetail);
            }
            existingDetail.setDescription(updatedProduct.getDetail().getDescription());
            existingDetail.setWarranty(updatedProduct.getDetail().getWarranty());
            existingDetail.setWeight(updatedProduct.getDetail().getWeight());
            existingDetail.setDimensions(updatedProduct.getDetail().getDimensions());
            existingDetail.setManufacturedCountry(updatedProduct.getDetail().getManufacturedCountry());
        }

        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

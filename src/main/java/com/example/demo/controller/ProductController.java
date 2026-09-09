package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.Review;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Product product = new Product();
        product.getReviews().add(new Review());
        model.addAttribute("product", product);
        return "products/add";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes) {
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("message", "บันทึกข้อมูลสินค้าเรียบร้อยแล้ว");
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product, RedirectAttributes redirectAttributes) {
        productService.updateProduct(id, product);
        redirectAttributes.addFlashAttribute("message", "แก้ไขข้อมูลสินค้าเรียบร้อยแล้ว");
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("message", "ลบสินค้าเรียบร้อยแล้ว");
        return "redirect:/products";
    }
}

package com.ims.controller;

import com.ims.model.Category;
import com.ims.model.Product;
import com.ims.model.Supplier;
import com.ims.service.CategoryService;
import com.ims.service.ProductService;
import com.ims.service.SupplierService;
import com.ims.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private UserService userService;

    // ✅ List all products
    @GetMapping
    public String listProducts(Model model) {

        log.info("listProducts by:{}", userService.getCurrentUser().getRole());
        List<Product> products = productService.getAllProducts();
        products.sort(Comparator.comparing(Product::getName));
        List<Category> categories = categoryService.getAllCategories();
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("suppliers", suppliers);
        return "product";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "add-product";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("product") Product product) {
        log.info("create product:{}", product);
        productService.create(product);
        return "redirect:/admin/dashboard";
    }

    // ✅ Delete product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        log.info("delete product:{}", id);
        productService.deleteProduct(id);
        return "redirect:/admin/dashboard";
    }
}
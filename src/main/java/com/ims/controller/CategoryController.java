package com.ims.controller;

import com.ims.model.Category;
import com.ims.service.CategoryService;
import com.ims.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    // ✅ List all categories
    @GetMapping
    public String listCategories(Model model) {
        log.info("listCategories");
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category-list";
    }

    //
    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "add-category";
    }

    // Commanding after /add
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category) {
        log.info("saveCategory");
        categoryService.create(category);
        return "redirect:/categories";
    }

    // ✅ Delete category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        log.info("deleteCategory");
        categoryService.deleteCategory(id);
        return "redirect:/categories/";
    }
}
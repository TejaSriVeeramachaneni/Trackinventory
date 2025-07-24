package com.ims.service;

import com.ims.model.Category;
import com.ims.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public Category create(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepo.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
    }

    @Override
    public void deleteCategory(int id) {
        Optional<Category> category = categoryRepo.findById(id);
        category.ifPresent(categoryRepo::delete);
    }
}

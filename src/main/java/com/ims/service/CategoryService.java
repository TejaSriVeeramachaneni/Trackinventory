package com.ims.service;

import com.ims.model.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    void deleteCategory(int id);
}

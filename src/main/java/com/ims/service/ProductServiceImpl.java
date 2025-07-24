package com.ims.service;

import com.ims.model.Product;
import com.ims.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;


    @Override
    public Product create(Product product) {
        return productRepo.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product getProductByName(String name) {
        return productRepo.findByName(name).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void deleteProduct(int id) {
        Optional<Product> product = productRepo.findById(id);
        product.ifPresent(productRepo::delete);
    }
}

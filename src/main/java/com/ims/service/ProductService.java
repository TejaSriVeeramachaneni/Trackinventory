package com.ims.service;

import com.ims.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {
    Product create(Product product);
    List<Product> getAllProducts();
    Product getProductById(int id);
    Product getProductByName(String name);
    void deleteProduct(int id);
}

package com.ims.repository;

import com.ims.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);
}

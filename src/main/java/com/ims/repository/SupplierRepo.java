package com.ims.repository;

import com.ims.model.Supplier;
import com.ims.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Integer> {

    @Query("SELECT s FROM Supplier s JOIN Product p ON s.id = p.supplier.id WHERE p.id = :productId")
    List<Supplier> findSuppliersByProductId(@Param("productId") int productId);
}
package com.ims.service;

import com.ims.model.Product;
import com.ims.repository.OrderRepo;
import com.ims.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderRepo orderRepo;

    public long getTotalProducts() {
        return productRepo.count();
    }

    public long getTotalOrders() {
        return orderRepo.count();
    }

    public long getOrdersByStatus(String status) {
        return orderRepo.countByStatus(status);
    }

    public long getTotalStockQuantity() {
        return productRepo.findAll().stream().mapToInt(Product::getStockQuantity).sum();
    }

    public long getOrdersByType(String type) {
        return orderRepo.countByType(type);
    }
}

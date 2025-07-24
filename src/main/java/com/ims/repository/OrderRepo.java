package com.ims.repository;

import com.ims.model.Order;
import com.ims.model.OrderStatus;
import com.ims.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    List<Order> findByStatus(String status);

    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    int countByStatus(String status);

    int countByType(String type);

}

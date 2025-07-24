package com.ims.service;

import com.ims.model.Order;
import com.ims.model.OrderStatus;
import com.ims.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface OrderService {
//    List<Order> myOrders(User user);


    Order createOrder(Order order);

    List<Order> getAllOrders();

    Optional<Order> getOrderById(int id);

    List<Order> getPendingOrders();

    void approveOrder(int orderId, User approver);

    void rejectOrder(int orderId,User approver);




}

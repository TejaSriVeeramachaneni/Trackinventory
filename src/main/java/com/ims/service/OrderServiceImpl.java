package com.ims.service;

import com.ims.model.*;
import com.ims.repository.OrderItemRepo;
import com.ims.repository.OrderRepo;
import com.ims.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private OrderItemRepo orderItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepo userRepo;

//
    @Override
    public Order createOrder(Order order) {
        order.setStatus(String.valueOf(OrderStatus.PENDING));
        order.setOrderDate(LocalDate.now());
//        Product product = productService.getProductByName(order.getItems().toString());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    // Get order by ID
    @Override
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }


    @Override
    public List<Order> getPendingOrders() {
        return orderRepository.findByStatus("PENDING");
    }


    @Override
    @Transactional
    public void approveOrder(int orderId, User approve) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));
        order.setStatus("APPROVED");
        order.setApprovedBy(approve);
        if(order.getType().equals("PURCHASE")){
            for(OrderItem item: order.getItems()){
                Product product = item.getProduct();
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                productService.create(product);
            }
        }
        else{
            for(OrderItem item: order.getItems()){
                Product product = item.getProduct();
                int quantity = product.getStockQuantity() - item.getQuantity();
                if(quantity < 0){
                    throw new RuntimeException(product.getName() + " - is out of stock, Avaliable : " + (product.getStockQuantity()));
                }
                product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
                productService.create(product);
            }
        }

        orderRepository.save(order);
    }

    @Override
    public void rejectOrder(int orderId, User approve) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));
        order.setStatus("REJECTED");
        order.setApprovedBy(approve);
        orderRepository.save(order);
    }
}

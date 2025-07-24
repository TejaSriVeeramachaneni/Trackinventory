package com.ims.controller;

import com.ims.model.Order;
import com.ims.model.OrderItem;
import com.ims.model.OrderStatus;
import com.ims.model.User;
import com.ims.service.OrderService;
import com.ims.service.ProductService;
import com.ims.service.SupplierService;
import com.ims.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    // Show all orders
    @GetMapping
    public String listOrders(Model model) {
        log.info("listOrders:{}", userService.getCurrentUser().getRole());
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        log.info("showCreateForm:{}", userService.getCurrentUser().getRole());
        Order order = new Order();
        OrderItem item = new OrderItem();
        List<OrderItem> list= new ArrayList<>();
        list.add(item);
        order.setItems(list);

        model.addAttribute("order", order);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "orders/new";
    }

    // Handle create order request
    @PostMapping("/new")
    public String createOrder(@ModelAttribute("order") Order order) {
        log.info("createOrder:{}", userService.getCurrentUser().getRole());
        //String createdBy = (String) session.getAttribute("username");
//        System.out.println(session.toString());
        User user = userService.getCurrentUser();
        order.setCreatedBy(user);
        System.out.println(user.toString());
        for (OrderItem item : order.getItems()) {
            item.setOrder(order);
        }

        orderService.createOrder(order);
        return "redirect:/orders";

    }

    @GetMapping("/pending")
    public String viewPendingOrders(Model model) {
        log.info("viewPendingOrders:{}", userService.getCurrentUser().getRole());
        List<Order> pendingOrders = orderService.getPendingOrders();
        model.addAttribute("pendingOrders", pendingOrders);
        return "order-approval";
    }

    @PostMapping("/approve/{id}")
    public String approveOrder(@PathVariable int id) {
        log.info("approveOrder:{}", userService.getCurrentUser().getRole());
        User approver = userService.getCurrentUser();
        orderService.approveOrder(id, approver);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/reject/{id}")
    public String rejectOrder(@PathVariable int id) {
        log.info("rejectOrder:{}", userService.getCurrentUser().getRole());
        User approver = userService.getCurrentUser();
        orderService.rejectOrder(id,approver);
        return "redirect:/admin/dashboard";
    }
}

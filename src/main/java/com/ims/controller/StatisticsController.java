package com.ims.controller;

import com.ims.service.StatisticsService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StatisticsController {

    @Autowired
    private StatisticsService statsService;

    private static final Logger log = LoggerFactory.getLogger(StatisticsController.class);

    @GetMapping("/stats")
    public String showStats(Model model, HttpSession session) {
        log.info("show stats");
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";

        model.addAttribute("totalProducts", statsService.getTotalProducts());
        model.addAttribute("totalOrders", statsService.getTotalOrders());
        model.addAttribute("pendingOrders", statsService.getOrdersByStatus("PENDING"));
        model.addAttribute("approvedOrders", statsService.getOrdersByStatus("APPROVED"));
        model.addAttribute("rejectedOrders", statsService.getOrdersByStatus("REJECTED"));
        model.addAttribute("totalStock", statsService.getTotalStockQuantity());
        model.addAttribute("salesOrders", statsService.getOrdersByType("SALES"));
        model.addAttribute("purchaseOrders", statsService.getOrdersByType("PURCHASE"));


        return "stats";
    }
}

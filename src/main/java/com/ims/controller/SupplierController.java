package com.ims.controller;


import com.ims.model.Supplier;
import com.ims.service.ProductService;
import com.ims.service.SupplierService;
import com.ims.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {


    @Autowired
    private SupplierService supplierService;

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(SupplierController.class);

    // List all suppliers
    @GetMapping
    public String listSuppliers(Model model) {
        log.info("listSuppliers");
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "suppliers";
    }

    // Show form to add a new supplier
    @GetMapping("/add")
    public String add(Model model) {
        log.info("addSupplier");
        model.addAttribute("supplier", new Supplier());
        return "add-supplier";
    }

    // Save new or updated supplier
    @PostMapping("/save")
    public String save(@ModelAttribute("supplier") Supplier supplier) {
        log.info("Creating Supplier");
        supplierService.createSupplier(supplier);
        return "redirect:/suppliers";
    }

    // Edit supplier
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        log.info("editSupplier");
        Supplier supplier = supplierService.getSupplier(id);
        model.addAttribute("supplier", supplier);
        return "add-supplier";
    }

    // Delete supplier
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        log.info("deleteSupplier");
        supplierService.deleteSupplier(id);
        return "redirect:/suppliers";
    }
}

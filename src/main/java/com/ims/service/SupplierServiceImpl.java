package com.ims.service;

import com.ims.model.Supplier;
import com.ims.repository.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepo supplierRepo;


    @Override
    public Supplier createSupplier(Supplier supplier) {
        return supplierRepo.save(supplier);
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepo.findAll();
    }

    @Override
    public Supplier getSupplier(int supplierId) {
        return supplierRepo.findById(supplierId).orElseThrow(()->new RuntimeException("Supplier not found"));
    }

    @Override
    public void deleteSupplier(int supplierId) {
        Optional<Supplier> supplier = supplierRepo.findById(supplierId);
        supplier.ifPresent(supplier1 -> supplierRepo.delete(supplier1));
    }
}

package com.ims.service;

import com.ims.model.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface SupplierService {
    Supplier createSupplier(Supplier supplier);
    List<Supplier> getAllSuppliers();
    Supplier getSupplier(int supplierId);
    void deleteSupplier(int supplierId);
}

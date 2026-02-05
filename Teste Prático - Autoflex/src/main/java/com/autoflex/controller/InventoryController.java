package com.autoflex.controller;


import com.autoflex.dto.ProductionPlanDTO;
import com.autoflex.entity.Product;
import com.autoflex.entity.RawMaterial;
import com.autoflex.repository.ProductRepository;
import com.autoflex.repository.RawMaterialRepository;
import com.autoflex.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private ProductionService productionService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RawMaterialRepository rawMaterialRepository;


    @GetMapping("/production-plan")
    public ResponseEntity<List<ProductionPlanDTO>> getProductionPlan() {
        return ResponseEntity.ok(productionService.calculateProduction());
    }


    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {

        return productRepository.save(product);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    @PostMapping("/raw-materials")
    public RawMaterial createRawMaterial(@RequestBody RawMaterial material) {
        return rawMaterialRepository.save(material);
    }

    @GetMapping("/raw-materials")
    public List<RawMaterial> getAllRawMaterials() {
        return rawMaterialRepository.findAll();
    }
}

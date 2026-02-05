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
@CrossOrigin(origins = "*") // Necessário para o React
public class InventoryController {

    @Autowired
    private ProductionService productionService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    // --- RF004/RF008: Plano de Produção ---
    @GetMapping("/production-plan")
    public ResponseEntity<List<ProductionPlanDTO>> getProductionPlan() {
        return ResponseEntity.ok(productionService.calculateProduction());
    }

    // --- CRUD PRODUTOS (RF001 / RF005) ---

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        // Se vier com composições (receita), o JPA tenta salvar em cascata
        return productRepository.save(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setValue(productDetails.getValue());
            // Nota: Atualizar composições é complexo, mantivemos simples aqui
            return ResponseEntity.ok(productRepository.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // --- CRUD MATÉRIAS-PRIMAS (RF002 / RF006) ---

    @GetMapping("/raw-materials")
    public List<RawMaterial> getAllMaterials() {
        return rawMaterialRepository.findAll();
    }

    @PostMapping("/raw-materials")
    public RawMaterial createMaterial(@RequestBody RawMaterial material) {
        return rawMaterialRepository.save(material);
    }

    @PutMapping("/raw-materials/{id}")
    public ResponseEntity<RawMaterial> updateMaterial(@PathVariable Long id, @RequestBody RawMaterial details) {
        return rawMaterialRepository.findById(id).map(material -> {
            material.setName(details.getName());
            material.setStockQuantity(details.getStockQuantity());
            return ResponseEntity.ok(rawMaterialRepository.save(material));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/raw-materials/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        if (rawMaterialRepository.existsById(id)) {
            rawMaterialRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

package com.autoflex.controller;

import com.autoflex.entity.Product;
import com.autoflex.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {

        if (product.getCompositions() != null) {
            product.getCompositions().forEach(comp -> comp.setProduct(product));
        }
        return productRepository.save(product);
    }

    @GetMapping
    public List<Product> list() {
        return productRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
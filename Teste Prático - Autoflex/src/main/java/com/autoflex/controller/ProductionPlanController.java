package com.autoflex.controller;

import com.autoflex.dto.ProductionPlanDTO;
import com.autoflex.service.ProductionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/production-plan")
@CrossOrigin(origins = "*")
public class ProductionPlanController {

    private final ProductionService productionService;

    public ProductionPlanController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    public List<ProductionPlanDTO> getPlan() {
        // Ajustado para bater com o nome do método no Service
        return productionService.calculateProduction();
    }
}

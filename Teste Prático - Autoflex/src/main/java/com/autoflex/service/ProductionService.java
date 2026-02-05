package com.autoflex.service;


import com.autoflex.dto.ProductionPlanDTO;
import com.autoflex.entity.Product;
import com.autoflex.entity.ProductComposition;
import com.autoflex.entity.RawMaterial;
import com.autoflex.repository.ProductRepository;
import com.autoflex.repository.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductionService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    public List<ProductionPlanDTO> calculateProduction() {
        // 1. Busca produtos ordenados por valor (Prioridade do requisito)
        List<Product> products = productRepository.findAllOrderByValueDesc();

        // 2. Busca todo o estoque atual
        List<RawMaterial> materials = rawMaterialRepository.findAll();

        // 3. Cria um mapa de estoque temporário para simular o consumo
        // Map<ID da Matéria, Quantidade Disponível>
        Map<Long, Integer> tempStock = new HashMap<>();
        for (RawMaterial rm : materials) {
            tempStock.put(rm.getId(), rm.getStockQuantity());
        }

        List<ProductionPlanDTO> plan = new ArrayList<>();


        for (Product product : products) {
            int maxProducible = Integer.MAX_VALUE;


            for (ProductComposition comp : product.getCompositions()) {
                Long rawMaterialId = comp.getRawMaterial().getId();
                Integer quantityNeededPerUnit = comp.getQuantityRequired();
                Integer quantityAvailable = tempStock.getOrDefault(rawMaterialId, 0);

                if (quantityNeededPerUnit > 0) {
                    int possibleWithThisMaterial = quantityAvailable / quantityNeededPerUnit;

                    maxProducible = Math.min(maxProducible, possibleWithThisMaterial);
                }
            }

            // Se for possível produzir algo, adiciona ao plano e desconta do estoque
            if (maxProducible > 0 && maxProducible != Integer.MAX_VALUE) {

                // Atualiza o estoque temporário
                for (ProductComposition comp : product.getCompositions()) {
                    Long rawMaterialId = comp.getRawMaterial().getId();
                    Integer quantityNeeded = comp.getQuantityRequired() * maxProducible;

                    int currentStock = tempStock.get(rawMaterialId);
                    tempStock.put(rawMaterialId, currentStock - quantityNeeded);
                }


                BigDecimal totalValue = product.getValue().multiply(new BigDecimal(maxProducible));

                plan.add(new ProductionPlanDTO(product.getName(), maxProducible, totalValue));
            }
        }

        return plan;
    }


}

package com.autoflex.service;

import com.autoflex.dto.ProductionPlanDTO;
import com.autoflex.entity.*;
import com.autoflex.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;

/**
 * Serviço responsável por calcular o que pode ser produzido com base no estoque.
 * Se não houver material suficiente, ele gera um alerta para o usuário.
 */
@Service
public class ProductionService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductionService(ProductRepository productRepository, RawMaterialRepository rawMaterialRepository) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductionPlanDTO> calculateProduction() {
        // Busca os produtos priorizando os de maior valor de mercado
        List<Product> products = productRepository.findAllOrderByValueDesc();
        List<RawMaterial> materials = rawMaterialRepository.findAll();

        // Criamos um estoque temporário na memória para simular o uso dos materiais
        Map<Long, Integer> tempStock = new HashMap<>();
        for (RawMaterial rm : materials) {
            tempStock.put(rm.getId(), rm.getStockQuantity());
        }

        List<ProductionPlanDTO> plan = new ArrayList<>();

        for (Product product : products) {
            // Se o produto não tiver receita cadastrada, ignoramos
            if (product.getCompositions() == null || product.getCompositions().isEmpty()) {
                continue;
            }

            int maxProducible = Integer.MAX_VALUE;
            String missingMaterialName = "";

            // Verifica a disponibilidade de cada material da composição
            for (ProductComposition comp : product.getCompositions()) {
                Long matId = comp.getRawMaterial().getId();
                int available = tempStock.getOrDefault(matId, 0);
                int needed = comp.getQuantityRequired();

                if (needed > 0) {
                    int possible = available / needed;

                    // Se a quantidade disponível for menor que a necessária, guardamos o nome do material
                    if (possible < 1 && missingMaterialName.isEmpty()) {
                        missingMaterialName = comp.getRawMaterial().getName();
                    }

                    if (possible < maxProducible) {
                        maxProducible = possible;
                    }
                }
            }

            // Se for possível produzir pelo menos 1 unidade
            if (maxProducible > 0 && maxProducible != Integer.MAX_VALUE) {
                // Diminui os materiais do nosso estoque virtual
                for (ProductComposition comp : product.getCompositions()) {
                    Long matId = comp.getRawMaterial().getId();
                    int totalUsed = comp.getQuantityRequired() * maxProducible;
                    int currentInStock = tempStock.get(matId);
                    tempStock.put(matId, currentInStock - totalUsed);
                }

                // Calcula o valor total e adiciona ao plano
                BigDecimal totalValue = product.getValue().multiply(new BigDecimal(maxProducible));
                plan.add(new ProductionPlanDTO(product.getName(), product.getValue(), maxProducible, totalValue));
            } else {
                // LÓGICA DE ALERTA: Caso não tenha estoque, cria um DTO com a mensagem de erro
                ProductionPlanDTO warningItem = new ProductionPlanDTO();
                warningItem.setProductName(product.getName());
                warningItem.setUnitPrice(product.getValue());
                warningItem.setQuantityToProduce(0);
                warningItem.setTotalValue(BigDecimal.ZERO);

                // Define a mensagem de aviso indicando o material que acabou
                if (!missingMaterialName.isEmpty()) {
                    warningItem.setWarningMessage("Falta de material: " + missingMaterialName);
                } else {
                    warningItem.setWarningMessage("Estoque insuficiente");
                }

                plan.add(warningItem);
            }
        }
        return plan;
    }
}
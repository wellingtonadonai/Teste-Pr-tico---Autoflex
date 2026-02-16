package com.autoflex;

import com.autoflex.dto.ProductionPlanDTO;
import com.autoflex.entity.*;
import com.autoflex.repository.*;
import com.autoflex.service.ProductionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductionServiceTest {

    @InjectMocks
    private ProductionService productionService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @Test
    public void shouldCalculateProductionAndValuesCorrectly() {
        // 1. CENÁRIO: Temos 100 de Madeira e a Mesa custa $200 e pede 10 Madeiras
        RawMaterial wood = new RawMaterial();
        wood.setId(1L);
        wood.setName("Wood");
        wood.setStockQuantity(100);

        Product table = new Product();
        table.setId(1L);
        table.setName("Table");
        table.setValue(new BigDecimal("200.00"));

        ProductComposition composition = new ProductComposition();
        composition.setRawMaterial(wood);
        composition.setQuantityRequired(10);

        table.setCompositions(List.of(composition));

        Mockito.when(productRepository.findAllOrderByValueDesc()).thenReturn(List.of(table));
        Mockito.when(rawMaterialRepository.findAll()).thenReturn(List.of(wood));

        // 2. AÇÃO
        List<ProductionPlanDTO> result = productionService.calculateProduction();

        // 3. VERIFICAÇÃO
        Assertions.assertFalse(result.isEmpty(), "O plano não deveria estar vazio");
        ProductionPlanDTO dto = result.get(0);

        // Valida se a quantidade está certa (100 / 10 = 10)
        Assertions.assertEquals(10, dto.getQuantityToProduce());

        // Valida se o preço unitário passou correto ($200.00)
        Assertions.assertEquals(new BigDecimal("200.00"), dto.getUnitPrice());

        // Valida o Valor Total (10 unidades * $200 = $2000)
        // Usamos compareTo para BigDecimal porque é mais seguro que equals
        Assertions.assertEquals(0, new BigDecimal("2000.00").compareTo(dto.getTotalValue()));

        // Garante que não há erro de estoque neste cenário
        Assertions.assertNull(dto.getWarningMessage());
    }

    @Test
    public void shouldShowWarningWhenStockIsZero() {
        // CENÁRIO: Estoque zerado de madeira
        RawMaterial wood = new RawMaterial();
        wood.setName("Wood");
        wood.setStockQuantity(0);

        Product table = new Product();
        table.setName("Table");
        table.setValue(new BigDecimal("200.00"));

        ProductComposition comp = new ProductComposition();
        comp.setRawMaterial(wood);
        comp.setQuantityRequired(10);
        table.setCompositions(List.of(comp));

        Mockito.when(productRepository.findAllOrderByValueDesc()).thenReturn(List.of(table));
        Mockito.when(rawMaterialRepository.findAll()).thenReturn(List.of(wood));

        // AÇÃO
        List<ProductionPlanDTO> result = productionService.calculateProduction();

        // VERIFICAÇÃO: Deve aparecer o aviso que implementamos no Service
        Assertions.assertEquals(1, result.size());
        Assertions.assertNotNull(result.get(0).getWarningMessage());
        Assertions.assertTrue(result.get(0).getWarningMessage().contains("Wood"));
    }
}
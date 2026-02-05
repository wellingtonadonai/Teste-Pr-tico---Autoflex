package com.autoflex;


import com.autoflex.dto.ProductionPlanDTO;
import com.autoflex.entity.Product;
import com.autoflex.entity.ProductComposition;
import com.autoflex.entity.RawMaterial;
import com.autoflex.repository.ProductRepository;
import com.autoflex.repository.RawMaterialRepository;
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
    public void shouldCalculateProductionCorrectly() {
        // 1. CENÁRIO (ARRANGE)
        // Criando uma matéria-prima: Madeira
        RawMaterial wood = new RawMaterial();
        wood.setId(1L);
        wood.setName("Wood");
        wood.setStockQuantity(100); // Temos 100 unidades em estoque

        // Lista de matérias-primas do banco
        List<RawMaterial> stockList = new ArrayList<>();
        stockList.add(wood);

        // Criando um produto: Mesa
        Product table = new Product();
        table.setId(1L);
        table.setName("Table");
        table.setValue(new BigDecimal("200.00"));

        // Definindo a receita: Mesa precisa de 10 Madeiras
        ProductComposition composition = new ProductComposition();
        composition.setProduct(table);
        composition.setRawMaterial(wood);
        composition.setQuantityRequired(10); // Gasta 10 por unidade

        List<ProductComposition> compositions = new ArrayList<>();
        compositions.add(composition);
        table.setCompositions(compositions);

        // Lista de produtos do banco
        List<Product> productList = new ArrayList<>();
        productList.add(table);

        // Ensinando o Mockito a retornar nossas listas falsas
        Mockito.when(productRepository.findAllOrderByValueDesc()).thenReturn(productList);
        Mockito.when(rawMaterialRepository.findAll()).thenReturn(stockList);

        // 2. AÇÃO (ACT)
        // Chamamos o método que queremos testar
        List<ProductionPlanDTO> result = productionService.calculateProduction();

        // 3. VERIFICAÇÃO (ASSERT)
        // Se temos 100 madeiras e a mesa gasta 10, devemos conseguir produzir 10 mesas.

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size()); // Deve sugerir 1 tipo de produto

        ProductionPlanDTO dto = result.get(0);
        Assertions.assertEquals("Table", dto.getProductName());
        Assertions.assertEquals(10, dto.getQuantityToProduce()); // 100 / 10 = 10
    }
}
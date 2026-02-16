package com.autoflex.config;

import com.autoflex.entity.Product;
import com.autoflex.entity.ProductComposition;
import com.autoflex.entity.RawMaterial;
import com.autoflex.repository.ProductRepository;
import com.autoflex.repository.RawMaterialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Componente de inicialização de dados (Seeding).
 * Responsável por popular o banco de dados com registros iniciais para validação do sistema.
 */
@Configuration
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public DataInitializer(ProductRepository productRepository, RawMaterialRepository rawMaterialRepository) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica persistência existente para evitar duplicidade
        if (rawMaterialRepository.count() > 0) {
            return;
        }

        // Instanciação de Materiais de Referência
        RawMaterial wood = new RawMaterial();
        wood.setName("Wood");
        wood.setStockQuantity(50);

        RawMaterial iron = new RawMaterial();
        iron.setName("Iron");
        iron.setStockQuantity(30);

        RawMaterial plastic = new RawMaterial();
        plastic.setName("Plastic");
        plastic.setStockQuantity(100);

        rawMaterialRepository.saveAll(Arrays.asList(wood, iron, plastic));

        // Cadastro de Produto: Premium Desk (Prioridade Alta)
        Product table = new Product();
        table.setName("Premium Desk");
        table.setValue(new BigDecimal("1200.00"));

        ProductComposition c1 = new ProductComposition(table, wood, 4);
        ProductComposition c2 = new ProductComposition(table, iron, 2);

        table.setCompositions(Arrays.asList(c1, c2));
        productRepository.save(table);

        // Cadastro de Produto: Simple Chair
        Product chair = new Product();
        chair.setName("Simple Chair");
        chair.setValue(new BigDecimal("150.00"));

        ProductComposition c3 = new ProductComposition(chair, plastic, 2);
        ProductComposition c4 = new ProductComposition(chair, iron, 1);

        chair.setCompositions(Arrays.asList(c3, c4));
        productRepository.save(chair);
    }
}
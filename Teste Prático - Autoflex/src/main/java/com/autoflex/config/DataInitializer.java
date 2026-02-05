package com.autoflex.config;

import com.autoflex.entity.Product;
import com.autoflex.entity.ProductComposition;
import com.autoflex.entity.RawMaterial;
import com.autoflex.repository.ProductRepository;
import com.autoflex.repository.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Override
    public void run(String... args) throws Exception {
        // Se já tiver dados, não faz nada
        if (rawMaterialRepository.count() > 0) return;

        System.out.println("--- INICIANDO CARGA DE DADOS DE TESTE ---");

        // 1. Criar Matérias-Primas
        RawMaterial wood = new RawMaterial(); wood.setName("Wood (Madeira)"); wood.setStockQuantity(50);
        RawMaterial iron = new RawMaterial(); iron.setName("Iron (Ferro)"); iron.setStockQuantity(30);
        RawMaterial plastic = new RawMaterial(); plastic.setName("Plastic (Plástico)"); plastic.setStockQuantity(100);

        rawMaterialRepository.saveAll(Arrays.asList(wood, iron, plastic));

        // 2. Criar Produtos e Receitas

        // Produto 1: Mesa Premium (Valor Alto) - Gasta 4 Madeiras e 2 Ferros
        Product table = new Product();
        table.setName("Premium Desk");
        table.setValue(new BigDecimal("1200.00"));

        ProductComposition c1 = new ProductComposition(); c1.setProduct(table); c1.setRawMaterial(wood); c1.setQuantityRequired(4);
        ProductComposition c2 = new ProductComposition(); c2.setProduct(table); c2.setRawMaterial(iron); c2.setQuantityRequired(2);

        table.setCompositions(Arrays.asList(c1, c2));
        productRepository.save(table);

        // Produto 2: Cadeira Simples (Valor Baixo) - Gasta 2 Plásticos e 1 Ferro
        Product chair = new Product();
        chair.setName("Simple Chair");
        chair.setValue(new BigDecimal("150.00"));

        ProductComposition c3 = new ProductComposition(); c3.setProduct(chair); c3.setRawMaterial(plastic); c3.setQuantityRequired(2);
        ProductComposition c4 = new ProductComposition(); c4.setProduct(chair); c4.setRawMaterial(iron); c4.setQuantityRequired(1);

        chair.setCompositions(Arrays.asList(c3, c4));
        productRepository.save(chair);

        System.out.println("--- CARGA DE DADOS CONCLUÍDA ---");
    }
}

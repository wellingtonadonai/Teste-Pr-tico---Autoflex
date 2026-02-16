package com.autoflex.repository;

import com.autoflex.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Camada de acesso a dados para a entidade Product.
 * Estende JpaRepository para prover operações padrão de CRUD e persistência.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Recupera a listagem completa de produtos priorizada pelo valor de mercado.
     * Metodologia utilizada para o cálculo de sugestão do plano de produção (RF004).
     * * @return Lista de produtos ordenada do maior para o menor valor.
     */
    @Query("SELECT p FROM Product p ORDER BY p.value DESC")
    List<Product> findAllOrderByValueDesc();
}
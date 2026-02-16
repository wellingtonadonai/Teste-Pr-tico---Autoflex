package com.autoflex.repository;

import com.autoflex.entity.ProductComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCompositionRepository extends JpaRepository<ProductComposition, Long> {
    // Este método vai procurar e apagar todas as receitas que usam o material que queremos excluir
    void deleteByRawMaterialId(Long rawMaterialId);
}
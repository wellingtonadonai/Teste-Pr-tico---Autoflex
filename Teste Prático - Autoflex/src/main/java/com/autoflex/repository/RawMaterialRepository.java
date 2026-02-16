package com.autoflex.repository;

import com.autoflex.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de persistência para a entidade RawMaterial.
 * Provê os mecanismos de abstração de dados para o gerenciamento de inventário de insumos.
 */
public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
}
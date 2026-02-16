package com.autoflex.controller;

import com.autoflex.entity.RawMaterial;
import com.autoflex.repository.RawMaterialRepository;
import com.autoflex.repository.ProductCompositionRepository; // Importamos o repositório de composições
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional; // Necessário para deletar em sequência

import java.util.List;

@RestController
@RequestMapping("/api/raw-materials")
@CrossOrigin(origins = "*")
public class RawMaterialController {

    private final RawMaterialRepository rawMaterialRepository;
    private final ProductCompositionRepository productCompositionRepository; // Repositório auxiliar

    // Atualizamos o construtor para receber os dois repositórios
    public RawMaterialController(RawMaterialRepository rawMaterialRepository,
                                 ProductCompositionRepository productCompositionRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
        this.productCompositionRepository = productCompositionRepository;
    }

    @GetMapping
    public List<RawMaterial> getAll() {
        return rawMaterialRepository.findAll();
    }

    @PostMapping
    public RawMaterial create(@RequestBody RawMaterial material) {
        return rawMaterialRepository.save(material);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterial> update(@PathVariable Long id, @RequestBody RawMaterial details) {
        return rawMaterialRepository.findById(id).map(material -> {
            material.setName(details.getName());
            material.setStockQuantity(details.getStockQuantity());
            return ResponseEntity.ok(rawMaterialRepository.save(material));
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Método para excluir o material.
     * Usamos @Transactional para garantir que ele limpe as receitas antes de apagar o material.
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return rawMaterialRepository.findById(id).map(material -> {
            productCompositionRepository.deleteByRawMaterialId(id);
            rawMaterialRepository.delete(material);

            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

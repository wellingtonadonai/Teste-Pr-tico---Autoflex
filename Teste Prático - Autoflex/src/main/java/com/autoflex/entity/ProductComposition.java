package com.autoflex.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    public class ProductComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "raw_material_id", nullable = false)
    private RawMaterial rawMaterial;

    @Column(nullable = false)
    private Integer quantityRequired;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public RawMaterial getRawMaterial() {
            return rawMaterial;
        }

        public void setRawMaterial(RawMaterial rawMaterial) {
            this.rawMaterial = rawMaterial;
        }

        public Integer getQuantityRequired() {
            return quantityRequired;
        }

        public void setQuantityRequired(Integer quantityRequired) {
            this.quantityRequired = quantityRequired;
        }
    }

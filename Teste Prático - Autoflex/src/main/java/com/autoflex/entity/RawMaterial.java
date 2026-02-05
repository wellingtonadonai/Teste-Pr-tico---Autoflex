package com.autoflex.entity;

import jakarta.persistence.*;
import lombok.Data;

    @Entity
    @Data
    @Table(name = "raw_materials")
    public class RawMaterial {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private Integer stockQuantity; // Quantidade em estoque

}

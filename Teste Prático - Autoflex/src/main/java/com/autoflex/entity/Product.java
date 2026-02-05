package com.autoflex.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

    @Entity
    @Table(name = "products")
    public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private BigDecimal value;


        @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<ProductComposition> compositions = new ArrayList<>();

        public Product(){

        }

        public Product(Long id, String name, BigDecimal value, List<ProductComposition> compositions) {
            this.id = id;
            this.name = name;
            this.value = value;
            this.compositions = compositions;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getValue() {
            return value;
        }

        public void setValue(BigDecimal value) {
            this.value = value;
        }

        public List<ProductComposition> getCompositions() {
            return compositions;
        }

        public void setCompositions(List<ProductComposition> compositions) {
            this.compositions = compositions;
        }
    }

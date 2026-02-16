package com.autoflex.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Representa o produto final no banco de dados.
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal value;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductComposition> compositions;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
    public List<ProductComposition> getCompositions() { return compositions; }
    public void setCompositions(List<ProductComposition> compositions) { this.compositions = compositions; }
}
package com.autoflex.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductionPlanDTO {
    private String productName;
    private Integer quantityToProduce;
    private BigDecimal totalValue;
}

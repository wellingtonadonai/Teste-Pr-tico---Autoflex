package com.autoflex.dto;


import lombok.*;

import java.math.BigDecimal;



  public class ProductionPlanDTO {
    private String productName;
    private Integer quantityToProduce;
    private BigDecimal totalValue;

    public  ProductionPlanDTO(){

    }

    public ProductionPlanDTO(String productName, Integer quantityToProduce, BigDecimal totalValue) {
      this.productName = productName;
      this.quantityToProduce = quantityToProduce;
      this.totalValue = totalValue;
    }

    public String getProductName() {
      return productName;
    }

    public void setProductName(String productName) {
      this.productName = productName;
    }

    public Integer getQuantityToProduce() {
      return quantityToProduce;
    }

    public void setQuantityToProduce(Integer quantityToProduce) {
      this.quantityToProduce = quantityToProduce;
    }

    public BigDecimal getTotalValue() {
      return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
      this.totalValue = totalValue;
    }
  }

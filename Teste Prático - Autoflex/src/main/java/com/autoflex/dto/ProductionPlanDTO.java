package com.autoflex.dto;

import java.math.BigDecimal;

/**
 * Objeto usado para levar os dados do plano de produção para a tela.
 * Ele separa o preço da unidade da quantidade total, evitando confusão.
 */
public class ProductionPlanDTO {

  private String productName;        // Nome do produto (ex: Cadeira)
  private BigDecimal unitPrice;      // Preço de 1 unidade (ex: $50)
  private Integer quantityToProduce; // Quantas unidades o estoque permite fazer
  private BigDecimal totalValue;     // Valor total da produção (Preço x Quantidade)

  public ProductionPlanDTO() {
  }

  public ProductionPlanDTO(String productName, BigDecimal unitPrice, Integer quantityToProduce, BigDecimal totalValue) {
    this.productName = productName;
    this.unitPrice = unitPrice;
    this.quantityToProduce = quantityToProduce;
    this.totalValue = totalValue;
  }


  private String warningMessage;


  public String getWarningMessage() { return warningMessage; }
  public void setWarningMessage(String warningMessage) { this.warningMessage = warningMessage; }


  public String getProductName() { return productName; }
  public void setProductName(String productName) { this.productName = productName; }

  public BigDecimal getUnitPrice() { return unitPrice; }
  public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

  public Integer getQuantityToProduce() { return quantityToProduce; }
  public void setQuantityToProduce(Integer quantityToProduce) { this.quantityToProduce = quantityToProduce; }

  public BigDecimal getTotalValue() { return totalValue; }
  public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }


}
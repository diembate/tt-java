package com.ncc.java.service.dto;
import com.ncc.java.domain.Authority;
import com.ncc.java.domain.ImportDetailInfo;
import com.ncc.java.domain.Product;
import com.ncc.java.domain.User;

import java.math.BigDecimal;
import java.util.stream.Collectors;


public class ImportDetailInfoDTO {

    private Long id;
    private Integer quantityImport;
    private BigDecimal priceImport;
    private Long productId;

    public ImportDetailInfoDTO() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantityImport() {
        return quantityImport;
    }

    public void setQuantityImport(Integer quantityImport) {
        this.quantityImport = quantityImport;
    }

    public BigDecimal getPriceImport() {
        return priceImport;
    }

    public void setPriceImport(BigDecimal priceImport) {
        this.priceImport = priceImport;
    }



    public ImportDetailInfoDTO(ImportDetailInfo importDetailInfo) {
    this.id = importDetailInfo.getId();
    this.quantityImport = importDetailInfo.getQuantityImport();
    this.priceImport = importDetailInfo.getPriceImport();



}



}

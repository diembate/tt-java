package com.ncc.java.service.dto;

import com.ncc.java.domain.ImportDetailInfo;
import com.ncc.java.domain.OrderDetailInfo;

import java.math.BigDecimal;

public class OrderDetailInfoDTO {
    private Long id;
    private Integer quantityOrder;

    private Long product;

    public OrderDetailInfoDTO() {
    }

    public Integer getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(Integer quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public Long getProduct() {
        return product;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderDetailInfoDTO (OrderDetailInfo orderDetailInfo) {
        this.id = orderDetailInfo.getId();
        this.quantityOrder = orderDetailInfo.getQuantityOrder();


    }
}

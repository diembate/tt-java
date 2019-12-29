package com.ncc.java.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A OrderDetailInfo.
 */
@Entity
@Table(name = "order_detail_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderDetailInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price_product", precision = 21, scale = 2)
    private BigDecimal priceProduct;

    @Column(name = "quantity_order")
    private Integer quantityOrder;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @ManyToOne
    @JsonIgnoreProperties("orderDetailInfos")
    private OrderInfo orderInfo;

    @ManyToOne
    @JsonIgnoreProperties("orderDetailInfos")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public OrderDetailInfo productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPriceProduct() {
        return priceProduct;
    }

    public OrderDetailInfo priceProduct(BigDecimal priceProduct) {
        this.priceProduct = priceProduct;
        return this;
    }

    public void setPriceProduct(BigDecimal priceProduct) {
        this.priceProduct = priceProduct;
    }

    public Integer getQuantityOrder() {
        return quantityOrder;
    }

    public OrderDetailInfo quantityOrder(Integer quantityOrder) {
        this.quantityOrder = quantityOrder;
        return this;
    }

    public void setQuantityOrder(Integer quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public OrderDetailInfo amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public OrderDetailInfo orderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public OrderDetailInfo orderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        return this;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Product getProduct() {
        return product;
    }

    public OrderDetailInfo product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDetailInfo)) {
            return false;
        }
        return id != null && id.equals(((OrderDetailInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderDetailInfo{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", priceProduct=" + getPriceProduct() +
            ", quantityOrder=" + getQuantityOrder() +
            ", amount=" + getAmount() +
            ", orderDate='" + getOrderDate() + "'" +
            "}";
    }
}

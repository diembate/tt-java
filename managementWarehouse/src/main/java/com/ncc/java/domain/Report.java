package com.ncc.java.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Report.
 */
@Entity
@Table(name = "report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity_remaining")
    private Integer quantityRemaining;

    @Column(name = "total_order_quantity")
    private Integer totalOrderQuantity;

    @OneToMany(mappedBy = "report")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "report")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderDetailInfo> orderDetailInfos = new HashSet<>();

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

    public Report productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantityRemaining() {
        return quantityRemaining;
    }

    public Report quantityRemaining(Integer quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
        return this;
    }

    public void setQuantityRemaining(Integer quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
    }

    public Integer getTotalOrderQuantity() {
        return totalOrderQuantity;
    }

    public Report totalOrderQuantity(Integer totalOrderQuantity) {
        this.totalOrderQuantity = totalOrderQuantity;
        return this;
    }

    public void setTotalOrderQuantity(Integer totalOrderQuantity) {
        this.totalOrderQuantity = totalOrderQuantity;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Report products(Set<Product> products) {
        this.products = products;
        return this;
    }



    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<OrderDetailInfo> getOrderDetailInfos() {
        return orderDetailInfos;
    }

    public Report orderDetailInfos(Set<OrderDetailInfo> orderDetailInfos) {
        this.orderDetailInfos = orderDetailInfos;
        return this;
    }


    public void setOrderDetailInfos(Set<OrderDetailInfo> orderDetailInfos) {
        this.orderDetailInfos = orderDetailInfos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Report)) {
            return false;
        }
        return id != null && id.equals(((Report) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Report{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", quantityRemaining=" + getQuantityRemaining() +
            ", totalOrderQuantity=" + getTotalOrderQuantity() +
            "}";
    }
}

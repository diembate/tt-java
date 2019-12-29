package com.ncc.java.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A OrderInfo.
 */
@Entity
@Table(name = "order_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @ManyToOne
    @JsonIgnoreProperties("orderInfos")
    private Customer customer;

    @OneToMany(mappedBy = "orderInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderDetailInfo> orderDetailInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public OrderInfo amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public OrderInfo orderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderInfo customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OrderDetailInfo> getOrderDetailInfos() {
        return orderDetailInfos;
    }

    public OrderInfo orderDetailInfos(Set<OrderDetailInfo> orderDetailInfos) {
        this.orderDetailInfos = orderDetailInfos;
        return this;
    }

    public OrderInfo addOrderDetailInfo(OrderDetailInfo orderDetailInfo) {
        this.orderDetailInfos.add(orderDetailInfo);
        orderDetailInfo.setOrderInfo(this);
        return this;
    }

    public OrderInfo removeOrderDetailInfo(OrderDetailInfo orderDetailInfo) {
        this.orderDetailInfos.remove(orderDetailInfo);
        orderDetailInfo.setOrderInfo(null);
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
        if (!(o instanceof OrderInfo)) {
            return false;
        }
        return id != null && id.equals(((OrderInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", orderDate='" + getOrderDate() + "'" +
            "}";
    }
}

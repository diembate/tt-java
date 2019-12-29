package com.ncc.java.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Brand.
 */
@Entity
@Table(name = "brand")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_name")
    private String brandName;

    @OneToMany(mappedBy = "brand")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public Brand brandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Brand products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Brand addProduct(Product product) {
        this.products.add(product);
        product.setBrand(this);
        return this;
    }

    public Brand removeProduct(Product product) {
        this.products.remove(product);
        product.setBrand(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Brand)) {
            return false;
        }
        return id != null && id.equals(((Brand) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Brand{" +
            "id=" + getId() +
            ", brandName='" + getBrandName() + "'" +
            "}";
    }
}

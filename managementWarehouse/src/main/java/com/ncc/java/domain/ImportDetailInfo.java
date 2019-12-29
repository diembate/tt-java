package com.ncc.java.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A ImportDetailInfo.
 */
@Entity
@Table(name = "import_detail_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ImportDetailInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity_import")
    private Integer quantityImport;

    @Column(name = "import_date")
    private LocalDate importDate;

    @Column(name = "price_import", precision = 21, scale = 2)
    private BigDecimal priceImport;

    @ManyToOne
    @JsonIgnoreProperties("importDetailInfos")
    private ImportInfo importInfo;

    @ManyToOne
    @JsonIgnoreProperties("importDetailInfos")
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

    public ImportDetailInfo productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantityImport() {
        return quantityImport;
    }

    public ImportDetailInfo quantityImport(Integer quantityImport) {
        this.quantityImport = quantityImport;
        return this;
    }

    public void setQuantityImport(Integer quantityImport) {
        this.quantityImport = quantityImport;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public ImportDetailInfo importDate(LocalDate importDate) {
        this.importDate = importDate;
        return this;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public BigDecimal getPriceImport() {
        return priceImport;
    }

    public ImportDetailInfo priceImport(BigDecimal priceImport) {
        this.priceImport = priceImport;
        return this;
    }

    public void setPriceImport(BigDecimal priceImport) {
        this.priceImport = priceImport;
    }

    public ImportInfo getImportInfo() {
        return importInfo;
    }

    public ImportDetailInfo importInfo(ImportInfo importInfo) {
        this.importInfo = importInfo;
        return this;
    }

    public void setImportInfo(ImportInfo importInfo) {
        this.importInfo = importInfo;
    }

    public Product getProduct() {
        return product;
    }

    public ImportDetailInfo product(Product product) {
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
        if (!(o instanceof ImportDetailInfo)) {
            return false;
        }
        return id != null && id.equals(((ImportDetailInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ImportDetailInfo{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", quantityImport=" + getQuantityImport() +
            ", importDate='" + getImportDate() + "'" +
            ", priceImport=" + getPriceImport() +
            "}";
    }
}

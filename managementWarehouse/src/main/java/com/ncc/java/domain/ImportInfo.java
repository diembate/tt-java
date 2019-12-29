package com.ncc.java.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A ImportInfo.
 */
@Entity
@Table(name = "import_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ImportInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deliver_person")
    private String deliverPerson;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "cost", precision = 21, scale = 2)
    private BigDecimal cost;

    @OneToMany(mappedBy = "importInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ImportDetailInfo> importDetailInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliverPerson() {
        return deliverPerson;
    }

    public ImportInfo deliverPerson(String deliverPerson) {
        this.deliverPerson = deliverPerson;
        return this;
    }

    public void setDeliverPerson(String deliverPerson) {
        this.deliverPerson = deliverPerson;
    }

    public String getSupplier() {
        return supplier;
    }

    public ImportInfo supplier(String supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public ImportInfo cost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Set<ImportDetailInfo> getImportDetailInfos() {
        return importDetailInfos;
    }

    public ImportInfo importDetailInfos(Set<ImportDetailInfo> importDetailInfos) {
        this.importDetailInfos = importDetailInfos;
        return this;
    }

    public ImportInfo addImportDetailInfo(ImportDetailInfo importDetailInfo) {
        this.importDetailInfos.add(importDetailInfo);
        importDetailInfo.setImportInfo(this);
        return this;
    }

    public ImportInfo removeImportDetailInfo(ImportDetailInfo importDetailInfo) {
        this.importDetailInfos.remove(importDetailInfo);
        importDetailInfo.setImportInfo(null);
        return this;
    }

    public void setImportDetailInfos(Set<ImportDetailInfo> importDetailInfos) {
        this.importDetailInfos = importDetailInfos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImportInfo)) {
            return false;
        }
        return id != null && id.equals(((ImportInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ImportInfo{" +
            "id=" + getId() +
            ", deliverPerson='" + getDeliverPerson() + "'" +
            ", supplier='" + getSupplier() + "'" +
            ", cost=" + getCost() +
            "}";
    }
}

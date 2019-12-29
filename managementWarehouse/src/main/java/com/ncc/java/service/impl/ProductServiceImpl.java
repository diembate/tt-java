package com.ncc.java.service.impl;

import com.ncc.java.domain.ImportDetailInfo;
import com.ncc.java.domain.OrderDetailInfo;
import com.ncc.java.domain.Product;
import com.ncc.java.repository.ProductRepository;
import com.ncc.java.service.ImportDetailInfoService;
import com.ncc.java.service.OrderDetailInfoService;
import com.ncc.java.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private com.ncc.java.repository.ImportDetailInfoRepository ImportDetailInfoRepository;
    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Save a orderDetailInfo.
     *
     * @param product the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Product save(Product product) {
        log.debug("Request to save OrderDetailInfo : {}", product);
        ImportDetailInfo importDetailInfo ;
//
        return productRepository.save(product);
    }

    /**
     * Get all the Products.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        log.debug("Request to get all Products");
        return productRepository.findAll();
    }


    /**
     * Get one products by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id);
    }

    /**
     * Delete the Product by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderDetailInfo : {}", id);
        productRepository.deleteById(id);
    }


    @Override
    public void orderDetailBill(OrderDetailInfo orderDetailInfo) {
        Product product = orderDetailInfo.getProduct();
        product.setQuantityProduct(product.getQuantityProduct()-orderDetailInfo.getQuantityOrder());
        productRepository.save(product);
    }

    @Override
    public void importDetailBill(ImportDetailInfo importDetailInfo) {
        Product product = importDetailInfo.getProduct();
        product.setQuantityProduct( product.getQuantityProduct()+importDetailInfo.getQuantityImport());
        productRepository.save(product);
    }
}


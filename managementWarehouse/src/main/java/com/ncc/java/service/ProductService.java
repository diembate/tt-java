package com.ncc.java.service;


import com.ncc.java.domain.ImportDetailInfo;
import com.ncc.java.domain.OrderDetailInfo;
import com.ncc.java.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);


    /**
     * Get all the orderInfos.
     *
     * @return the list of entities.
     */
    List<Product> findAll();


    /**
     * Get the "id" orderInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Product> findOne(Long id);


    void orderDetailBill(OrderDetailInfo orderDetailInfo);
    void importDetailBill(ImportDetailInfo importDetailInfo);

    /**
     * Delete the "id" orderInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}


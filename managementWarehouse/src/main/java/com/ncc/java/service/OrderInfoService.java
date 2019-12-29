package com.ncc.java.service;

import com.ncc.java.domain.OrderInfo;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OrderInfo}.
 */
public interface OrderInfoService {

    /**
     * Save a orderInfo.
     *
     * @param orderInfo the entity to save.
     * @return the persisted entity.
     */
    OrderInfo save(OrderInfo orderInfo);

    /**
     * Get all the orderInfos.
     *
     * @return the list of entities.
     */
    List<OrderInfo> findAll();


    /**
     * Get the "id" orderInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderInfo> findOne(Long id);

    /**
     * Delete the "id" orderInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

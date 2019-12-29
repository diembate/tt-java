package com.ncc.java.service;

import com.ncc.java.domain.OrderDetailInfo;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OrderDetailInfo}.
 */
public interface OrderDetailInfoService {

    /**
     * Save a orderDetailInfo.
     *
     * @param orderDetailInfo the entity to save.
     * @return the persisted entity.
     */
    OrderDetailInfo save(OrderDetailInfo orderDetailInfo);

    /**
     * Get all the orderDetailInfos.
     *
     * @return the list of entities.
     */
    List<OrderDetailInfo> findAll();


    /**
     * Get the "id" orderDetailInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderDetailInfo> findOne(Long id);

    /**
     * Delete the "id" orderDetailInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

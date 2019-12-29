package com.ncc.java.service.impl;

import com.ncc.java.service.OrderInfoService;
import com.ncc.java.domain.OrderInfo;
import com.ncc.java.repository.OrderInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderInfo}.
 */
@Service
@Transactional
public class OrderInfoServiceImpl implements OrderInfoService {

    private final Logger log = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    private final OrderInfoRepository orderInfoRepository;

    public OrderInfoServiceImpl(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

    /**
     * Save a orderInfo.
     *
     * @param orderInfo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderInfo save(OrderInfo orderInfo) {
        log.debug("Request to save OrderInfo : {}", orderInfo);
        return orderInfoRepository.save(orderInfo);
    }

    /**
     * Get all the orderInfos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderInfo> findAll() {
        log.debug("Request to get all OrderInfos");
        return orderInfoRepository.findAll();
    }


    /**
     * Get one orderInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderInfo> findOne(Long id) {
        log.debug("Request to get OrderInfo : {}", id);
        return orderInfoRepository.findById(id);
    }

    /**
     * Delete the orderInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderInfo : {}", id);
        orderInfoRepository.deleteById(id);
    }
}

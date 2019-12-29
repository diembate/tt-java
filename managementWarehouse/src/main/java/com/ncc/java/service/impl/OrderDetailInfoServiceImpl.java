package com.ncc.java.service.impl;

import com.ncc.java.service.OrderDetailInfoService;
import com.ncc.java.domain.OrderDetailInfo;
import com.ncc.java.repository.OrderDetailInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderDetailInfo}.
 */
@Service
@Transactional
public class OrderDetailInfoServiceImpl implements OrderDetailInfoService {

    private final Logger log = LoggerFactory.getLogger(OrderDetailInfoServiceImpl.class);

    private final OrderDetailInfoRepository orderDetailInfoRepository;

    public OrderDetailInfoServiceImpl(OrderDetailInfoRepository orderDetailInfoRepository) {
        this.orderDetailInfoRepository = orderDetailInfoRepository;
    }

    /**
     * Save a orderDetailInfo.
     *
     * @param orderDetailInfo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderDetailInfo save(OrderDetailInfo orderDetailInfo) {
        log.debug("Request to save OrderDetailInfo : {}", orderDetailInfo);
        Date now = Calendar.getInstance().getTime();
        Instant instant = Instant.ofEpochMilli(now.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        orderDetailInfo.setOrderDate(localDate);
        return orderDetailInfoRepository.save(orderDetailInfo);
    }

    /**
     * Get all the orderDetailInfos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderDetailInfo> findAll() {
        log.debug("Request to get all OrderDetailInfos");
        return orderDetailInfoRepository.findAll();
    }


    /**
     * Get one orderDetailInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDetailInfo> findOne(Long id) {
        log.debug("Request to get OrderDetailInfo : {}", id);
        return orderDetailInfoRepository.findById(id);
    }

    /**
     * Delete the orderDetailInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderDetailInfo : {}", id);
        orderDetailInfoRepository.deleteById(id);
    }
}

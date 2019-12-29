package com.ncc.java.web.rest;

import com.ncc.java.domain.OrderInfo;
import com.ncc.java.service.OrderInfoService;
import com.ncc.java.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ncc.java.domain.OrderInfo}.
 */
@RestController
@RequestMapping("/api")
public class OrderInfoResource {

    private final Logger log = LoggerFactory.getLogger(OrderInfoResource.class);

    private static final String ENTITY_NAME = "orderInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderInfoService orderInfoService;

    public OrderInfoResource(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }

    /**
     * {@code POST  /order-infos} : Create a new orderInfo.
     *
     * @param orderInfo the orderInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderInfo, or with status {@code 400 (Bad Request)} if the orderInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-infos")
    public ResponseEntity<OrderInfo> createOrderInfo(@RequestBody OrderInfo orderInfo) throws URISyntaxException {
        log.debug("REST request to save OrderInfo : {}", orderInfo);
        if (orderInfo.getId() != null) {
            throw new BadRequestAlertException("A new orderInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderInfo result = orderInfoService.save(orderInfo);
        return ResponseEntity.created(new URI("/api/order-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-infos} : Updates an existing orderInfo.
     *
     * @param orderInfo the orderInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderInfo,
     * or with status {@code 400 (Bad Request)} if the orderInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-infos")
    public ResponseEntity<OrderInfo> updateOrderInfo(@RequestBody OrderInfo orderInfo) throws URISyntaxException {
        log.debug("REST request to update OrderInfo : {}", orderInfo);
        if (orderInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderInfo result = orderInfoService.save(orderInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-infos} : get all the orderInfos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderInfos in body.
     */
    @GetMapping("/order-infos")
    public List<OrderInfo> getAllOrderInfos() {
        log.debug("REST request to get all OrderInfos");
        return orderInfoService.findAll();
    }

    /**
     * {@code GET  /order-infos/:id} : get the "id" orderInfo.
     *
     * @param id the id of the orderInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-infos/{id}")
    public ResponseEntity<OrderInfo> getOrderInfo(@PathVariable Long id) {
        log.debug("REST request to get OrderInfo : {}", id);
        Optional<OrderInfo> orderInfo = orderInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderInfo);
    }

    /**
     * {@code DELETE  /order-infos/:id} : delete the "id" orderInfo.
     *
     * @param id the id of the orderInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-infos/{id}")
    public ResponseEntity<Void> deleteOrderInfo(@PathVariable Long id) {
        log.debug("REST request to delete OrderInfo : {}", id);
        orderInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

package com.ncc.java.web.rest;


import com.ncc.java.domain.OrderDetailInfo;
import com.ncc.java.domain.Product;
import com.ncc.java.repository.ProductRepository;
import com.ncc.java.service.OrderDetailInfoService;
import com.ncc.java.service.ProductService;
import com.ncc.java.service.dto.OrderDetailInfoDTO;
import com.ncc.java.service.mapper.OrderDetailInfoMapper;
import com.ncc.java.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ncc.java.domain.OrderDetailInfo}.
 */
@RestController
@RequestMapping("/api")
public class OrderDetailInfoResource {
    @Autowired
    OrderDetailInfoMapper orderDetailInfoMapper;
@Autowired
    ProductService  productService;

@Autowired
    ProductRepository productRepository;
    private final Logger log = LoggerFactory.getLogger(OrderDetailInfoResource.class);

    private static final String ENTITY_NAME = "orderDetailInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderDetailInfoService orderDetailInfoService;

    public OrderDetailInfoResource(OrderDetailInfoService orderDetailInfoService) {
        this.orderDetailInfoService = orderDetailInfoService;
    }

    /**
     * {@code POST  /order-detail-infos} : Create a new orderDetailInfo.
     *
     * @param orderDetailInfoDTO the orderDetailInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderDetailInfo, or with status {@code 400 (Bad Request)} if the orderDetailInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-detail-infos")
    public ResponseEntity<OrderDetailInfo> createOrderDetailInfo(@RequestBody OrderDetailInfoDTO orderDetailInfoDTO) throws URISyntaxException {
        log.debug("REST request to save OrderDetailInfo : {}", orderDetailInfoDTO);
        OrderDetailInfo orderDetailInfo = orderDetailInfoMapper.orderDetailInfoDTOToOrderDetailInfo(orderDetailInfoDTO);
        if (orderDetailInfo.getId() != null) {
            throw new BadRequestAlertException("A new orderDetailInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Product productOrder =productRepository.findOneById(orderDetailInfoDTO.getProduct());
        orderDetailInfo.setProduct(productOrder);

        Product product = orderDetailInfo.getProduct();
        BigDecimal itemCost  = BigDecimal.ZERO;
        itemCost  = product.getPriceProduct().multiply(new BigDecimal(orderDetailInfo.getQuantityOrder()));
        orderDetailInfo.setProductName(product.getProductName());
        orderDetailInfo.setPriceProduct(product.getPriceProduct());
        orderDetailInfo.setAmount(itemCost);

        OrderDetailInfo result = orderDetailInfoService.save(orderDetailInfo);
        productService.orderDetailBill(result);



        return ResponseEntity.created(new URI("/api/order-detail-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-detail-infos} : Updates an existing orderDetailInfo.
     *
     * @param orderDetailInfo the orderDetailInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetailInfo,
     * or with status {@code 400 (Bad Request)} if the orderDetailInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderDetailInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-detail-infos")
    public ResponseEntity<OrderDetailInfo> updateOrderDetailInfo(@RequestBody OrderDetailInfo orderDetailInfo) throws URISyntaxException {
        log.debug("REST request to update OrderDetailInfo : {}", orderDetailInfo);
        if (orderDetailInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderDetailInfo result = orderDetailInfoService.save(orderDetailInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderDetailInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-detail-infos} : get all the orderDetailInfos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderDetailInfos in body.
     */
    @GetMapping("/order-detail-infos")
    public List<OrderDetailInfo> getAllOrderDetailInfos() {
        log.debug("REST request to get all OrderDetailInfos");
        return orderDetailInfoService.findAll();
    }

    /**
     * {@code GET  /order-detail-infos/:id} : get the "id" orderDetailInfo.
     *
     * @param id the id of the orderDetailInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderDetailInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-detail-infos/{id}")
    public ResponseEntity<OrderDetailInfo> getOrderDetailInfo(@PathVariable Long id) {
        log.debug("REST request to get OrderDetailInfo : {}", id);
        Optional<OrderDetailInfo> orderDetailInfo = orderDetailInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderDetailInfo);
    }

    /**
     * {@code DELETE  /order-detail-infos/:id} : delete the "id" orderDetailInfo.
     *
     * @param id the id of the orderDetailInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-detail-infos/{id}")
    public ResponseEntity<Void> deleteOrderDetailInfo(@PathVariable Long id) {
        log.debug("REST request to delete OrderDetailInfo : {}", id);
        orderDetailInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

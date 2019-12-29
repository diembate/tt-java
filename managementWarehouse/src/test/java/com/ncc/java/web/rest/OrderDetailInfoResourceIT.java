package com.ncc.java.web.rest;

import com.ncc.java.ManagementWarehouseApp;
import com.ncc.java.domain.OrderDetailInfo;
import com.ncc.java.repository.OrderDetailInfoRepository;
import com.ncc.java.service.OrderDetailInfoService;
import com.ncc.java.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.ncc.java.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderDetailInfoResource} REST controller.
 */
@SpringBootTest(classes = ManagementWarehouseApp.class)
public class OrderDetailInfoResourceIT {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE_PRODUCT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_PRODUCT = new BigDecimal(2);

    private static final Integer DEFAULT_QUANTITY_ORDER = 1;
    private static final Integer UPDATED_QUANTITY_ORDER = 2;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrderDetailInfoRepository orderDetailInfoRepository;

    @Autowired
    private OrderDetailInfoService orderDetailInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOrderDetailInfoMockMvc;

    private OrderDetailInfo orderDetailInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderDetailInfoResource orderDetailInfoResource = new OrderDetailInfoResource(orderDetailInfoService);
        this.restOrderDetailInfoMockMvc = MockMvcBuilders.standaloneSetup(orderDetailInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderDetailInfo createEntity(EntityManager em) {
        OrderDetailInfo orderDetailInfo = new OrderDetailInfo()
            .productName(DEFAULT_PRODUCT_NAME)
            .priceProduct(DEFAULT_PRICE_PRODUCT)
            .quantityOrder(DEFAULT_QUANTITY_ORDER)
            .amount(DEFAULT_AMOUNT)
            .orderDate(DEFAULT_ORDER_DATE);
        return orderDetailInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderDetailInfo createUpdatedEntity(EntityManager em) {
        OrderDetailInfo orderDetailInfo = new OrderDetailInfo()
            .productName(UPDATED_PRODUCT_NAME)
            .priceProduct(UPDATED_PRICE_PRODUCT)
            .quantityOrder(UPDATED_QUANTITY_ORDER)
            .amount(UPDATED_AMOUNT)
            .orderDate(UPDATED_ORDER_DATE);
        return orderDetailInfo;
    }

    @BeforeEach
    public void initTest() {
        orderDetailInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderDetailInfo() throws Exception {
        int databaseSizeBeforeCreate = orderDetailInfoRepository.findAll().size();

        // Create the OrderDetailInfo
        restOrderDetailInfoMockMvc.perform(post("/api/order-detail-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderDetailInfo)))
            .andExpect(status().isCreated());

        // Validate the OrderDetailInfo in the database
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoRepository.findAll();
        assertThat(orderDetailInfoList).hasSize(databaseSizeBeforeCreate + 1);
        OrderDetailInfo testOrderDetailInfo = orderDetailInfoList.get(orderDetailInfoList.size() - 1);
        assertThat(testOrderDetailInfo.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testOrderDetailInfo.getPriceProduct()).isEqualTo(DEFAULT_PRICE_PRODUCT);
        assertThat(testOrderDetailInfo.getQuantityOrder()).isEqualTo(DEFAULT_QUANTITY_ORDER);
        assertThat(testOrderDetailInfo.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testOrderDetailInfo.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
    }

    @Test
    @Transactional
    public void createOrderDetailInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderDetailInfoRepository.findAll().size();

        // Create the OrderDetailInfo with an existing ID
        orderDetailInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderDetailInfoMockMvc.perform(post("/api/order-detail-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderDetailInfo)))
            .andExpect(status().isBadRequest());

        // Validate the OrderDetailInfo in the database
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoRepository.findAll();
        assertThat(orderDetailInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderDetailInfos() throws Exception {
        // Initialize the database
        orderDetailInfoRepository.saveAndFlush(orderDetailInfo);

        // Get all the orderDetailInfoList
        restOrderDetailInfoMockMvc.perform(get("/api/order-detail-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderDetailInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].priceProduct").value(hasItem(DEFAULT_PRICE_PRODUCT.intValue())))
            .andExpect(jsonPath("$.[*].quantityOrder").value(hasItem(DEFAULT_QUANTITY_ORDER)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderDetailInfo() throws Exception {
        // Initialize the database
        orderDetailInfoRepository.saveAndFlush(orderDetailInfo);

        // Get the orderDetailInfo
        restOrderDetailInfoMockMvc.perform(get("/api/order-detail-infos/{id}", orderDetailInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderDetailInfo.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.priceProduct").value(DEFAULT_PRICE_PRODUCT.intValue()))
            .andExpect(jsonPath("$.quantityOrder").value(DEFAULT_QUANTITY_ORDER))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderDetailInfo() throws Exception {
        // Get the orderDetailInfo
        restOrderDetailInfoMockMvc.perform(get("/api/order-detail-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderDetailInfo() throws Exception {
        // Initialize the database
        orderDetailInfoService.save(orderDetailInfo);

        int databaseSizeBeforeUpdate = orderDetailInfoRepository.findAll().size();

        // Update the orderDetailInfo
        OrderDetailInfo updatedOrderDetailInfo = orderDetailInfoRepository.findById(orderDetailInfo.getId()).get();
        // Disconnect from session so that the updates on updatedOrderDetailInfo are not directly saved in db
        em.detach(updatedOrderDetailInfo);
        updatedOrderDetailInfo
            .productName(UPDATED_PRODUCT_NAME)
            .priceProduct(UPDATED_PRICE_PRODUCT)
            .quantityOrder(UPDATED_QUANTITY_ORDER)
            .amount(UPDATED_AMOUNT)
            .orderDate(UPDATED_ORDER_DATE);

        restOrderDetailInfoMockMvc.perform(put("/api/order-detail-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderDetailInfo)))
            .andExpect(status().isOk());

        // Validate the OrderDetailInfo in the database
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoRepository.findAll();
        assertThat(orderDetailInfoList).hasSize(databaseSizeBeforeUpdate);
        OrderDetailInfo testOrderDetailInfo = orderDetailInfoList.get(orderDetailInfoList.size() - 1);
        assertThat(testOrderDetailInfo.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testOrderDetailInfo.getPriceProduct()).isEqualTo(UPDATED_PRICE_PRODUCT);
        assertThat(testOrderDetailInfo.getQuantityOrder()).isEqualTo(UPDATED_QUANTITY_ORDER);
        assertThat(testOrderDetailInfo.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testOrderDetailInfo.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderDetailInfo() throws Exception {
        int databaseSizeBeforeUpdate = orderDetailInfoRepository.findAll().size();

        // Create the OrderDetailInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderDetailInfoMockMvc.perform(put("/api/order-detail-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderDetailInfo)))
            .andExpect(status().isBadRequest());

        // Validate the OrderDetailInfo in the database
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoRepository.findAll();
        assertThat(orderDetailInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderDetailInfo() throws Exception {
        // Initialize the database
        orderDetailInfoService.save(orderDetailInfo);

        int databaseSizeBeforeDelete = orderDetailInfoRepository.findAll().size();

        // Delete the orderDetailInfo
        restOrderDetailInfoMockMvc.perform(delete("/api/order-detail-infos/{id}", orderDetailInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoRepository.findAll();
        assertThat(orderDetailInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

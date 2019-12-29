package com.ncc.java.web.rest;

import com.ncc.java.ManagementWarehouseApp;
import com.ncc.java.domain.OrderInfo;
import com.ncc.java.repository.OrderInfoRepository;
import com.ncc.java.service.OrderInfoService;
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
 * Integration tests for the {@link OrderInfoResource} REST controller.
 */
@SpringBootTest(classes = ManagementWarehouseApp.class)
public class OrderInfoResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderInfoService orderInfoService;

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

    private MockMvc restOrderInfoMockMvc;

    private OrderInfo orderInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderInfoResource orderInfoResource = new OrderInfoResource(orderInfoService);
        this.restOrderInfoMockMvc = MockMvcBuilders.standaloneSetup(orderInfoResource)
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
    public static OrderInfo createEntity(EntityManager em) {
        OrderInfo orderInfo = new OrderInfo()
            .amount(DEFAULT_AMOUNT)
            .orderDate(DEFAULT_ORDER_DATE);
        return orderInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderInfo createUpdatedEntity(EntityManager em) {
        OrderInfo orderInfo = new OrderInfo()
            .amount(UPDATED_AMOUNT)
            .orderDate(UPDATED_ORDER_DATE);
        return orderInfo;
    }

    @BeforeEach
    public void initTest() {
        orderInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderInfo() throws Exception {
        int databaseSizeBeforeCreate = orderInfoRepository.findAll().size();

        // Create the OrderInfo
        restOrderInfoMockMvc.perform(post("/api/order-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderInfo)))
            .andExpect(status().isCreated());

        // Validate the OrderInfo in the database
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll();
        assertThat(orderInfoList).hasSize(databaseSizeBeforeCreate + 1);
        OrderInfo testOrderInfo = orderInfoList.get(orderInfoList.size() - 1);
        assertThat(testOrderInfo.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testOrderInfo.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
    }

    @Test
    @Transactional
    public void createOrderInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderInfoRepository.findAll().size();

        // Create the OrderInfo with an existing ID
        orderInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderInfoMockMvc.perform(post("/api/order-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderInfo)))
            .andExpect(status().isBadRequest());

        // Validate the OrderInfo in the database
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll();
        assertThat(orderInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderInfos() throws Exception {
        // Initialize the database
        orderInfoRepository.saveAndFlush(orderInfo);

        // Get all the orderInfoList
        restOrderInfoMockMvc.perform(get("/api/order-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderInfo() throws Exception {
        // Initialize the database
        orderInfoRepository.saveAndFlush(orderInfo);

        // Get the orderInfo
        restOrderInfoMockMvc.perform(get("/api/order-infos/{id}", orderInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderInfo.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderInfo() throws Exception {
        // Get the orderInfo
        restOrderInfoMockMvc.perform(get("/api/order-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderInfo() throws Exception {
        // Initialize the database
        orderInfoService.save(orderInfo);

        int databaseSizeBeforeUpdate = orderInfoRepository.findAll().size();

        // Update the orderInfo
        OrderInfo updatedOrderInfo = orderInfoRepository.findById(orderInfo.getId()).get();
        // Disconnect from session so that the updates on updatedOrderInfo are not directly saved in db
        em.detach(updatedOrderInfo);
        updatedOrderInfo
            .amount(UPDATED_AMOUNT)
            .orderDate(UPDATED_ORDER_DATE);

        restOrderInfoMockMvc.perform(put("/api/order-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderInfo)))
            .andExpect(status().isOk());

        // Validate the OrderInfo in the database
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll();
        assertThat(orderInfoList).hasSize(databaseSizeBeforeUpdate);
        OrderInfo testOrderInfo = orderInfoList.get(orderInfoList.size() - 1);
        assertThat(testOrderInfo.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testOrderInfo.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderInfo() throws Exception {
        int databaseSizeBeforeUpdate = orderInfoRepository.findAll().size();

        // Create the OrderInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderInfoMockMvc.perform(put("/api/order-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderInfo)))
            .andExpect(status().isBadRequest());

        // Validate the OrderInfo in the database
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll();
        assertThat(orderInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderInfo() throws Exception {
        // Initialize the database
        orderInfoService.save(orderInfo);

        int databaseSizeBeforeDelete = orderInfoRepository.findAll().size();

        // Delete the orderInfo
        restOrderInfoMockMvc.perform(delete("/api/order-infos/{id}", orderInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll();
        assertThat(orderInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

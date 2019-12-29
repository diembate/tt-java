package com.ncc.java.web.rest;

import com.ncc.java.ManagementWarehouseApp;
import com.ncc.java.domain.ImportDetailInfo;
import com.ncc.java.repository.ImportDetailInfoRepository;
import com.ncc.java.service.ImportDetailInfoService;
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
 * Integration tests for the {@link ImportDetailInfoResource} REST controller.
 */
@SpringBootTest(classes = ManagementWarehouseApp.class)
public class ImportDetailInfoResourceIT {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY_IMPORT = 1;
    private static final Integer UPDATED_QUANTITY_IMPORT = 2;

    private static final LocalDate DEFAULT_IMPORT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IMPORT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PRICE_IMPORT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_IMPORT = new BigDecimal(2);

    @Autowired
    private ImportDetailInfoRepository importDetailInfoRepository;

    @Autowired
    private ImportDetailInfoService importDetailInfoService;

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

    private MockMvc restImportDetailInfoMockMvc;

    private ImportDetailInfo importDetailInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImportDetailInfoResource importDetailInfoResource = new ImportDetailInfoResource(importDetailInfoService);
        this.restImportDetailInfoMockMvc = MockMvcBuilders.standaloneSetup(importDetailInfoResource)
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
    public static ImportDetailInfo createEntity(EntityManager em) {
        ImportDetailInfo importDetailInfo = new ImportDetailInfo()
            .productName(DEFAULT_PRODUCT_NAME)
            .quantityImport(DEFAULT_QUANTITY_IMPORT)
            .importDate(DEFAULT_IMPORT_DATE)
            .priceImport(DEFAULT_PRICE_IMPORT);
        return importDetailInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImportDetailInfo createUpdatedEntity(EntityManager em) {
        ImportDetailInfo importDetailInfo = new ImportDetailInfo()
            .productName(UPDATED_PRODUCT_NAME)
            .quantityImport(UPDATED_QUANTITY_IMPORT)
            .importDate(UPDATED_IMPORT_DATE)
            .priceImport(UPDATED_PRICE_IMPORT);
        return importDetailInfo;
    }

    @BeforeEach
    public void initTest() {
        importDetailInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createImportDetailInfo() throws Exception {
        int databaseSizeBeforeCreate = importDetailInfoRepository.findAll().size();

        // Create the ImportDetailInfo
        restImportDetailInfoMockMvc.perform(post("/api/import-detail-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importDetailInfo)))
            .andExpect(status().isCreated());

        // Validate the ImportDetailInfo in the database
        List<ImportDetailInfo> importDetailInfoList = importDetailInfoRepository.findAll();
        assertThat(importDetailInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ImportDetailInfo testImportDetailInfo = importDetailInfoList.get(importDetailInfoList.size() - 1);
        assertThat(testImportDetailInfo.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testImportDetailInfo.getQuantityImport()).isEqualTo(DEFAULT_QUANTITY_IMPORT);
        assertThat(testImportDetailInfo.getImportDate()).isEqualTo(DEFAULT_IMPORT_DATE);
        assertThat(testImportDetailInfo.getPriceImport()).isEqualTo(DEFAULT_PRICE_IMPORT);
    }

    @Test
    @Transactional
    public void createImportDetailInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = importDetailInfoRepository.findAll().size();

        // Create the ImportDetailInfo with an existing ID
        importDetailInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImportDetailInfoMockMvc.perform(post("/api/import-detail-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importDetailInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ImportDetailInfo in the database
        List<ImportDetailInfo> importDetailInfoList = importDetailInfoRepository.findAll();
        assertThat(importDetailInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImportDetailInfos() throws Exception {
        // Initialize the database
        importDetailInfoRepository.saveAndFlush(importDetailInfo);

        // Get all the importDetailInfoList
        restImportDetailInfoMockMvc.perform(get("/api/import-detail-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(importDetailInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].quantityImport").value(hasItem(DEFAULT_QUANTITY_IMPORT)))
            .andExpect(jsonPath("$.[*].importDate").value(hasItem(DEFAULT_IMPORT_DATE.toString())))
            .andExpect(jsonPath("$.[*].priceImport").value(hasItem(DEFAULT_PRICE_IMPORT.intValue())));
    }
    
    @Test
    @Transactional
    public void getImportDetailInfo() throws Exception {
        // Initialize the database
        importDetailInfoRepository.saveAndFlush(importDetailInfo);

        // Get the importDetailInfo
        restImportDetailInfoMockMvc.perform(get("/api/import-detail-infos/{id}", importDetailInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(importDetailInfo.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.quantityImport").value(DEFAULT_QUANTITY_IMPORT))
            .andExpect(jsonPath("$.importDate").value(DEFAULT_IMPORT_DATE.toString()))
            .andExpect(jsonPath("$.priceImport").value(DEFAULT_PRICE_IMPORT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingImportDetailInfo() throws Exception {
        // Get the importDetailInfo
        restImportDetailInfoMockMvc.perform(get("/api/import-detail-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImportDetailInfo() throws Exception {
        // Initialize the database
        importDetailInfoService.save(importDetailInfo);

        int databaseSizeBeforeUpdate = importDetailInfoRepository.findAll().size();

        // Update the importDetailInfo
        ImportDetailInfo updatedImportDetailInfo = importDetailInfoRepository.findById(importDetailInfo.getId()).get();
        // Disconnect from session so that the updates on updatedImportDetailInfo are not directly saved in db
        em.detach(updatedImportDetailInfo);
        updatedImportDetailInfo
            .productName(UPDATED_PRODUCT_NAME)
            .quantityImport(UPDATED_QUANTITY_IMPORT)
            .importDate(UPDATED_IMPORT_DATE)
            .priceImport(UPDATED_PRICE_IMPORT);

        restImportDetailInfoMockMvc.perform(put("/api/import-detail-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImportDetailInfo)))
            .andExpect(status().isOk());

        // Validate the ImportDetailInfo in the database
        List<ImportDetailInfo> importDetailInfoList = importDetailInfoRepository.findAll();
        assertThat(importDetailInfoList).hasSize(databaseSizeBeforeUpdate);
        ImportDetailInfo testImportDetailInfo = importDetailInfoList.get(importDetailInfoList.size() - 1);
        assertThat(testImportDetailInfo.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testImportDetailInfo.getQuantityImport()).isEqualTo(UPDATED_QUANTITY_IMPORT);
        assertThat(testImportDetailInfo.getImportDate()).isEqualTo(UPDATED_IMPORT_DATE);
        assertThat(testImportDetailInfo.getPriceImport()).isEqualTo(UPDATED_PRICE_IMPORT);
    }

    @Test
    @Transactional
    public void updateNonExistingImportDetailInfo() throws Exception {
        int databaseSizeBeforeUpdate = importDetailInfoRepository.findAll().size();

        // Create the ImportDetailInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImportDetailInfoMockMvc.perform(put("/api/import-detail-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importDetailInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ImportDetailInfo in the database
        List<ImportDetailInfo> importDetailInfoList = importDetailInfoRepository.findAll();
        assertThat(importDetailInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImportDetailInfo() throws Exception {
        // Initialize the database
        importDetailInfoService.save(importDetailInfo);

        int databaseSizeBeforeDelete = importDetailInfoRepository.findAll().size();

        // Delete the importDetailInfo
        restImportDetailInfoMockMvc.perform(delete("/api/import-detail-infos/{id}", importDetailInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ImportDetailInfo> importDetailInfoList = importDetailInfoRepository.findAll();
        assertThat(importDetailInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.ncc.java.web.rest;

import com.ncc.java.ManagementWarehouseApp;
import com.ncc.java.domain.ImportInfo;
import com.ncc.java.repository.ImportInfoRepository;
import com.ncc.java.service.ImportInfoService;
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
import java.util.List;

import static com.ncc.java.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ImportInfoResource} REST controller.
 */
@SpringBootTest(classes = ManagementWarehouseApp.class)
public class ImportInfoResourceIT {

    private static final String DEFAULT_DELIVER_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_DELIVER_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);

    @Autowired
    private ImportInfoRepository importInfoRepository;

    @Autowired
    private ImportInfoService importInfoService;

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

    private MockMvc restImportInfoMockMvc;

    private ImportInfo importInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImportInfoResource importInfoResource = new ImportInfoResource(importInfoService);
        this.restImportInfoMockMvc = MockMvcBuilders.standaloneSetup(importInfoResource)
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
    public static ImportInfo createEntity(EntityManager em) {
        ImportInfo importInfo = new ImportInfo()
            .deliverPerson(DEFAULT_DELIVER_PERSON)
            .supplier(DEFAULT_SUPPLIER)
            .cost(DEFAULT_COST);
        return importInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImportInfo createUpdatedEntity(EntityManager em) {
        ImportInfo importInfo = new ImportInfo()
            .deliverPerson(UPDATED_DELIVER_PERSON)
            .supplier(UPDATED_SUPPLIER)
            .cost(UPDATED_COST);
        return importInfo;
    }

    @BeforeEach
    public void initTest() {
        importInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createImportInfo() throws Exception {
        int databaseSizeBeforeCreate = importInfoRepository.findAll().size();

        // Create the ImportInfo
        restImportInfoMockMvc.perform(post("/api/import-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importInfo)))
            .andExpect(status().isCreated());

        // Validate the ImportInfo in the database
        List<ImportInfo> importInfoList = importInfoRepository.findAll();
        assertThat(importInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ImportInfo testImportInfo = importInfoList.get(importInfoList.size() - 1);
        assertThat(testImportInfo.getDeliverPerson()).isEqualTo(DEFAULT_DELIVER_PERSON);
        assertThat(testImportInfo.getSupplier()).isEqualTo(DEFAULT_SUPPLIER);
        assertThat(testImportInfo.getCost()).isEqualTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    public void createImportInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = importInfoRepository.findAll().size();

        // Create the ImportInfo with an existing ID
        importInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImportInfoMockMvc.perform(post("/api/import-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ImportInfo in the database
        List<ImportInfo> importInfoList = importInfoRepository.findAll();
        assertThat(importInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImportInfos() throws Exception {
        // Initialize the database
        importInfoRepository.saveAndFlush(importInfo);

        // Get all the importInfoList
        restImportInfoMockMvc.perform(get("/api/import-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(importInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliverPerson").value(hasItem(DEFAULT_DELIVER_PERSON)))
            .andExpect(jsonPath("$.[*].supplier").value(hasItem(DEFAULT_SUPPLIER)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())));
    }
    
    @Test
    @Transactional
    public void getImportInfo() throws Exception {
        // Initialize the database
        importInfoRepository.saveAndFlush(importInfo);

        // Get the importInfo
        restImportInfoMockMvc.perform(get("/api/import-infos/{id}", importInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(importInfo.getId().intValue()))
            .andExpect(jsonPath("$.deliverPerson").value(DEFAULT_DELIVER_PERSON))
            .andExpect(jsonPath("$.supplier").value(DEFAULT_SUPPLIER))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingImportInfo() throws Exception {
        // Get the importInfo
        restImportInfoMockMvc.perform(get("/api/import-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImportInfo() throws Exception {
        // Initialize the database
        importInfoService.save(importInfo);

        int databaseSizeBeforeUpdate = importInfoRepository.findAll().size();

        // Update the importInfo
        ImportInfo updatedImportInfo = importInfoRepository.findById(importInfo.getId()).get();
        // Disconnect from session so that the updates on updatedImportInfo are not directly saved in db
        em.detach(updatedImportInfo);
        updatedImportInfo
            .deliverPerson(UPDATED_DELIVER_PERSON)
            .supplier(UPDATED_SUPPLIER)
            .cost(UPDATED_COST);

        restImportInfoMockMvc.perform(put("/api/import-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImportInfo)))
            .andExpect(status().isOk());

        // Validate the ImportInfo in the database
        List<ImportInfo> importInfoList = importInfoRepository.findAll();
        assertThat(importInfoList).hasSize(databaseSizeBeforeUpdate);
        ImportInfo testImportInfo = importInfoList.get(importInfoList.size() - 1);
        assertThat(testImportInfo.getDeliverPerson()).isEqualTo(UPDATED_DELIVER_PERSON);
        assertThat(testImportInfo.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
        assertThat(testImportInfo.getCost()).isEqualTo(UPDATED_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingImportInfo() throws Exception {
        int databaseSizeBeforeUpdate = importInfoRepository.findAll().size();

        // Create the ImportInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImportInfoMockMvc.perform(put("/api/import-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ImportInfo in the database
        List<ImportInfo> importInfoList = importInfoRepository.findAll();
        assertThat(importInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImportInfo() throws Exception {
        // Initialize the database
        importInfoService.save(importInfo);

        int databaseSizeBeforeDelete = importInfoRepository.findAll().size();

        // Delete the importInfo
        restImportInfoMockMvc.perform(delete("/api/import-infos/{id}", importInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ImportInfo> importInfoList = importInfoRepository.findAll();
        assertThat(importInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

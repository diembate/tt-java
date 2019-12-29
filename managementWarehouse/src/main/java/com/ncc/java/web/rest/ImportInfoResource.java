package com.ncc.java.web.rest;

import com.ncc.java.domain.ImportInfo;
import com.ncc.java.service.ImportInfoService;
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
 * REST controller for managing {@link com.ncc.java.domain.ImportInfo}.
 */
@RestController
@RequestMapping("/api")
public class ImportInfoResource {

    private final Logger log = LoggerFactory.getLogger(ImportInfoResource.class);

    private static final String ENTITY_NAME = "importInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImportInfoService importInfoService;

    public ImportInfoResource(ImportInfoService importInfoService) {
        this.importInfoService = importInfoService;
    }

    /**
     * {@code POST  /import-infos} : Create a new importInfo.
     *
     * @param importInfo the importInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new importInfo, or with status {@code 400 (Bad Request)} if the importInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/import-infos")
    public ResponseEntity<ImportInfo> createImportInfo(@RequestBody ImportInfo importInfo) throws URISyntaxException {
        log.debug("REST request to save ImportInfo : {}", importInfo);
        if (importInfo.getId() != null) {
            throw new BadRequestAlertException("A new importInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImportInfo result = importInfoService.save(importInfo);
        return ResponseEntity.created(new URI("/api/import-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /import-infos} : Updates an existing importInfo.
     *
     * @param importInfo the importInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated importInfo,
     * or with status {@code 400 (Bad Request)} if the importInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the importInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/import-infos")
    public ResponseEntity<ImportInfo> updateImportInfo(@RequestBody ImportInfo importInfo) throws URISyntaxException {
        log.debug("REST request to update ImportInfo : {}", importInfo);
        if (importInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImportInfo result = importInfoService.save(importInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, importInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /import-infos} : get all the importInfos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of importInfos in body.
     */
    @GetMapping("/import-infos")
    public List<ImportInfo> getAllImportInfos() {
        log.debug("REST request to get all ImportInfos");
        return importInfoService.findAll();
    }

    /**
     * {@code GET  /import-infos/:id} : get the "id" importInfo.
     *
     * @param id the id of the importInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the importInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/import-infos/{id}")
    public ResponseEntity<ImportInfo> getImportInfo(@PathVariable Long id) {
        log.debug("REST request to get ImportInfo : {}", id);
        Optional<ImportInfo> importInfo = importInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(importInfo);
    }

    /**
     * {@code DELETE  /import-infos/:id} : delete the "id" importInfo.
     *
     * @param id the id of the importInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/import-infos/{id}")
    public ResponseEntity<Void> deleteImportInfo(@PathVariable Long id) {
        log.debug("REST request to delete ImportInfo : {}", id);
        importInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

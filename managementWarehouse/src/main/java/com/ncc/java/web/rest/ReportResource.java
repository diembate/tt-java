package com.ncc.java.web.rest;

import com.ncc.java.service.ReportService;
import com.ncc.java.web.rest.errors.BadRequestAlertException;
import com.ncc.java.service.dto.ReportDTO;

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
 * REST controller for managing {@link com.ncc.java.domain.Report}.
 */
@RestController
@RequestMapping("/api")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    private final ReportService reportService;

    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * {@code GET  /reports} : get all the reports.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reports in body.
     */
    @GetMapping("/reports")
    public List<ReportDTO> getAllReports() {
        log.debug("REST request to get all Reports");
        return reportService.findAll();
    }

    /**
     * {@code GET  /reports/:id} : get the "id" report.
     *
     * @param id the id of the reportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reports/{id}")
    public ResponseEntity<ReportDTO> getReport(@PathVariable Long id) {
        log.debug("REST request to get Report : {}", id);
        Optional<ReportDTO> reportDTO = reportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportDTO);
    }
}

package com.ncc.java.service.impl;

import com.ncc.java.domain.Product;
import com.ncc.java.service.ImportDetailInfoService;
import com.ncc.java.domain.ImportDetailInfo;
import com.ncc.java.repository.ImportDetailInfoRepository;
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
 * Service Implementation for managing {@link ImportDetailInfo}.
 */
@Service
@Transactional
public class ImportDetailInfoServiceImpl implements ImportDetailInfoService {

    private final Logger log = LoggerFactory.getLogger(ImportDetailInfoServiceImpl.class);

    private final ImportDetailInfoRepository importDetailInfoRepository;

    public ImportDetailInfoServiceImpl(ImportDetailInfoRepository importDetailInfoRepository) {
        this.importDetailInfoRepository = importDetailInfoRepository;
    }

    /**
     * Save a importDetailInfo.
     *
     * @param importDetailInfo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ImportDetailInfo save(ImportDetailInfo importDetailInfo) {
        log.debug("Request to save ImportDetailInfo : {}", importDetailInfo);

        Date now = Calendar.getInstance().getTime();
        Instant instant = Instant.ofEpochMilli(now.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        importDetailInfo.setImportDate(localDate);


        return importDetailInfoRepository.save(importDetailInfo);
    }

    /**
     * Get all the importDetailInfos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImportDetailInfo> findAll() {
        log.debug("Request to get all ImportDetailInfos");
        return importDetailInfoRepository.findAll();
    }


    /**
     * Get one importDetailInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImportDetailInfo> findOne(Long id) {
        log.debug("Request to get ImportDetailInfo : {}", id);
        return importDetailInfoRepository.findById(id);
    }

    /**
     * Delete the importDetailInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImportDetailInfo : {}", id);
        importDetailInfoRepository.deleteById(id);
    }
}

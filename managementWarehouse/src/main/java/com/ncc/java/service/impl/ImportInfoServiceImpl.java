package com.ncc.java.service.impl;

import com.ncc.java.service.ImportInfoService;
import com.ncc.java.domain.ImportInfo;
import com.ncc.java.repository.ImportInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ImportInfo}.
 */
@Service
@Transactional
public class ImportInfoServiceImpl implements ImportInfoService {

    private final Logger log = LoggerFactory.getLogger(ImportInfoServiceImpl.class);

    private final ImportInfoRepository importInfoRepository;

    public ImportInfoServiceImpl(ImportInfoRepository importInfoRepository) {
        this.importInfoRepository = importInfoRepository;
    }

    /**
     * Save a importInfo.
     *
     * @param importInfo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ImportInfo save(ImportInfo importInfo) {
        log.debug("Request to save ImportInfo : {}", importInfo);
        return importInfoRepository.save(importInfo);
    }

    /**
     * Get all the importInfos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImportInfo> findAll() {
        log.debug("Request to get all ImportInfos");
        return importInfoRepository.findAll();
    }


    /**
     * Get one importInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImportInfo> findOne(Long id) {
        log.debug("Request to get ImportInfo : {}", id);
        return importInfoRepository.findById(id);
    }

    /**
     * Delete the importInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImportInfo : {}", id);
        importInfoRepository.deleteById(id);
    }
}

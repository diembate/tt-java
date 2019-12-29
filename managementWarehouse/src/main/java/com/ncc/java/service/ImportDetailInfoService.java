package com.ncc.java.service;

import com.ncc.java.domain.ImportDetailInfo;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ImportDetailInfo}.
 */
public interface ImportDetailInfoService {

    /**
     * Save a importDetailInfo.
     *
     * @param importDetailInfo the entity to save.
     * @return the persisted entity.
     */
    ImportDetailInfo save(ImportDetailInfo importDetailInfo);

    /**
     * Get all the importDetailInfos.
     *
     * @return the list of entities.
     */
    List<ImportDetailInfo> findAll();


    /**
     * Get the "id" importDetailInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImportDetailInfo> findOne(Long id);

    /**
     * Delete the "id" importDetailInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

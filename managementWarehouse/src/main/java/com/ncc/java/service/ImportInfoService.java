package com.ncc.java.service;

import com.ncc.java.domain.ImportInfo;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ImportInfo}.
 */
public interface ImportInfoService {

    /**
     * Save a importInfo.
     *
     * @param importInfo the entity to save.
     * @return the persisted entity.
     */
    ImportInfo save(ImportInfo importInfo);

    /**
     * Get all the importInfos.
     *
     * @return the list of entities.
     */
    List<ImportInfo> findAll();


    /**
     * Get the "id" importInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImportInfo> findOne(Long id);

    /**
     * Delete the "id" importInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

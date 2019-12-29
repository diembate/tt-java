package com.ncc.java.service.impl;

import com.ncc.java.service.BrandService;
import com.ncc.java.domain.Brand;
import com.ncc.java.repository.BrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Brand}.
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * Save a brand.
     *
     * @param brand the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Brand save(Brand brand) {
        log.debug("Request to save Brand : {}", brand);
        return brandRepository.save(brand);
    }

    /**
     * Get all the brands.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Brand> findAll() {
        log.debug("Request to get all Brands");
        return brandRepository.findAll();
    }


    /**
     * Get one brand by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Brand> findOne(Long id) {
        log.debug("Request to get Brand : {}", id);
        return brandRepository.findById(id);
    }

    /**
     * Delete the brand by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Brand : {}", id);
        brandRepository.deleteById(id);
    }
}

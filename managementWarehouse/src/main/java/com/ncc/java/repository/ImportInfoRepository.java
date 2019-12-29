package com.ncc.java.repository;

import com.ncc.java.domain.ImportInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ImportInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImportInfoRepository extends JpaRepository<ImportInfo, Long> {

}

package com.ncc.java.repository;

import com.ncc.java.domain.ImportDetailInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ImportDetailInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImportDetailInfoRepository extends JpaRepository<ImportDetailInfo, Long> {

}

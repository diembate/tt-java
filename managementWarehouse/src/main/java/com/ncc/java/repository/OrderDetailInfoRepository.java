package com.ncc.java.repository;

import com.ncc.java.domain.OrderDetailInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderDetailInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderDetailInfoRepository extends JpaRepository<OrderDetailInfo, Long> {

}

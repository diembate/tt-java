package com.ncc.java.repository;

import com.ncc.java.domain.OrderInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {

}

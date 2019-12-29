package com.ncc.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ncc.java.web.rest.TestUtil;

public class OrderDetailInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderDetailInfo.class);
        OrderDetailInfo orderDetailInfo1 = new OrderDetailInfo();
        orderDetailInfo1.setId(1L);
        OrderDetailInfo orderDetailInfo2 = new OrderDetailInfo();
        orderDetailInfo2.setId(orderDetailInfo1.getId());
        assertThat(orderDetailInfo1).isEqualTo(orderDetailInfo2);
        orderDetailInfo2.setId(2L);
        assertThat(orderDetailInfo1).isNotEqualTo(orderDetailInfo2);
        orderDetailInfo1.setId(null);
        assertThat(orderDetailInfo1).isNotEqualTo(orderDetailInfo2);
    }
}

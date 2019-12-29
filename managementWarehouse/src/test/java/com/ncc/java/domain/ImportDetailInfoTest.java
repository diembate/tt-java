package com.ncc.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ncc.java.web.rest.TestUtil;

public class ImportDetailInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImportDetailInfo.class);
        ImportDetailInfo importDetailInfo1 = new ImportDetailInfo();
        importDetailInfo1.setId(1L);
        ImportDetailInfo importDetailInfo2 = new ImportDetailInfo();
        importDetailInfo2.setId(importDetailInfo1.getId());
        assertThat(importDetailInfo1).isEqualTo(importDetailInfo2);
        importDetailInfo2.setId(2L);
        assertThat(importDetailInfo1).isNotEqualTo(importDetailInfo2);
        importDetailInfo1.setId(null);
        assertThat(importDetailInfo1).isNotEqualTo(importDetailInfo2);
    }
}

package com.ncc.java.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ncc.java.web.rest.TestUtil;

public class ImportInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImportInfo.class);
        ImportInfo importInfo1 = new ImportInfo();
        importInfo1.setId(1L);
        ImportInfo importInfo2 = new ImportInfo();
        importInfo2.setId(importInfo1.getId());
        assertThat(importInfo1).isEqualTo(importInfo2);
        importInfo2.setId(2L);
        assertThat(importInfo1).isNotEqualTo(importInfo2);
        importInfo1.setId(null);
        assertThat(importInfo1).isNotEqualTo(importInfo2);
    }
}

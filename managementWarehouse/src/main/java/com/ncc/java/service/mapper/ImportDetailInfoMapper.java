package com.ncc.java.service.mapper;

import com.ncc.java.domain.Authority;
import com.ncc.java.domain.ImportDetailInfo;
import com.ncc.java.domain.User;
import com.ncc.java.service.dto.ImportDetailInfoDTO;
import com.ncc.java.service.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ImportDetailInfoMapper {


    public List<ImportDetailInfoDTO> importDetailInfosToImportDetailInfoDTOs (List<ImportDetailInfo> importDetailInfos) {
        return importDetailInfos.stream()
            .filter(Objects::nonNull)
            .map(this::importDetailInfoToImportDetailInfoDTO)
            .collect(Collectors.toList());
    }

    public ImportDetailInfoDTO importDetailInfoToImportDetailInfoDTO(ImportDetailInfo importDetailInfo) {
        return new ImportDetailInfoDTO(importDetailInfo);
    }

    public List<ImportDetailInfo>importDetailInfoDTOsToImportDetailInfos(List<ImportDetailInfoDTO> importDetailInfoDTOs) {
        return importDetailInfoDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::importDetailInfoDTOToImportDetailInfo)
            .collect(Collectors.toList());
    }




    public ImportDetailInfo importDetailInfoDTOToImportDetailInfo(ImportDetailInfoDTO importDetailInfoDTO) {
        if (importDetailInfoDTO == null) {
            return null;
        } else {  ImportDetailInfo importDetailInfo = new ImportDetailInfo();
            importDetailInfo.setId(importDetailInfoDTO.getId());
            importDetailInfo.setQuantityImport(importDetailInfoDTO.getQuantityImport());
            importDetailInfo.setPriceImport(importDetailInfoDTO.getPriceImport());

            return importDetailInfo;
        }
    }


    public ImportDetailInfo importDetailInfoFromId(Long id) {
        if (id == null) {
            return null;
        }
        ImportDetailInfo importDetailInfo = new ImportDetailInfo();
        importDetailInfo.setId(id);
        return importDetailInfo;
    }




}

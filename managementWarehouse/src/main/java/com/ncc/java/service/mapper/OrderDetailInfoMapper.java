package com.ncc.java.service.mapper;
import com.ncc.java.domain.OrderDetailInfo;

import com.ncc.java.service.dto.OrderDetailInfoDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;




@Component
public class OrderDetailInfoMapper {
    public List<OrderDetailInfoDTO> orderDetailInfosToOrderDetailInfoDTOs (List<OrderDetailInfo> orderDetailInfos) {
        return orderDetailInfos.stream()
            .filter(Objects::nonNull)
            .map(this::orderDetailInfoToOrderDetailInfoDTO)
            .collect(Collectors.toList());
    }

    public OrderDetailInfoDTO orderDetailInfoToOrderDetailInfoDTO(OrderDetailInfo orderDetailInfo) {
        return new OrderDetailInfoDTO(orderDetailInfo);
    }

    public List<OrderDetailInfo>orderDetailInfoDTOsToOrderDetailInfos(List<OrderDetailInfoDTO> orderDetailInfoDTOs) {
        return orderDetailInfoDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::orderDetailInfoDTOToOrderDetailInfo)
            .collect(Collectors.toList());
    }




    public OrderDetailInfo orderDetailInfoDTOToOrderDetailInfo(OrderDetailInfoDTO orderDetailInfoDTO) {
        if (orderDetailInfoDTO == null) {
            return null;
        } else {  OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
            orderDetailInfo.setId(orderDetailInfoDTO.getId());
            orderDetailInfo.setQuantityOrder(orderDetailInfoDTO.getQuantityOrder());


            return orderDetailInfo;
        }
    }


    public OrderDetailInfo orderDetailInfoFromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
        orderDetailInfo.setId(id);
        return orderDetailInfo;
    }



}

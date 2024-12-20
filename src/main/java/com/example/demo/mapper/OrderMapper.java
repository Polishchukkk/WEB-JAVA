package com.example.demo.mapper;

import com.example.demo.domain.Order;
import com.example.demo.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {

    OrderDTO orderToOrderDTO(Order order);
    Order orderDTOToOrder(OrderDTO orderDTO);
}

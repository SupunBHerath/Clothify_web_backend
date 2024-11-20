package edu.icet.clothify_web_backend.service;

import edu.icet.clothify_web_backend.model.OrderDto;

import java.util.List;

public interface OrderService {

    String createOrders(OrderDto orderDto);
     OrderDto getOrdersById(int id );
    List<OrderDto> getAllOrders();
    List<OrderDto> getAllOrdersByCusId(int id);
}

package edu.icet.clothify_web_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.clothify_web_backend.entity.OrderEntity;
import edu.icet.clothify_web_backend.model.OrderDto;
import edu.icet.clothify_web_backend.repository.OrderJpaRepository;
import edu.icet.clothify_web_backend.repository.OrderRepository;
import edu.icet.clothify_web_backend.repository.ProductJdbcRepository;
import edu.icet.clothify_web_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductJdbcRepository productJdbcRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final ObjectMapper mapper;

    @Override
    public String createOrders(OrderDto orderDto) {
        OrderEntity save = orderRepository.save(mapper.convertValue(orderDto, OrderEntity.class));
        if (save.getId()>0) return "Place Order Successfully ... Order Id : "+save.getId();
        return "Error found..";
    }

    public OrderDto getOrdersById(int id ){
        Optional<OrderEntity> orderById = orderRepository.findById(id);
        System.out.println(orderById);
        return mapper.convertValue(orderById, OrderDto.class);
    }

    public List<OrderDto> getAllOrders(){
        Iterable<OrderEntity> allOrders = orderRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();
        allOrders.forEach(order->orderDtoList.add(mapper.convertValue(order, OrderDto.class)));
        return orderDtoList;
    }

    public List<OrderDto> getAllOrdersByCusId(int id){
        Iterable<OrderEntity> allOrders = orderJpaRepository.findOrdersByCustomerId(id);
        List<OrderDto> orderDtoList = new ArrayList<>();
        allOrders.forEach(order->orderDtoList.add(mapper.convertValue(order, OrderDto.class)));
        return orderDtoList;
    }
}

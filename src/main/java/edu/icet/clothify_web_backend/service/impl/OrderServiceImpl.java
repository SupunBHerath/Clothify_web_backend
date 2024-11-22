package edu.icet.clothify_web_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.clothify_web_backend.entity.OrderDetailsEntity;
import edu.icet.clothify_web_backend.entity.OrderEntity;
import edu.icet.clothify_web_backend.model.OrderDto;
import edu.icet.clothify_web_backend.repository.OrderJpaRepository;
import edu.icet.clothify_web_backend.repository.OrderRepository;
import edu.icet.clothify_web_backend.repository.ProductJdbcRepository;
import edu.icet.clothify_web_backend.service.OrderService;
import jakarta.transaction.Transactional;
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
    @Transactional //Safety: Wrapping the method with @Transactional ensures that all database operations are treated as a single transaction, avoiding partial updates if an error occurs.
    public String createOrders(OrderDto orderDto) {
        OrderEntity savedOrder = orderRepository.save(mapper.convertValue(orderDto, OrderEntity.class));
        if (savedOrder.getId() <= 0) {
            return "Error found..";
        }
        savedOrder.getOrderDetails().forEach(orderDetail -> {
            productJdbcRepository.updateSizeQty(
                    orderDetail.getQty(),
                    orderDetail.getProductId(),
                    orderDetail.getProductSize()
            );
        });
        return "Place Order Successfully ... Order Id : CS" + savedOrder.getId();
    }

    @Override
    public OrderDto getOrdersById(int id ){
        Optional<OrderEntity> orderById = orderRepository.findById(id);
        System.out.println(orderById);
        return mapper.convertValue(orderById, OrderDto.class);
    }
    @Override
    public List<OrderDto> getAllOrders(){
        Iterable<OrderEntity> allOrders = orderRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();
        allOrders.forEach(order->orderDtoList.add(mapper.convertValue(order, OrderDto.class)));
        return orderDtoList;
    }

    @Override
    public List<OrderDto> getAllOrdersByCusId(int id){
        Iterable<OrderEntity> allOrders = orderJpaRepository.findOrdersByCustomerId(id);
        List<OrderDto> orderDtoList = new ArrayList<>();
        allOrders.forEach(order->orderDtoList.add(mapper.convertValue(order, OrderDto.class)));
        return orderDtoList;
    }

    @Override
    @Transactional
    public  boolean updateStatusById(int id , String status){
        return orderJpaRepository.updateOrderStatus(id,status)>0;
    }
}

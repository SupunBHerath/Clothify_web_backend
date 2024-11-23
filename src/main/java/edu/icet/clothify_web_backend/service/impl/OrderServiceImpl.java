package edu.icet.clothify_web_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.clothify_web_backend.entity.OrderEntity;
import edu.icet.clothify_web_backend.exception.impl.ErrorException;
import edu.icet.clothify_web_backend.model.OrderCountsDTO;
import edu.icet.clothify_web_backend.model.OrderDto;
import edu.icet.clothify_web_backend.repository.OrderDetailJpaRepository;
import edu.icet.clothify_web_backend.repository.OrderJpaRepository;
import edu.icet.clothify_web_backend.repository.OrderRepository;
import edu.icet.clothify_web_backend.repository.ProductJdbcRepository;
import edu.icet.clothify_web_backend.service.OrderService;
import edu.icet.clothify_web_backend.util.impl.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailJpaRepository orderDetailJpaRepository;
    private final ProductJdbcRepository productJdbcRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final ObjectMapper mapper;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    //Safety: Wrapping the method with @Transactional ensures that all database operations are treated as a single transaction, avoiding partial updates if an error occurs.
    public String createOrders(OrderDto orderDto) {
        OrderEntity savedOrder = orderRepository.save(mapper.convertValue(orderDto, OrderEntity.class));
        if (savedOrder.getId() <= 0) {
            return "Order Not Create....";
        }
        savedOrder.getOrderDetails().forEach(orderDetail -> {
            productJdbcRepository.updateSizeQty(
                    (-orderDetail.getQty()),
                    orderDetail.getProductId(),
                    orderDetail.getProductSize()
            );
        });
        return "Place Order Successfully ... Order Id : CS" + savedOrder.getId();
    }

    @Override
    public OrderDto getOrdersById(int id) {
        Optional<OrderEntity> orderById = orderRepository.findById(id);
        System.out.println(orderById);
        return mapper.convertValue(orderById, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        Iterable<OrderEntity> allOrders = orderRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();
        allOrders.forEach(order -> orderDtoList.add(mapper.convertValue(order, OrderDto.class)));
        return orderDtoList;
    }

    @Override
    public List<OrderDto> getAllOrdersByCusId(int id) {
        Iterable<OrderEntity> allOrders = orderJpaRepository.findOrdersByCustomerId(id);
        List<OrderDto> orderDtoList = new ArrayList<>();
        allOrders.forEach(order -> orderDtoList.add(mapper.convertValue(order, OrderDto.class)));
        return orderDtoList;
    }

    @Override
    @Transactional
    public boolean updateStatusById(int id, String status) {
        return orderJpaRepository.updateOrderStatus(id, status) > 0;
    }

    @Transactional
    public boolean deleteOrderById(int id, String jwtToken) {
        if (!jwtUtil.validateTokenByToken(jwtToken)) throw new ErrorException("JWT Token Validate Fail ");
        Integer jwtTokenUserID = jwtUtil.extractUserId(jwtToken);
        List<OrderEntity> orderByOrderId = orderJpaRepository.findOrderByOrderId(id);
        if (orderByOrderId.isEmpty()) throw new ErrorException("Order Not Found ");
        if(!Objects.equals(orderByOrderId.get(0).getCusId(), jwtTokenUserID))  throw new ErrorException("You Can't Delete this Order");
//        if (!orderByOrderId.get(0).getStatus().equalsIgnoreCase("Processing") ||
//                !orderByOrderId.get(0).getStatus().equalsIgnoreCase("Rejected") ) return false;
        orderByOrderId.get(0).getOrderDetails().forEach(orderDetail -> {
            productJdbcRepository.updateSizeQty(
                    orderDetail.getQty(),
                    orderDetail.getProductId(),
                    orderDetail.getProductSize()
            );
        });
        orderDetailJpaRepository.deleteByOrderId(orderByOrderId.get(0).getId());
        int i = orderJpaRepository.deleteOrderById(id);
        return i>0;
    }

    public OrderCountsDTO getOrderCounts() {
        List<Object[]> result = orderJpaRepository.getOrderCounts();

        if (result == null || result.isEmpty()) {
            return new OrderCountsDTO();
        }
        Object[] row = result.get(0);
        if (row.length != 4) {
            System.out.println("Error: Result has incorrect length.");
            return new OrderCountsDTO();
        }
        OrderCountsDTO dto = new OrderCountsDTO();
        dto.setProcessing(((Number) row[0]).longValue());
        dto.setDelivering(((Number) row[1]).longValue());
        dto.setDelivered(((Number) row[2]).longValue());
        dto.setRejected((((Number) row[3]).longValue()));



        return dto;
    }

   public Double getTotalPrice(){
       return orderJpaRepository.getTotalPrice();
    }






}

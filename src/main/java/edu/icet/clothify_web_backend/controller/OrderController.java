package edu.icet.clothify_web_backend.controller;

import edu.icet.clothify_web_backend.model.OrderDto;
import edu.icet.clothify_web_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public String createOrders(@RequestBody OrderDto orderDto) {
        return orderService.createOrders(orderDto);
    }

    @GetMapping("/{id}")
    public OrderDto getOrdersById(@PathVariable int id) {
        return orderService.getOrdersById(id);
    }

    @GetMapping("cus/{id}")
    public List<OrderDto> getAllOrdersByCusId(@PathVariable int id) {
        return orderService.getAllOrdersByCusId(id);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }
}

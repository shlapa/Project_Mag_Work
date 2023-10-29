package com.amr.project.controller;


import com.amr.project.mapper.OrderMapper;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Status;
import com.amr.project.service.abstracts.BillService;
import com.amr.project.service.abstracts.OrderService;
import com.amr.project.service.abstracts.UserService;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class RestOrderController {
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final OrderService orderService;

    private final BillService billService;

    @Autowired
    public RestOrderController(OrderMapper orderMapper, UserService userService, OrderService orderService, BillService billService) {
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.orderService = orderService;
        this.billService = billService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrderOfCurrentUser (Principal principal) {
        User user = userService.findByUserName(principal.getName());
        return new ResponseEntity<>(orderMapper.toListOrderDto(user.getOrders()), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById (@PathVariable Long id) {
        return new ResponseEntity<>(orderMapper.orderToOrderDto(orderService.findById(id)), HttpStatus.FOUND);
    }

    @PutMapping("")
    public void createOrder (@RequestBody OrderDto orderDto) {
        Order order = orderMapper.orderDtoToOrder(orderDto);
        User user = userService.findById(orderDto.getUserId());
        order.setUser(user);
        order.setStatus(Status.START);
        orderService.persist(order);
    }

    @GetMapping("/pay/test")
    public BillResponse payTest () {
        return billService.connectionToQiwi(orderMapper.orderToOrderDto(orderService.findById(1L)));
    }

    @PostMapping("/pay")
    public String pay (@RequestParam  Long orderid) {
        Order order = orderService.findById(orderid);
        String payUrl = billService.connectionToQiwi(orderMapper.orderToOrderDto(orderService.findById(orderid))).getPayUrl();
        return payUrl;
    }

}

package com.example.training.controller;

import com.example.training.model.domain.OrderDto;
import com.example.training.service.PaymentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v0/payment")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/{accountId}")
    public OrderDto pay(@PathVariable Long accountId) {
        return service.pay(accountId);
    }
}

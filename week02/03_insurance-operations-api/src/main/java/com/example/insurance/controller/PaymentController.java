package com.example.insurance.controller;

import com.example.insurance.service.InsuranceOperationsService;
import com.example.insurance.model.ApiModels.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final InsuranceOperationsService s;
    public PaymentController(InsuranceOperationsService s) {
        this.s=s;
    }
    @GetMapping("/{id}")
    public Object get(@PathVariable long id) {
        return s.payment(id);
    }
    @PostMapping
    public Object create(@Valid @RequestBody PaymentRequest r) {
        return s.createPayment(r);
    }
    @GetMapping("/contracts/{contractId}")
    public Object byContract(@PathVariable long contractId) {
        return s.contractPayments(contractId);
    }
    @PostMapping("/{id}/refund")
    public Object refund(@PathVariable long id) {
        return s.refundPayment(id);
    }
}

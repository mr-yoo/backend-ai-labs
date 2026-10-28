package com.example.insurance.controller;

import com.example.insurance.service.InsuranceOperationsService;
import com.example.insurance.model.ApiModels.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {
    private final InsuranceOperationsService s;
    public ClaimController(InsuranceOperationsService s) {
        this.s = s;
    }
    @GetMapping
    public Object list() {
        return s.claims();
    }
    @GetMapping("/{id}")
    public Object get(@PathVariable long id) {
        return s.claim(id);
    }
    @PostMapping
    public Object create(@Valid @RequestBody ClaimRequest r) {
        return s.createClaim(r);
    }
    @GetMapping("/contracts/{contractId}")
    public Object byContract(@PathVariable long contractId) {
        return s.contractClaims(contractId);
    }
    @PostMapping("/{id}/review")
    public Object review(@PathVariable long id, @Valid @RequestBody ReviewRequest r) {
        return s.reviewClaim(id, r);
    }
}

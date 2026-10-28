package com.example.insurance.controller;

import com.example.insurance.service.InsuranceOperationsService;
import com.example.insurance.model.ApiModels.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final InsuranceOperationsService s;
    public CustomerController(InsuranceOperationsService s) {
        this.s = s;
    }
    @GetMapping
    public Object list() {
        return s.customers();
    }
    @GetMapping("/{id}")
    public Object get(@PathVariable long id) {
        return s.customer(id);
    }
    @PostMapping
    public Object create(@Valid @RequestBody CustomerRequest r) {
        return s.createCustomer(r);
    }
    @PutMapping("/{id}")
    public Object update(@PathVariable long id, @Valid @RequestBody CustomerRequest r) {
        return s.updateCustomer(id, r);
    }
    @GetMapping("/{id}/preferences")
    public Object preferences(@PathVariable long id) {
        return s.preferences(id);
    }
    @PutMapping("/{id}/preferences")
    public Object updatePreferences(@PathVariable long id, @Valid @RequestBody PreferenceRequest r) {
        return s.updatePreferences(id, r);
    }
}

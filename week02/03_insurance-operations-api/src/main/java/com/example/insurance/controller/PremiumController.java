package com.example.insurance.controller;

import com.example.insurance.service.InsuranceOperationsService;
import com.example.insurance.model.ApiModels.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/premiums")
public class PremiumController  {
    private final InsuranceOperationsService s;
    public PremiumController(InsuranceOperationsService s)  {
        this.s=s;
    }
    @GetMapping("/contracts/{id}/quote")
    public Object quote(@PathVariable long id, @RequestParam(defaultValue="0") int additionalDiscountPercent)  {
        return s.quote(id, additionalDiscountPercent);
    }
    @PostMapping("/quotes")
    public Object createQuote(@Valid @RequestBody QuoteRequest r)  {
        return s.quoteRequest(r);
    }
    @GetMapping("/discount-rules")
    public Object rules()  {
        return s.discountRules();
    }
    @GetMapping("/customers/{customerId}/summary")
    public Object summary(@PathVariable long customerId)  {
        return s.premiumSummary(customerId);
    }
    @GetMapping("/contracts/{id}/renewal-preview")
    public Object preview(@PathVariable long id)  {
        return s.previewRenewal(id);
    }
}

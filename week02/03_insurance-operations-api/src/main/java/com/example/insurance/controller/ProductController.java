package com.example.insurance.controller;
import com.example.insurance.service.InsuranceOperationsService;
import com.example.insurance.model.ApiModels.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/products")
public class ProductController {
 private final InsuranceOperationsService s;
 public ProductController(InsuranceOperationsService s){this.s=s;}
 @GetMapping public Object list(){return s.products();}
@GetMapping("/{code}") public Object get(@PathVariable String code){return s.productByCode(code);}
@PostMapping public Object create(@Valid @RequestBody ProductRequest r){return s.createProduct(r);}
@PatchMapping("/{code}/availability") public Object availability(@PathVariable String code,@RequestParam boolean enabled){return s.toggleProduct(code,enabled);}

}
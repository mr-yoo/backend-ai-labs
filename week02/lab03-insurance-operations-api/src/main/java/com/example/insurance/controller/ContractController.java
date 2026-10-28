package com.example.insurance.controller;
import com.example.insurance.service.InsuranceOperationsService;
import com.example.insurance.model.ApiModels.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/contracts")
public class ContractController {
 private final InsuranceOperationsService s;
 public ContractController(InsuranceOperationsService s){this.s=s;}
 @GetMapping public Object list(){return s.contracts();}
@GetMapping("/{id}") public Object get(@PathVariable long id){return s.contract(id);}
@PostMapping public Object create(@Valid @RequestBody ContractRequest r){return s.createContract(r);}
@GetMapping("/customer/{customerId}") public Object byCustomer(@PathVariable long customerId){return s.customerContracts(customerId);}
@PatchMapping("/{id}/status") public Object status(@PathVariable long id,@Valid @RequestBody StatusRequest r){return s.changeStatus(id,r.status());}
@PostMapping("/{id}/renew") public Object renew(@PathVariable long id){return s.renew(id);}
}
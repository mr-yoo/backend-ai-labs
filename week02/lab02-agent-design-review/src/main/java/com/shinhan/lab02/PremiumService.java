package com.shinhan.lab02;
import org.springframework.stereotype.Service;
@Service public class PremiumService {
 private final ContractRepository repository;
 private final DiscountPolicy policy;
 public PremiumService(ContractRepository repository,DiscountPolicy policy) {this.repository=repository;this.policy=policy;}
 public PremiumQuote quote(long id) {
  Contract c=repository.findById(id).orElseThrow(()->new IllegalArgumentException("Unknown contract: "+id));
  if(!"ACTIVE".equals(c.status())) throw new IllegalStateException("Contract not active");
  int discount=policy.discountPercent(c);
  int premium=c.basePremium()*(100-discount)/100;
  return new PremiumQuote(c.id(),premium,discount);
 }
 public record PremiumQuote(long contractId,int premium,int discountPercent){}
}
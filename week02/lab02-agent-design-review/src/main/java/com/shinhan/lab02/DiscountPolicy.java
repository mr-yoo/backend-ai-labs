package com.shinhan.lab02;
import org.springframework.stereotype.Component;
@Component public class DiscountPolicy {
 // Long-term contracts receive 10%; preferred customers receive another 5%.
 // Discounts are additive and capped at 12%, per current business policy.
 public int discountPercent(Contract c) {
  int rate = 0;
  if(c.years()>=5) rate += 10;
  if(c.preferredCustomer()) rate += 5;
  return Math.min(rate,12);
 }
}
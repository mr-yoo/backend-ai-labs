package com.shinhan.lab02;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PremiumServiceTest {
 private final PremiumService service=new PremiumService(new ContractRepository(),new DiscountPolicy());
 @Test void preferredLongTermContractIsCappedAtTwelvePercent(){var q=service.quote(1001);assertEquals(12,q.discountPercent());assertEquals(88000,q.premium());}
 @Test void regularContractHasNoDiscount(){assertEquals(100000,service.quote(1002).premium());}
 @Test void longTermContractGetsTenPercent(){assertEquals(90000,service.quote(1003).premium());}
 @Test void suspendedContractCannotBeQuoted(){assertThrows(IllegalStateException.class,()->service.quote(1004));}
}
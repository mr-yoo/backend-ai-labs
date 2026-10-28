package com.shinhan.lab02;
import java.util.*;
import org.springframework.stereotype.Repository;
@Repository public class ContractRepository {
 private final Map<Long,Contract> contracts = Map.of(
  1001L,new Contract(1001,1,100000,5,true,"ACTIVE"),
  1002L,new Contract(1002,2,100000,1,false,"ACTIVE"),
  1003L,new Contract(1003,3,100000,5,false,"ACTIVE"),
  1004L,new Contract(1004,4,100000,5,true,"SUSPENDED"));
 public Optional<Contract> findById(long id) { return Optional.ofNullable(contracts.get(id)); }
}
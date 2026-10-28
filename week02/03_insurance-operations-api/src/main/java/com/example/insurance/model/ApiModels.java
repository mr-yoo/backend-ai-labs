package com.example.insurance.model;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
public final class ApiModels {
 private ApiModels(){}
 public record CustomerRequest(@NotBlank String name,@Email String email,@NotBlank String phone) {}
 public record PreferenceRequest(boolean sms,boolean email,boolean paperless) {}
 public record ContractRequest(@NotNull Long customerId,@NotBlank String productCode,@Positive int termYears,@Positive BigDecimal basePremium) {}
 public record StatusRequest(@NotBlank String status) {}
 public record ProductRequest(@NotBlank String code,@NotBlank String name,@Positive BigDecimal basePremium,@Min(1) int minTermYears) {}
 public record QuoteRequest(@NotNull Long contractId,@Min(0) int additionalDiscountPercent) {}
 public record PaymentRequest(@NotNull Long contractId,@Positive BigDecimal amount,@NotBlank String channel) {}
 public record ClaimRequest(@NotNull Long contractId,@NotBlank String reason,@Positive BigDecimal amount) {}
 public record ReviewRequest(@NotBlank String decision,String note) {}
 public record DateRange(LocalDate from,LocalDate to) {}
 public record PageResult(List<?> items,int count) {}
}
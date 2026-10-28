package com.shinhan.lab02;
public record Contract(long id, long customerId, int basePremium, int years, boolean preferredCustomer, String status) {}
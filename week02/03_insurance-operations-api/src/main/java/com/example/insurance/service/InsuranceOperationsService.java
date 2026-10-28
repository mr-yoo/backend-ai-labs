package com.example.insurance.service;

import com.example.insurance.model.ApiModels.*;
import org.springframework.stereotype.Service;
import java.math.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class InsuranceOperationsService {
    private final AtomicLong ids=new AtomicLong(1000);
    private final Map<Long, Map<String, Object>> customers=new ConcurrentHashMap<>();
    private final Map<Long, Map<String, Object>> contracts=new ConcurrentHashMap<>();
    private final Map<String, Map<String, Object>> products=new ConcurrentHashMap<>();
    private final Map<Long, Map<String, Object>> payments=new ConcurrentHashMap<>();
    private final Map<Long, Map<String, Object>> claims=new ConcurrentHashMap<>();
    public InsuranceOperationsService() {
        products.put("LIFE-01", product("LIFE-01", "Term Life", new BigDecimal("120000"), 3));
        products.put("HEALTH-01", product("HEALTH-01", "Health Cover", new BigDecimal("85000"), 1));
        long c=next();
        customers.put(c, map("id", c, "name", "Sample Customer", "email", "sample@example.com", "phone", "010-0000-0000", "sms", true, "emailNotice", true, "paperless", false));
        long id=next();
        contracts.put(id, map("id", id, "customerId", c, "productCode", "LIFE-01", "termYears", 5, "basePremium", new BigDecimal("120000"), "status", "ACTIVE", "createdAt", LocalDate.now().toString()));
    }
    private long next() {
        return ids.incrementAndGet();
    }
    private Map<String, Object> map(Object... kv) {
        Map<String, Object> m=new LinkedHashMap<>();
        for(int i=0;i<kv.length;i+=2)m.put((String)kv[i], kv[i+1]);
        return m;
    }
    private Map<String, Object> product(String code, String name, BigDecimal base, int min) {
        return map("code", code, "name", name, "basePremium", base, "minTermYears", min, "enabled", true);
    }
    private Map<String, Object> require(Map<Long, Map<String, Object>> store, long id, String kind) {
        Map<String, Object> x=store.get(id);
        if(x==null)throw new NoSuchElementException(kind+" "+id+" not found");
        return x;
    }
    private Map<String, Object> requireProduct(String code) {
        Map<String, Object> p=products.get(code);
        if(p==null)throw new NoSuchElementException("Product "+code+" not found");
        return p;
    }
    public List<Map<String, Object>> customers() {
        return new ArrayList<>(customers.values());
    }
    public Map<String, Object> customer(long id) {
        return require(customers, id, "Customer");
    }
    public Map<String, Object> createCustomer(CustomerRequest r) {
        long id=next();
        var v=map("id", id, "name", r.name(), "email", r.email(), "phone", r.phone(), "sms", true, "emailNotice", true, "paperless", false);
        customers.put(id, v);
        return v;
    }
    public Map<String, Object> updateCustomer(long id, CustomerRequest r) {
        var v=customer(id);
        v.put("name", r.name());
        v.put("email", r.email());
        v.put("phone", r.phone());
        return v;
    }
    public Map<String, Object> preferences(long id) {
        var c=customer(id);
        return map("sms", c.get("sms"), "email", c.get("emailNotice"), "paperless", c.get("paperless"));
    }
    public Map<String, Object> updatePreferences(long id, PreferenceRequest r) {
        var c=customer(id);
        c.put("sms", r.sms());
        c.put("emailNotice", r.email());
        c.put("paperless", r.paperless());
        return preferences(id);
    }
    public List<Map<String, Object>> contracts() {
        return new ArrayList<>(contracts.values());
    }
    public Map<String, Object> contract(long id) {
        return require(contracts, id, "Contract");
    }
    public Map<String, Object> createContract(ContractRequest r) {
        customer(r.customerId());
        var p=requireProduct(r.productCode());
        if(!Boolean.TRUE.equals(p.get("enabled")))throw new IllegalArgumentException("Product disabled");
        if(r.termYears()<(int)p.get("minTermYears"))throw new IllegalArgumentException("Term below product minimum");
        long id=next();
        var v=map("id", id, "customerId", r.customerId(), "productCode", r.productCode(), "termYears", r.termYears(), "basePremium", r.basePremium(), "status", "PENDING", "createdAt", LocalDate.now().toString());
        contracts.put(id, v);
        return v;
    }
    public List<Map<String, Object>> customerContracts(long customerId) {
        customer(customerId);
        return contracts.values().stream().filter(c->Objects.equals(c.get("customerId"), customerId)).toList();
    }
    public Map<String, Object> changeStatus(long id, String status) {
        var c=contract(id);
        if(!Set.of("ACTIVE", "SUSPENDED", "CANCELLED").contains(status))throw new IllegalArgumentException("Unsupported contract status");
        if("CANCELLED".equals(c.get("status")))throw new IllegalArgumentException("Cancelled contract cannot transition");
        c.put("status", status);
        return c;
    }
    public Map<String, Object> renew(long id) {
        var c=contract(id);
        if(!"ACTIVE".equals(c.get("status")))throw new IllegalArgumentException("Only active contracts can renew");
        c.put("termYears", (int)c.get("termYears")+1);
        return c;
    }
    public List<Map<String, Object>> products() {
        return new ArrayList<>(products.values());
    }
    public Map<String, Object> productByCode(String code) {
        return requireProduct(code);
    }
    public Map<String, Object> createProduct(ProductRequest r) {
        if(products.containsKey(r.code()))throw new IllegalArgumentException("Product code exists");
        var p=product(r.code(), r.name(), r.basePremium(), r.minTermYears());
        products.put(r.code(), p);
        return p;
    }
    public Map<String, Object> toggleProduct(String code, boolean enabled) {
        var p=requireProduct(code);
        p.put("enabled", enabled);
        return p;
    }
    public List<Map<String, Object>> availableProducts() {
        return products.values().stream().filter(p->Boolean.TRUE.equals(p.get("enabled"))).toList();
    }
    public Map<String, Object> productContracts(String code) {
        requireProduct(code);
        return map("productCode", code, "contracts", contracts.values().stream().filter(c->code.equals(c.get("productCode"))).count());
    }
    public Map<String, Object> quote(long id, int extra) {
        var c=contract(id);
        if("CANCELLED".equals(c.get("status")))throw new IllegalArgumentException("Cancelled contract cannot be quoted");
        if(extra<0||extra>12)throw new IllegalArgumentException("Discount out of range");
        int tenure=(int)c.get("termYears")>=5?10:0;
        int discount=Math.min(12, tenure+extra);
        BigDecimal base=(BigDecimal)c.get("basePremium");
        BigDecimal finalAmount=base.multiply(BigDecimal.valueOf(100-discount)).divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
        return map("contractId", id, "basePremium", base, "discountPercent", discount, "payablePremium", finalAmount);
    }
    public Map<String, Object> quoteRequest(QuoteRequest r) {
        return quote(r.contractId(), r.additionalDiscountPercent());
    }
    public Map<String, Object> discountRules() {
        return map("longTermYears", 5, "longTermDiscountPercent", 10, "maxTotalDiscountPercent", 12, "rounding", "HALF_UP");
    }
    public Map<String, Object> premiumSummary(long customerId) {
        var list=customerContracts(customerId);
        BigDecimal sum=BigDecimal.ZERO;
        for(var c:list) {
            if(!"CANCELLED".equals(c.get("status")))sum=sum.add((BigDecimal)quote((long)c.get("id"), 0).get("payablePremium"));
        }
        return map("customerId", customerId, "contractCount", list.size(), "totalPremium", sum);
    }
    public Map<String, Object> previewRenewal(long id) {
        var c=contract(id);
        if(!"ACTIVE".equals(c.get("status")))throw new IllegalArgumentException("Contract not active");
        return map("contractId", id, "nextTermYears", (int)c.get("termYears")+1, "currentPremium", quote(id, 0).get("payablePremium"));
    }
    public List<Map<String, Object>> payments() {
        return new ArrayList<>(payments.values());
    }
    public Map<String, Object> payment(long id) {
        return require(payments, id, "Payment");
    }
    public Map<String, Object> createPayment(PaymentRequest r) {
        var c=contract(r.contractId());
        if(!"ACTIVE".equals(c.get("status")))throw new IllegalArgumentException("Contract not active");
        long id=next();
        var p=map("id", id, "contractId", r.contractId(), "amount", r.amount(), "channel", r.channel(), "status", "RECEIVED", "createdAt", LocalDate.now().toString());
        payments.put(id, p);
        return p;
    }
    public List<Map<String, Object>> contractPayments(long id) {
        contract(id);
        return payments.values().stream().filter(p->Objects.equals(p.get("contractId"), id)).toList();
    }
    public Map<String, Object> refundPayment(long id) {
        var p=payment(id);
        if(!"RECEIVED".equals(p.get("status")))throw new IllegalArgumentException("Payment not refundable");
        p.put("status", "REFUNDED");
        return p;
    }
    public Map<String, Object> paymentSummary() {
        BigDecimal total=payments.values().stream().filter(p->"RECEIVED".equals(p.get("status"))).map(p->(BigDecimal)p.get("amount")).reduce(BigDecimal.ZERO, BigDecimal::add);
        return map("receivedTotal", total, "paymentCount", payments.size());
    }
    public List<Map<String, Object>> claims() {
        return new ArrayList<>(claims.values());
    }
    public Map<String, Object> claim(long id) {
        return require(claims, id, "Claim");
    }
    public Map<String, Object> createClaim(ClaimRequest r) {
        var c=contract(r.contractId());
        if(!"ACTIVE".equals(c.get("status")))throw new IllegalArgumentException("Only active contracts support claims");
        long id=next();
        var x=map("id", id, "contractId", r.contractId(), "reason", r.reason(), "amount", r.amount(), "status", "SUBMITTED", "reviewNote", "", "createdAt", LocalDate.now().toString());
        claims.put(id, x);
        return x;
    }
    public List<Map<String, Object>> contractClaims(long id) {
        contract(id);
        return claims.values().stream().filter(c->Objects.equals(c.get("contractId"), id)).toList();
    }
    public Map<String, Object> reviewClaim(long id, ReviewRequest r) {
        var c=claim(id);
        if(!"SUBMITTED".equals(c.get("status")))throw new IllegalArgumentException("Claim already reviewed");
        if(!Set.of("APPROVED", "REJECTED").contains(r.decision()))throw new IllegalArgumentException("Unsupported decision");
        c.put("status", r.decision());
        c.put("reviewNote", r.note()==null?"":r.note());
        return c;
    }
    public Map<String, Object> claimSummary() {
        return map("total", claims.size(), "submitted", claims.values().stream().filter(c->"SUBMITTED".equals(c.get("status"))).count(), "approved", claims.values().stream().filter(c->"APPROVED".equals(c.get("status"))).count());
    }
}

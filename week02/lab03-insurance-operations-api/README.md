# Insurance Operations API

보험 계약, 고객, 청구, 납입, 갱신, 상품 정보를 관리하는 Spring Boot 기반 REST API 서비스입니다.

## Environment
- Java 21
- Spring Boot 3.3.5
- Maven
- In-memory data storage (no external database required)

## Run

```bash
mvn spring-boot:run
```

Default port: 8080.

## Test

```bash
mvn test
```

## API domains
- Customers: customer profiles and preferences
- Contracts: insurance contract lifecycle
- Products: insurance product catalog
- Premiums: premium calculation and quotation
- Payments: payment collection and reconciliation
- Claims: claim submission and review

The service uses in-memory storage and sample records for local execution. Restarting the application resets stored data.

## Source layout
- `controller`: REST endpoints and request validation
- `service`: application operations and business rules
- `model`: request and response models
- `error`: API error responses

For current endpoint details, refer to controller source and request/response models.

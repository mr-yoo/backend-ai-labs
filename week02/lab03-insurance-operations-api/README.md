# Insurance Operations API

보험 계약, 고객, 청구, 납입, 갱신, 상품 정보를 관리하는 Spring Boot 기반 REST API 서비스입니다.

## Environment
- Java 21
- Spring Boot 3.3.5
- Gradle 8.14.3 (Wrapper)
- JUnit 5
- In-memory data storage (no external database required)

## Run

아래 Gradle 명령은 저장소 루트에서 실행한다.

```bash
./gradlew :lab03-insurance-operations-api:bootRun
```

Default port: 8080.

## Test

```bash
./gradlew :lab03-insurance-operations-api:test
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

## OpenAPI / Swagger UI

애플리케이션을 실행한 후 아래 주소에서 API 명세를 확인하고 요청을 실행할 수 있다.

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

Codespaces에서는 전달된 포트 8080의 브라우저 주소 뒤에 위 경로를 붙인다.
각 실습은 독립 실행하며, 동시에 실행할 때는 서로 다른 서버 포트를 사용한다.

SpringDoc OpenAPI 2.6.0의 WebMVC UI starter를 사용한다.

## JUnit 5 테스트 환경

`spring-boot-starter-test`, `junit-platform-launcher`, `useJUnitPlatform()`을 사용한다.
저장소 루트에서 실행한다.

```bash
./gradlew :lab03-insurance-operations-api:test
```
테스트는 이 실습의 `src/test/java`에 작성한다.
테스트는 이 실습의 `src/test/java`에 작성한다.
보고서: `week02/lab03-insurance-operations-api/build/reports/tests/test/index.html`.

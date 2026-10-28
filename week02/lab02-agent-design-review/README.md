# Insurance Premium API

보험 계약의 기본 보험료와 계약 조건에 따른 할인 금액을 계산하는 Spring Boot REST API.

## 기술 스택
- Java 21
- Spring Boot 3.3.5
- Maven
- JUnit 5

현재 계약 데이터는 애플리케이션 내부의 메모리 저장소에서 제공한다. 별도의 데이터베이스 설정은 필요하지 않다.

## 주요 기능
- 계약 ID 기반 보험료 조회
- 계약 기간 및 고객 유형에 따른 할인 계산
- 계약 상태에 따른 보험료 조회 제한

## 실행
```bash
mvn test
mvn spring-boot:run
```

## API
### GET /api/contracts/{id}/premium

활성 계약의 보험료와 할인율을 조회한다.

```bash
curl http://localhost:8080/api/contracts/1001/premium
```

응답 예시:
```json
{
  "contractId": 1001,
  "premium": 88000,
  "discountPercent": 12
}
```

존재하지 않는 계약은 404, 비활성 계약은 400을 반환한다.

## 할인 정책
- 계약 기간이 5년 이상이면 10% 할인
- 우량 고객이면 5% 할인
- 할인율은 합산하되 최대 12%까지 적용
- 활성(ACTIVE) 계약만 보험료 조회 가능

## 프로젝트 구조
```text
src/main/java/com/shinhan/lab02/
  InsuranceApplication.java
  Contract.java
  ContractRepository.java
  DiscountPolicy.java
  PremiumService.java
  PremiumController.java
src/test/java/com/shinhan/lab02/
  PremiumServiceTest.java
```

## 개발 시 참고
- 현재 구현은 인메모리 계약 데이터 기반이다.
- 보험료는 정수 단위로 계산한다.
- 할인 정책 변경 시 기존 계약 유형에 대한 테스트를 확인한다.

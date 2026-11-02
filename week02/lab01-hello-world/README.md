# Week 02 / Lab 01 — Hello World

Spring Boot REST API를 실행하는 첫 번째 실습이다.

## 실행

아래 Gradle 명령은 저장소 루트에서 실행한다.

```bash
# 저장소 루트에서 실행
./gradlew :lab01-hello-world:bootRun
```

브라우저 또는 터미널에서 확인한다.

```bash
curl http://localhost:8080/hello
```

예상 응답: `Hello World!`

다음 실습은 `week02/lab02-...` 형태로 별도 디렉터리에 추가한다.

## OpenAPI / Swagger UI

애플리케이션을 실행한 후 아래 주소에서 API 명세를 확인하고 요청을 실행할 수 있다.

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

Codespaces에서는 전달된 포트 8080의 브라우저 주소 뒤에 위 경로를 붙인다.
각 실습은 독립 실행하며, 동시에 실행할 때는 서로 다른 서버 포트를 사용한다.

SpringDoc OpenAPI 2.8.6의 WebMVC UI starter를 사용한다.

## JUnit 5 테스트 환경

`spring-boot-starter-test`, `junit-platform-launcher`, `useJUnitPlatform()`을 사용한다.
저장소 루트에서 실행한다.

```bash
./gradlew :lab01-hello-world:test
```
테스트는 이 실습의 `src/test/java`에 작성한다.
테스트는 이 실습의 `src/test/java`에 작성한다.
보고서: `week02/lab01-hello-world/build/reports/tests/test/index.html`.

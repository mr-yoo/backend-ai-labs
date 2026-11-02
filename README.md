# Backend AI Labs

5주 과정 백엔드 AI 개발 실습 저장소. Java 21과 Gradle 8.14.3을 사용하며, 각 실습은 별도 Spring Boot 애플리케이션으로 실행한다.

## Week 02

- [Lab 01 — Spring Boot Hello World](week02/lab01-hello-world/README.md)
- [Lab 02 — Insurance Premium API](week02/lab02-agent-design-review/README.md)
- [Lab 03 — Insurance Operations API](week02/lab03-insurance-operations-api/README.md)

## GitHub Codespaces / Dev Container

1. 저장소의 **Code → Codespaces → Create codespace on main**을 선택한다.
2. 저장소 루트에서 아래 명령을 실행한다.

```bash
./gradlew :lab01-hello-world:bootRun
```

3. 포트 8080을 열고 `/hello` 또는 `/swagger-ui.html` 경로를 확인한다.

개발 환경은 `.devcontainer/devcontainer.json`과 `.devcontainer/Dockerfile`에서 관리한다.
Java 21, Gradle 8.14.3 및 VS Code의 Java·Gradle·JUnit 테스트 확장을 제공한다.
기존 Codespace에는 **Codespaces: Rebuild Container**, 로컬 Dev Container에는 **Dev Containers: Rebuild Container**로 적용한다.
Gradle Wrapper는 시스템 Gradle 설치 없이도 동일한 버전으로 빌드하며, 첫 실행 시 배포 파일을 내려받는다.

```bash
java -version
./gradlew --version
```

Windows 명령 프롬프트에서는 `./gradlew` 대신 `gradlew.bat`를 사용한다.

## 빌드와 실행

아래 명령은 모두 저장소 루트에서 실행한다.

```bash
# 전체 실습 빌드 및 테스트
./gradlew build

# 실습 하나만 빌드 또는 실행
./gradlew :lab03-insurance-operations-api:build
./gradlew :lab03-insurance-operations-api:bootRun
```

세 실습의 기본 포트는 8080이다. 동시에 실행할 때는 다른 포트를 지정한다.

```bash
./gradlew :lab02-agent-design-review:bootRun --args='--server.port=8081'
```

Codespaces에서는 추가 포트도 전달해야 한다. 실행 JAR는 각 실습의 `build/libs/`에 생성된다.

## SpringDoc OpenAPI

Gradle의 `implementation` 의존성으로 SpringDoc WebMVC UI starter를 사용한다.
앱 실행 시 컨트롤러를 기반으로 API 명세와 Swagger UI를 제공한다. 별도의 OpenAPI 생성용 Gradle 플러그인은 필요하지 않다.

| 실습 | Spring Boot | SpringDoc OpenAPI |
| --- | --- | --- |
| Lab 01 | 3.4.4 | 2.8.6 |
| Lab 02 / Lab 03 | 3.3.5 | 2.6.0 |

버전은 [SpringDoc 공식 호환표](https://springdoc.org/v2/#_what_is_the_compatibility_matrix_of_springdoc_openapi_with_spring_boot)를 따른다.

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

Codespaces에서는 전달된 포트의 브라우저 주소 뒤에 위 경로를 붙인다.

## JUnit 5

모든 실습에 `spring-boot-starter-test`, `junit-platform-launcher`와 Gradle의 `useJUnitPlatform()`을 설정했다.
JUnit 버전은 각 실습의 Spring Boot BOM으로 관리한다.
테스트 소스는 각 실습의 `src/test/java` 아래에 작성한다.

```bash
# 전체 테스트
./gradlew test

# 기존 보험료 계산 단위 테스트만 실행
./gradlew :lab02-agent-design-review:test --tests '*PremiumServiceTest'

```

HTML 테스트 보고서는 각 실습의 `build/reports/tests/test/index.html`에 생성된다.

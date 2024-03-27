# [스플랩] 백엔드 개발자 채용 코딩과제

> 지원자 : 강시혁 <br>
> 제출일자 : 24.03.27

<br>

## 과제명 : 게임센터 회원관리 시스템 개발

### 개발환경
- Intellij
- Java(JDK21), SpringBoot 3.2.3, Gradle
- SpringWeb, SpringDataJpa, SpringValidation, Webflux, Lombok, AOP
- Thymeleaf, Devtools
- MySQL, H2(테스트)
- JUnit, assertj
- Slack Message API

### 구현 상세내용

<br>

## GET STARTED

> 현재 JPA ddl 자동생성 옵션은 `Validate` 상태입니다.
> 
> 원할한 테스트를 위해서 Application 실행 시, (회원,카드,게임) 각각의 초기 데이터를 DB에 등록하고 있습니다.

### 1) Application 실행 전, 아래에 해당하는 환경변수의 값을 입력해주세요.

- LOCAL_DATABASE_USERNAME : MySQL 계정
- LOCAL_DATABASE_PASSWORD : MySQL 비밀번호
- SLACK_WEBHOOK_URL : Slack 메시지 전송을 위한 Webhook Url

### 2) 실행 Port

- 8080

<br>

## 회고

# [스플랩] 백엔드 개발자 채용 코딩과제

> 지원자 : 강시혁 <br>
> 제출일자 : 24.03.27

## 과제명 : 게임센터 회원관리 시스템 개발

---

### 1) 개발환경
- Intellij
- Java(JDK21), SpringBoot 3.2.3, Gradle
- SpringWeb, SpringDataJpa, SpringValidation, Webflux, Lombok, AOP
- Thymeleaf, Devtools
- MySQL, H2(테스트)
- JUnit, assertj
- Slack Message API

### 2) 구현 상세내용

> 요구사항 구현
- 회원 목록 페이지네이션 및 이름, 레벨 필터링 검색 구현
- 회원 등록 기능 및 유효성 검증 로직 구현
- 회원 상세 조회 기능 및 카드목록 조회 구현
- 회원 수정 기능 및 유효성 검증 로직 구현
- 회원 삭제 기능 및 회원이 소유한 게임목록 동시 삭제 구현
- 카드 등록 및 유효성 검증 로직 구현
- 카드 삭제 기능 구현
- 카드 등록, 삭제시 조건에 맞는 레벨 업데이트 구현
- Slack 메시지 전송 기능 구현 

> 추가 구현
- 글로벌 예외 처리 및 예외에 따른 에러 페이지 구현
- 로그 추적 AOP 구현

### 3) 진행과정

- 이슈관리 [[🔗Link]](https://github.com/Si-Hyeak-KANG/SpringGames-member_management_system-splab/issues?q=is%3Aissue+is%3Aclosed)
  - Github Projects를 통해 이슈를 관리하고, 과제를 수행했습니다.
  <img width="500" alt="image" src="https://github.com/Si-Hyeak-KANG/SpringGames-member_management_system-splab/assets/79829085/7e0bba13-5518-4b10-b154-ea9dbc049c77">

<br>

## GET STARTED

---

> 현재 JPA ddl 자동생성 옵션은 `Validate` 상태입니다.
> 
> 원활한 테스트를 위해서 Application 실행 시, (회원,카드,게임) 각각의 초기 데이터를 DB에 등록하고 있습니다.

### 1) Application 실행 전, 아래 **환경변수의 값을 입력**해주세요.

- LOCAL_DATABASE_USERNAME : MySQL 계정
- LOCAL_DATABASE_PASSWORD : MySQL 비밀번호
- SLACK_WEBHOOK_URL : Slack 메시지 전송을 위한 Webhook Url

### 2) 실행 Port

- 8080

<br>

## 회고

---

### 1) 고민한점

[시간복잡도를 고려한 Gold 레벨 조건 체크]

`2~3장의 합이 $100 이상일 때 Gold 레벨` 해당 요구사항에 맞게 로직을 구현하면서 고민했습니다. <br>
처음 요구사항을 접했을 때, 시간복잡도와 관련한 문제를 생각했습니다. 만약 임의의 2~3장의 합을 구하기 위해 완전 탐색을 한다면, 최대 O(N^2) 또는 O(N^3)까지 발생하게 되었습니다. <br>
결국 회원이 소유한 카드가 많아진다면, 레벨을 체크할 때마다 시간이 지연될 것이라고 예상했습니다. <br>

저는 이 문제를 해결하기 위해서, `금액을 기준으로 내림차순 정렬`을 적용했습니다. <br>
이로써 가장 큰 금액의 카드 2~3장의 합이 $100 이상인지만 체크하면 되기 때문에 불필요한 시간이 발생하지 않게 되었습니다.

[데이터 조회시 Query 개선을 위한 고민]

회원 상세 페이지로 이동할 때, Spring data jpa로 DB에서 회원 정보와 회원이 소유한 카드 목록을 조회합니다. 이때 두 개의 쿼리가 발생한다는 점에서 하나의 쿼리로 개선할 수 없을지 고민했습니다. <br>
이를 해결하기 위해서 Join 을 활요했습니다. 따라서 회원을 조회할 때 카드도 함께 조회하게 되는데 이때 카드와 또 다른 매핑 관계인 게임이 카드의 개수만큼 쿼리가 발생하는 것을 발견했습니다. 자세하게 알아보았을 때 해당 문제가 N+1 문제라는 것을 알았고, 이 문제를 해결하면서 하나의 쿼리로 기능을 수행할 수 있게 고민했습니다.<br>
저는 @EntityGraph 를 통해서 연관 테이블의 하위 테이블까지 join 할 수 있다는 것을 알게 되었고, 이를 활용하여 한번에 Query로 기능을 수행할 수 있게 했습니다. 

현재 삭제 기능 또는 위와 같이 연관 테이블로 인해서 여러 개의 쿼리가 발생하는 부분을 확인해서 추후 쿼리를 개선하는 목표를 계획하고 있습니다.

### 2) 배운점

[스프링 웹 MVC에 대한 배움]

평소에 Rest API 통신 방식으로 서버를 구축하는 경우가 많았습니다. <br> 
따라서 이번 과제로 View 템플릿인 Thymeleaf 와 서버를 직접 구현하면서, 스프링 웹 MVC 구조와 구현 방법에 대해서 배울 수 있었습니다. <br>
특히 디버깅을 진행하면서 Model에 바인딩 되는 데이터와 에러 정보를 보고, 데이터를 전달하는 방식을 알 수 있었습니다.

## Restful-API-Account-App
## 프로젝트 진행 개요
회사를 다니며 여러 프로젝트를 진행하였지만 클라이언트와 서버를 동시에 구현하였기 때문에
RESTful한 API를 구현하고자 하는 노력이 부족했단 생각이 들었다. 백엔드 개발자를 준비하고 있기에 RESTful한 서버 구현에 집중하며 좋은 설계와 코드, 그리고 꼼꼼한 테스트를 기반으로 프로젝트를 진행하려고 한다.
해당 프로젝트는 계좌 서비스를 모티브로 하였는데, 돈과 관련한 서비스를 구현함으로써 예민한 주제인만큼 안정성 있는 서버 개발을 위한 마음가짐을 갖추고자 해당 주제를 선택하였다.
## 사용 기술
- SpringBoot 2.7.6
- JDK 11
- JPA (Hibernate)
- Spring Security (JWT)
- H2 Database
## API 목록
### User API
- 회원가입 : POST /api/users
- 회원 목록 조회 : GET /api/users
- 패스워드 변경 : PUT /api/s/users/password

### Account API
- 계좌 등록 : POST /api/s/accounts
- 계좌 목록 조회 : GET /api/s/accounts
- 계좌 상세 조회 : GET /api/s/accounts/detail
- 계좌 삭제 : DELETE /api/s/accounts
- 입금 : POST /api/s/accounts/deposit
- 출금 : POST /api/s/accounts/withdraw

### Transaction API
- 거래 목록 조회 : GET /api/s/transactions/{accountNumber}
- 거래 목록 삭제 : DELETE /api/s/transactions/{transactionId}

## 프로젝트를 진행하며 학습한 내용
1. 스프링 시큐리티 세션쿠키 방식과 토큰 방식의 차이
2. Mockito를 이용한 Junit 테스트
3. 통합테스트의 멱등성 보장
4. 테스트 코드 작성 시 각각의 레이어에 따른 역할
4. ResponseDto 클래스를 정의하여 일관성 있는 API 응답
5. CustomExceptionHandler 클래스를 정의하여 일관성 있는 예외 핸들링
6. 스프링 AOP를 통한 Validation 체크 응답 처리

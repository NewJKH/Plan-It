ERD 다이어그램 

![image](https://github.com/user-attachments/assets/ea65c2b7-0779-418b-850a-5b3f9a1b6ad5)

API 명세서

https://documenter.getpostman.com/view/44589755/2sB2jAanWR

## 설계 의도 및 특징

- **DTO 분리 설계**  
  기능별로 `CreateRequest`, `UpdateRequest`, `DeleteRequest` 등 구분하여 단일 책임 원칙에 맞춤  
  → 유효성 검증 대상이 명확해지고, 응답 구조 관리가 쉬움

- **전역 예외 처리 구조**  
  커스텀 예외 + `@ExceptionHandler`를 활용한 일관된 예외 응답  
  → 클라이언트에서 예외 처리 예측 가능

- **Controller는 단순 위임만 수행**  
  모든 핵심 로직은 Service에서 처리 → 유지보수성과 테스트 효율 증가

- **JDBC 기반 명시적 쿼리 사용**  
  JPA 대신 직접 SQL을 제어하여, 퍼포먼스 최적화 및 로직 명확화에 집중

---

## 개발 중 고민했던 부분

- **DTO를 어느 수준까지 나눠야 할지**  
  초기엔 하나의 DTO로 처리했으나, 기능별로 나누는 것이 유효성 처리와 책임 분리에 유리하다는 판단

- **예외 처리를 어느 수준까지 세분화할지**  
  비밀번호 불일치, 권한 불일치 등을 별도 예외로 나눌지 고민했으며,  
  최종적으로는 클라이언트에 명확한 피드백을 주는 방향으로 커스텀 예외를 각각 정의
---

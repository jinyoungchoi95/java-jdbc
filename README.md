# 만들면서 배우는 스프링
[Next Step - 과정 소개](https://edu.nextstep.camp/c/4YUvqn9V)

## JDBC 라이브러리 구현하기

### 학습목표
- JDBC 라이브러리를 구현하는 경험을 함으로써 중복을 제거하는 연습을 한다.
- Transaction 적용을 위해 알아야할 개념을 이해한다.

### 시작 가이드
1. 이전 미션에서 진행한 코드를 사용하고 싶다면, 마이그레이션 작업을 진행합니다.
    - 학습 테스트는 강의 시간에 풀어봅시다.
2. LMS의 1단계 미션부터 진행합니다.

## 준비 사항
- 강의 시작 전에 docker를 설치해주세요.

## 학습 테스트
1. [ConnectionPool](study/src/test/java/connectionpool)
2. [Transaction](study/src/test/java/transaction)

## 1단계 - JDBC 라이브러리 구현하기
- 쿼리가 동작하도록 수정한다

- findByAccount
```mysql
select id, account, password, email from users where account = ? 
```
- findAll
  - resultSet에서 next로 나오는 모든 유저를 list로 반환한다.
```mysql
select id, account, password, email from users 
```
- update
```mysql
update users set account = ?, password = ?, email = ? where id = ?
```

- RowMapper
  - ResultSet을 파라미터로 받아 제네릭 선언된 타입의 객체를 반환하는 스펙을 가진다
  - 구현체는 ResultSet을 받아 타입에 맞는 객체를 파싱하여 반환한다
- JdbcTemplate
  - query
    - RowMapper와 sql문을 받아 실행한다
    - argument array를 받을 수 있으며 이를 받는 경우 PreparedStatement에 값을 할당해준다
    - 반환되는 값이 List인 경우 List에 모든 값을 담아 반환한다
    - 반환되는 값이 Object인 경우 객체에 담아서 내보내며 값이 없는 경우 예외가 발생한다.
  - update
    - sql과 파라미터를 받아 실행한다

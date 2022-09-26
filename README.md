# 스프링 DB 1편 - 데이터 접근 핵심 원리 (인프런 김영한)

## JDBC
자바에서 데이터베이스에 접속할 수 있도록 하는 자바 API
자바는 표준 인터페이스를 정의해두었고 각각의 db 회사에서 자신의 db에 맞도록 구현해서 라이브러리로 제공
이것을 JDBC 드라이버라고 함.

## SQL Mapper
애플리케이션 로직 -> SQL Mapper -> JDBC
장점
- JDBC를 편리하게 사용하도록 도와줌
- JDBC의 반복 코드를 제거해줌
단점
- 개발자가 SQL을 직접 작성해야함.
- 대표기술: 스프링 JDBC Template, MyBatis

## ORM 기술
객체를 관계형 데이터베이스 테이블과 매핑해주는 기술
JPA는 자바 진영의 ORM 표준 인터페이스, 이것을 구현한 것으로 하이버네이트와 이클립스 링크 등

둘다 JDBC를 이용하지만 어떻게 동작하는지 기본 원리는 알아놓자!

H2 데이터베이스 드라이버는 JDBC Connection 인터페이스를 구현한 org.h2.jdbc.jdbcConnection 구현체를 제공
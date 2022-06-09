# foogether
Spring Boot 기반 배달 웹 어플리케이션


## Commit Message Convention

**[Prefix] 기능 요약**

**기능 상세 설명**


의 형태로 작성하며 기능 상세 설명이 필요 없을 경우 생략한다.

---

*Prefix*
- [new] : 새로운 기능 추가 시
- [fix] : 기능 수정 시 (오류 X)
- [bug] : 오류 수정 시
- [remove] : 기능 삭제 시
- [test] : 테스트 케이스 추가 시
- [docs] : 문서 수정 시

---
## Domain 컬럼 및 생성 규칙

### Member
 - [id] : 5자 이상, 16자 이하
 - [password] : 8자 이상, 32자 이하, 영문 + 숫자 조합
 - [name] : 2자 이상
 - [email] : javax.validation 의 @Email 규칙을 따른다. (aaa@bbb)
 - [birthday] : 19xx ~ 20xx년도의 날짜만 허용
 - [phone_number] : '-'(하이픈)을 포함한 번호
 - [role] : 'OWNER', 'CUSTOMER', 'ADMIN', 'RIDER' 문자열만 허용
 - [use]

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

### 데이터베이스 스키마(지속 수정 예정)
![foogether](https://user-images.githubusercontent.com/56034014/176667048-8fe856ec-291e-4e96-a52d-6dfc6cd861c6.png)

---

### Member
 - [id] : 5자 이상, 16자 이하
 - [password] : 8자 이상, 32자 이하, 영문 + 숫자 조합
 - [name] : 2자 이상
 - [email] : javax.validation 의 @Email 규칙을 따른다. (aaa@bbb)
 - [birthday] : 19xx ~ 20xx년도의 날짜만 허용
 - [phone_number] : '-'(하이픈)을 포함한 번호
 - [role] : 'OWNER', 'CUSTOMER', 'ADMIN', 'RIDER' 문자열만 허용
 - [use]

### Restaurant
 - [id]
 - [name]
 - [type]
 - [business_number] : 000-00-0000 (숫자, 하이픈으로 구성됨)
 - [address]
 - [post_number] : 숫자 5자리
 - [description]
 - [use]
 - [member_id] : FK(Member 테이블의 id 컬럼 참조)

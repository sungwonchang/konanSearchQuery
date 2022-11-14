# konanSearchQuery
코난 검색엔진검색 쿼리 작성기(Rest API)기준

## 코난 검색엔진 쿼리 작성기 작성중
코난 검색엔진의 java library가 제공되고 있으나 오래 된 버전이며 String 하드코딩의 어려움으로 해당 쿼리 작성기를 시간날때 개발하기로...

개발 목표인 코드 형태는 다음과 같은 형태 가 될것 (where)절만 80% 문법 지원이 목표.

```
  maker.getWhere().equal("name", "chang").and().equal("age", 2);
```
결과는 다음과 같이 출력됩니다.

```
  name = 'chang' AND age = 2

```

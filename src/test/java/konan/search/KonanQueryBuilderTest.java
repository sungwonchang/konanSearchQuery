package konan.search;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.search.core.WhereQueryBuilder;
import konan.searchentity.TestCompany;
import konan.searchentity.TestMock2;

class KonanQueryBuilderTest {

	@Nested
	@DisplayName("begin/end 쿼리 테스트")
	class GenerateBeginEndQuery {
		@Test
		@DisplayName("begin  end 괄호 감싸기 테스트")
		void getQueryBeginEnd() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().begin().equals("name", "testTestCompany").and().equals("empcnt", 2).end();

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("( name = 'testTestCompany' AND empcnt = 2 )", kquery)
			);
		}
	}

	@Nested
	@DisplayName("And 쿼리 테스트")
	class GenerateAndQuery {
		@Test
		@DisplayName("and 쿼리 테스트1")
		void getQueryAnd1() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and().equals("empcnt", 2);

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND empcnt = 2", kquery)
			);
		}

		@Test
		@DisplayName("and 쿼리 테스트2")
		void getQueryAndForFieldValue() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("empcnt", 2);

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND empcnt = 2", kquery)
			);
		}
	}

	@Nested
	@DisplayName("OR 쿼리 테스트")
	class GenerateOrQuery {
		@Test
		@DisplayName("OR 쿼리 테스트1")
		void getQueryOr() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or().equals("empcnt", 2);

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR empcnt = 2", kquery)
			);
		}

		@Test
		@DisplayName("OR 쿼리 테스트2")
		void getQueryOrForFieldValue() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("empcnt", 2);

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR empcnt = 2", kquery)
			);
		}
	}

	@Nested
	@DisplayName("Equal 쿼리 테스트")
	class GenerateEqualQuery {
		@Test
		@DisplayName("Equal 쿼리 테스트1-문자형")
		void getQueryEqualString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany");

			String kquery = maker.getQuery();
			System.out.println(kquery);

			assertAll(
					() -> assertEquals("name = 'testTestCompany'", kquery)
			);
		}

		@Test
		@DisplayName("Equal 쿼리 테스트2-숫자형")
		void getQueryEqualInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("empcnt", 1);

			String kquery = maker.getQuery();
			System.out.println(kquery);

			assertAll(
					() -> assertEquals("empcnt = 1", kquery)
			);
		}
	}

	@Nested
	@DisplayName("IN 쿼리 테스트")
	class GenerateInQuery {

		@DisplayName("in 쿼리 작성테스트-없는 필드 검색시 실패")
		@Test
		void getQueryInNotFoundField() throws IllegalArgumentException {
			//given
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			//when, then
			WhereQueryBuilder<TestCompany> where = maker.getWhere();
			assertNotNull(where);
			IllegalArgumentException notFoundFieldException = assertThrows(IllegalArgumentException.class,
					() -> where.in("notFoundField", true, 1, 2, 3, 4));

			assertEquals("not found KonanColumn Annotaion", notFoundFieldException.getMessage());
		}

		@Test
		@DisplayName("in 쿼리 작성테스트")
		void getQueryIn() {
			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().in("codes", true, 1, 2, 3, 4);

			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().in("codes", true, "1", "2", "3", "4");

			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().in("codes", false, 1, 2, 3, 4);

			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().in("codes", false, "1", "2", "3", "4");

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerString.getQuery();
			String kquery3 = makerInt2.getQuery();
			String kquery4 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery2),
					() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery3),
					() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery4)
			);
		}

		@Test
		@DisplayName("inex 쿼리 작성테스트 슷자")
		void getQueryinEx1() {
			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().inEx("codes", true, 1, 2, 3, 4);
			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().inEx("codes", false, 1, 2, 3, 4);

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerInt2.getQuery();

			assertAll(
					() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

		@Test
		@DisplayName("inex 쿼리 작성테스트 문자")
		void getQueryinEx2() {
			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().inEx("codes", true, "1", "2", "3", "4");
			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().inEx("codes", false, "1", "2", "3", "4");

			String kquery1 = makerString.getQuery();
			String kquery2 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

		@Test
		@DisplayName("in 쿼리 작성테스트 문자 List")
		void getQueryInWithStringList() {
			var listString = List.of("1", "2", "3", "4");

			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().inWithStringList("codes", true, listString);
			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().inWithStringList("codes", false, listString);

			String kquery1 = makerString.getQuery();
			String kquery2 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery2)
			);

		}

		@Test
		@DisplayName("in 쿼리 작성테스트 숫자 List")
		void getQueryInWithIntegerList() {
			var listInt = List.of(1, 2, 3, 4);

			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().inWithIntegerList("codes", true, listInt);
			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().inWithIntegerList("codes", false, listInt);

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerInt2.getQuery();

			assertAll(
					() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

	}

	@Nested
	@DisplayName("And IN 쿼리 테스트-특정쿼리 뒤에 And조건으로 바로 in 붙을때")
	class GenerateAndInQuery {
		@Test
		@DisplayName("in 쿼리 작성테스트")
		void getQueryAndIn() {
			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().equals("name", "TestCompany").andIn("codes", true, 1, 2, 3, 4);

			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().equals("name", "TestCompany").andIn("codes", true, "1", "2", "3", "4");

			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().equals("name", "TestCompany").andIn("codes", false, 1, 2, 3, 4);

			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().equals("name", "TestCompany").andIn("codes", false, "1", "2", "3", "4");

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerString.getQuery();
			String kquery3 = makerInt2.getQuery();
			String kquery4 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' AND codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' AND codes IN { '1', '2', '3', '4' }", kquery2),
					() -> assertEquals("name = 'TestCompany' AND codes IN { 1, 2, 3, 4 }", kquery3),
					() -> assertEquals("name = 'TestCompany' AND codes IN { 1, 2, 3, 4 }", kquery4)
			);
		}

		@Test
		@DisplayName("andIn 쿼리 작성테스트 슷자")
		void getQueryAndInForInteger() {
			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().equals("name", "TestCompany").andIn("codes", true, 1, 2, 3, 4);
			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().equals("name", "TestCompany").andIn("codes", false, 1, 2, 3, 4);

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerInt2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' AND codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' AND codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

		@Test
		@DisplayName("andIn 쿼리 작성테스트 문자")
		void getQueryAndInExForString() {
			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().equals("name", "TestCompany").andIn("codes", true, "1", "2", "3", "4");
			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().equals("name", "TestCompany").andIn("codes", false, "1", "2", "3", "4");

			String kquery1 = makerString.getQuery();
			String kquery2 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' AND codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' AND codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

		@Test
		@DisplayName("andIn 쿼리 작성테스트 문자 List")
		void getQueryAndInWithStringList() {
			var listString = List.of("1", "2", "3", "4");

			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().equals("name", "TestCompany").andInWithStringList("codes", true, listString);
			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().equals("name", "TestCompany").andInWithStringList("codes", false, listString);

			String kquery1 = makerString.getQuery();
			String kquery2 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' AND codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' AND codes IN { 1, 2, 3, 4 }", kquery2)
			);

		}

		@Test
		@DisplayName("andIn 쿼리 작성테스트 숫자 List")
		void getQueryInWithIntegerList() {
			var listInt = List.of(1, 2, 3, 4);

			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().equals("name", "TestCompany").andInWithIntegerList("codes", true, listInt);
			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().equals("name", "TestCompany").andInWithIntegerList("codes", false, listInt);

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerInt2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' AND codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' AND codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

	}

	@Nested
	class KonanMatchCheckerInterFaceTest {

		@Test
		@DisplayName("정의 되지 않는 필드 검색")
		void notExistFieldCheck_notFoundField() {
			//given
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			//when
			WhereQueryBuilder<TestCompany> where = maker.getWhere();

			//then
			assertNotNull(where);
			assertThrows(IllegalArgumentException.class,
					() -> where.notExistFieldCheck("notFoundField"),
					"not found KonanColumn Annotaion");
		}

		@Test
		@DisplayName("정의 되어진 필드 검색")
		void notExistFieldCheck_FoundField() {
			//given
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			//when
			WhereQueryBuilder<TestCompany> where = maker.getWhere();

			//then
			assertNotNull(where);
			assertDoesNotThrow(() -> where.notExistFieldCheck("name"),
					"not found KonanColumn Annotaion");
		}

		@Test
		@DisplayName("정의 되어진 필드 검색")
		void notExistFieldCheck_NotFoundTable() {
			//given @KonanTable만 있고  column정보가 없는 클래스
			var maker = new KonanQueryBuilder<TestMock2>(TestMock2.class);

			//when
			WhereQueryBuilder<TestMock2> where = maker.getWhere();

			//then
			assertNotNull(where);
			assertThrows(IllegalArgumentException.class,
					() -> where.notExistFieldCheck("name"), //존재 하는 필드로 검색하더라도
					"Generic Type allColumns not found KonanColumn Annotaion");
		}

		@Test
		@DisplayName("코난 검색 쿼리 조회")
		void getKonanQuery() {

		}

		@Test
		@DisplayName("괄호 갯수 체크-수동으로 괄호를 하는 경우 괄호 체크")
		void isNotBracketPair() {
			//given
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			//when
			WhereQueryBuilder<TestCompany> fault = maker.getWhere().equals("name", "test")
					.and().begin().equals("empcnt", 1);

			//then
			assertThrows(IllegalStateException.class,
					fault::getKonanQuery,
					"괄호의 열고 닫고 갯수가 불일치 합니다.");

		}

		@Test
		@DisplayName("작성중인 쿼리 삭제")
		void clear() {
			//given
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			//when
			WhereQueryBuilder<TestCompany> query = maker.getWhere().equals("name", "test");

			//then
			String konanQuery = query.getKonanQuery();
			assertNotNull(konanQuery);
			assertFalse(konanQuery.isBlank());
			//then2
			query.clear();
			String konanQuery1 = query.getKonanQuery();
			assertTrue(konanQuery1.isBlank());

		}
	}

}

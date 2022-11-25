package konan.search.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import konan.searchentity.TestCompany;
import konan.searchentity.TestMock2;

class WhereQueryBuilderTest {

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

package konan.search.core.condition;

import static konan.search.matcher.KonanMatcherFactory.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import konan.search.core.KonanQueryBuilder;
import konan.searchentity.TestCompany;

public class WhereQueryOrMatcherTest {
	@Nested
	class OrMatcherEqualTest {
		@Test
		@DisplayName("or eq 쿼리 테스트 or Matcher String")
		void getQueryOrForEqualMatchForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("addr", eq("서울시"));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR addr = '서울시'", kquery)
			);
		}

		@Test
		@DisplayName("or eq 쿼리 테스트 or Matcher Integer")
		void getQueryOrForEqualMatchForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("empcnt", eq(50));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR empcnt = 50", kquery)
			);
		}
	}

	@Nested
	class OrMatcherGreaterThanTest {

		@Test
		@DisplayName("or greaterThan 쿼리 테스트 or Matcher String")
		void getQueryOrForGreaterThanMatchForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("addr", gt("서울시"));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR addr > '서울시'", kquery)
			);
		}

		@Test
		@DisplayName("or greaterThan 쿼리 테스트 or Matcher Integer")
		void getQueryOrForGreaterThanMatchForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("empcnt", gt(50));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR empcnt > 50", kquery)
			);
		}
	}

	@Nested
	class OrMatcherGreaterEqualThanTest {

		@Test
		@DisplayName("or greaterEqualThan 쿼리 테스트 or Matcher String")
		void getQueryOrForGreaterEqualThanMatchForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("addr", gte("서울시"));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR addr >= '서울시'", kquery)
			);
		}

		@Test
		@DisplayName("or greaterEqualThan 쿼리 테스트 or Matcher Integer")
		void getQueryOrForGreaterThanMatchForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("empcnt", gte(50));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR empcnt >= 50", kquery)
			);
		}
	}

	@Nested
	class OrMatcherLessTest {
		@Test
		@DisplayName("or lessthan 쿼리 테스트 or Matcher String")
		void getQueryOrForGreaterEqualThanMatchForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("addr", lt("서울시"));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR addr < '서울시'", kquery)
			);
		}

		@Test
		@DisplayName("or lessthan 쿼리 테스트 or Matcher Integer")
		void getQueryOrForGreaterThanMatchForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("empcnt", lt(50));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR empcnt < 50", kquery)
			);
		}
	}

	@Nested
	class OrMatcherLessEqualThanTest {
		@Test
		@DisplayName("or LessEqualThan 쿼리 테스트 or Matcher String")
		void getQueryOrForGreaterEqualThanMatchForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("addr", lte("서울시"));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR addr <= '서울시'", kquery)
			);
		}

		@Test
		@DisplayName("or LessEqualThan 쿼리 테스트 or Matcher Integer")
		void getQueryOrForGreaterThanMatchForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("empcnt", lte(50));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR empcnt <= 50", kquery)
			);
		}
	}

	@Nested
	class OrMatcherLikeTest {

		@ParameterizedTest
		@CsvSource({"서울시", "서울시*", "*서울시*"})
		@DisplayName("or LessEqualThan 쿼리 테스트 or Matcher String 단어")
		void getQueryOrForGreaterThanMatchForStringLike(String keyword) {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("addr", like(keyword));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR addr like '" + keyword + "'", kquery)
			);
		}
	}
}

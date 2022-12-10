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

public class WhereQueryAndMatcherTest {
	@Nested
	class AndMatcherEqualTest {
		@Test
		@DisplayName("and eq 쿼리 테스트 and Matcher String")
		void getQueryAndForEqualMatchForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("addr", eq("서울시"));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND addr = '서울시'", kquery)
			);
		}

		@Test
		@DisplayName("and eq 쿼리 테스트 and Matcher Integer")
		void getQueryAndForEqualMatchForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("empcnt", eq(50));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND empcnt = 50", kquery)
			);
		}
	}

	@Nested
	class AndMatcherGreaterThanTest {

		@Test
		@DisplayName("and greaterThan 쿼리 테스트 and Matcher String")
		void getQueryAndForGreaterThanMatchForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("addr", gt("서울시"));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND addr > '서울시'", kquery)
			);
		}

		@Test
		@DisplayName("and greaterThan 쿼리 테스트 and Matcher Integer")
		void getQueryAndForGreaterThanMatchForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("empcnt", gt(50));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND empcnt > 50", kquery)
			);
		}
	}

	@Nested
	class AndMatcherGreaterEqualThanTest {

		@Test
		@DisplayName("and greaterEqualThan 쿼리 테스트 and Matcher String")
		void getQueryAndForGreaterEqualThanMatchForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("addr", gte("서울시"));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND addr >= '서울시'", kquery)
			);
		}

		@Test
		@DisplayName("and greaterEqualThan 쿼리 테스트 and Matcher Integer")
		void getQueryAndForGreaterThanMatchForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("empcnt", gte(50));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND empcnt >= 50", kquery)
			);
		}
	}

	@Nested
	class AndMatcherLessTest {
		@Test
		@DisplayName("and lessthan 쿼리 테스트 and Matcher String")
		void getQueryAndForGreaterEqualThanMatchForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("addr", lt("서울시"));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND addr < '서울시'", kquery)
			);
		}

		@Test
		@DisplayName("and lessthan 쿼리 테스트 and Matcher Integer")
		void getQueryAndForGreaterThanMatchForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("empcnt", lt(50));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND empcnt < 50", kquery)
			);
		}
	}

	@Nested
	class AndMatcherLessEqualThanTest {
		@Test
		@DisplayName("and LessEqualThan 쿼리 테스트 and Matcher String")
		void getQueryAndForGreaterEqualThanMatchForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("addr", lte("서울시"));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND addr <= '서울시'", kquery)
			);
		}

		@Test
		@DisplayName("and LessEqualThan 쿼리 테스트 and Matcher Integer")
		void getQueryAndForGreaterThanMatchForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("empcnt", lte(50));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND empcnt <= 50", kquery)
			);
		}
	}

	@Nested
	class AndMatcherLikeTest {

		@ParameterizedTest
		@CsvSource({"서울시", "서울시*", "*서울시*"})
		@DisplayName("and LessEqualThan 쿼리 테스트 and Matcher String 단어")
		void getQueryAndForGreaterThanMatchForStringLike(String keyword) {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("addr", like(keyword));

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND addr like '" + keyword + "'", kquery)
			);
		}
	}
}

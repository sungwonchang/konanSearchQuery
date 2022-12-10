package konan.search.core.condition;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.searchentity.TestCompany;

public class WhereQueryAndTest {
	@Nested
	@DisplayName("And 쿼리 테스트")
	class GenerateAndQuery {
		@Test
		@DisplayName("and 쿼리 테스트 and only")
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
		@DisplayName("and 쿼리 테스트 and int")
		void getQueryAndForFieldValueInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("empcnt", 2);

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND empcnt = 2", kquery)
			);
		}

		@Test
		@DisplayName("and 쿼리 테스트 and String")
		void getQueryAndForFieldValueString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").and("addr", "서울시");

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' AND addr = '서울시'", kquery)
			);
		}
	}
}

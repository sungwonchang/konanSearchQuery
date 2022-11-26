package konan.search.core.condition;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.searchentity.TestCompany;

public class WhereQueryOrTest {
	@Nested
	@DisplayName("OR 쿼리 테스트")
	class GenerateOrQuery {
		@Test
		@DisplayName("OR 쿼리 테스트 or only")
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
		@DisplayName("OR 쿼리 테스트 Integer")
		void getQueryOrForFieldValueForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("empcnt", 2);

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR empcnt = 2", kquery)
			);
		}

		@Test
		@DisplayName("OR 쿼리 테스트 String")
		void getQueryOrForFieldValueForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().equals("name", "testTestCompany").or("addr", "서울시");

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name = 'testTestCompany' OR addr = '서울시'", kquery)
			);
		}
	}
}

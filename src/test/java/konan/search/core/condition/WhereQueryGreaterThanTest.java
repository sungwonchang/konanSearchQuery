package konan.search.core.condition;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.searchentity.TestCompany;

public class WhereQueryGreaterThanTest {
	@Nested
	class GreaterThanQueryTest {

		@DisplayName("GreaterThan 쿼리 작성테스트")
		@Test
		void getQueryGreaterThanForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().greaterThan("name", "서울시");

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name > '서울시'", kquery)
			);
		}

		@DisplayName("GreaterThan 쿼리 작성테스트")
		@Test
		void getQueryGreaterThanForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().greaterThan("empcnt", 30);

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("empcnt > 30", kquery)
			);
		}

	}
}

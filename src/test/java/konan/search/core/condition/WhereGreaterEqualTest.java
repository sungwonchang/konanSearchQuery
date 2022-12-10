package konan.search.core.condition;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.searchentity.TestCompany;

public class WhereGreaterEqualTest {
	@Nested
	@DisplayName("GreaterEqual 쿼리 테스트")
	class GreaterEqualQueryTest {

		@DisplayName("GreaterEqual 쿼리 작성테스트")
		@Test
		void getQueryGreaterEqualForString() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().greaterEqual("name", "서울시");

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name >= '서울시'", kquery)
			);
		}

		@DisplayName("GreaterEqual 쿼리 작성테스트")
		@Test
		void getQueryGreaterEqualForInteger() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().greaterEqual("empcnt", 30);

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("empcnt >= 30", kquery)
			);
		}

	}
}

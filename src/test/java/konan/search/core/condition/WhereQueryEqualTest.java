package konan.search.core.condition;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.searchentity.TestCompany;

public class WhereQueryEqualTest {
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
}

package konan.search.core.condition;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.searchentity.TestCompany;

public class WhereQueryAndNotTest {
	@Nested
	class GenerateAndNotQuery {

		@Test
		@DisplayName("And NOT 단독 사용 쿼리 테스트")
		void getAndNotQuery() {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			maker.getWhere().equals("name", "TestCompany").andNot().equals("name", "untitle");

			String kquery = maker.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' ANDNOT name = 'untitle'", kquery)
			);

		}

		@Test
		@DisplayName("And NOT Equal Test Interger or String")
		void getAndNotQuery2() {
			var maker1 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			maker1.getWhere().equals("name", "TestCompany").andNot("name", "untitle");
			var maker2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			maker2.getWhere().equals("name", "TestCompany").andNot("empcnt", 0);

			String kquery1 = maker1.getQuery();
			String kquery2 = maker2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' ANDNOT name = 'untitle'", kquery1),
					() -> assertEquals("name = 'TestCompany' ANDNOT empcnt = 0", kquery2)
			);

		}

	}
}

package konan.search;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.searchentity.Company;

class KonanQueryBuilderTest {

	@Test
	void getQueryAnd1() {
		var maker = new KonanQueryBuilder<Company>(Company.class);

		maker.getWhere().equals("name", "testCompany").and().equals("empcnt", 2);

		String kquery = maker.getQuery();
		System.out.println(kquery);
		assertAll(
				() -> assertEquals("name = 'testCompany' AND empcnt = 2", kquery)
		);
	}

	@Test
	void getQueryEqualString() {
		var maker = new KonanQueryBuilder<Company>(Company.class);

		maker.getWhere().equals("name", "testCompany");

		String kquery = maker.getQuery();
		System.out.println(kquery);

		assertAll(
				() -> assertEquals("name = 'testCompany'", kquery)
		);
	}

	@Test
	void getQueryEqualInteger() {
		var maker = new KonanQueryBuilder<Company>(Company.class);

		maker.getWhere().equals("empcnt", 1);

		String kquery = maker.getQuery();
		System.out.println(kquery);

		assertAll(
				() -> assertEquals("empcnt = 1", kquery)
		);
	}

}

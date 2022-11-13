package konan.search;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.searchentity.Company;

class KonanQueryBuilderTest {

	@Test
	void getQueryAnd1() {
		var maker = new KonanQueryBuilder<Company>(Company.class);

		maker.getWhere().equal("name", "chang").and().equal("age", 2);

		String kquery = maker.getQuery();
		System.out.println(kquery);
		assertAll(
				() -> assertEquals(kquery, "name = 'chang' AND age = 2")
		);
	}

	@Test
	void getQueryEqualString() {
		var maker = new KonanQueryBuilder<Company>(Company.class);

		maker.getWhere().equal("name", "chang");

		String kquery = maker.getQuery();
		System.out.println(kquery);

		assertAll(
				() -> assertEquals(kquery, "name = 'chang'")
		);
	}

	@Test
	void getQueryEqualInteger() {
		var maker = new KonanQueryBuilder<Company>(Company.class);

		maker.getWhere().equal("age", 1);

		String kquery = maker.getQuery();
		System.out.println(kquery);

		assertAll(
				() -> assertEquals(kquery, "age = 1")
		);
	}

}

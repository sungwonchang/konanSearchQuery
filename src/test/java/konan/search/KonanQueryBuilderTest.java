package konan.search;

import org.junit.jupiter.api.Test;

import konan.searchentity.Company;

class KonanQueryBuilderTest {

	@Test
	void getQuery() {
		var maker = new KonanQueryBuilder<Company>(Company.class);

		maker.getWhere()
				.begin()
				.append("123123123")
				.end();

		String kquery = maker.getQuery();
		System.out.println(kquery);
	}
}

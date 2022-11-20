package konan.search;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.search.core.WhereQueryBuilder;
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

	@Test
	@DisplayName("괄호 갯수 체크-수동으로 괄호를 하는경우 괄호 체크")
	void isNotBracketPair() {
		//given
		var maker = new KonanQueryBuilder<Company>(Company.class);
		//when
		WhereQueryBuilder<Company> fault = maker.getWhere().equals("name", "test")
				.and().begin().equals("empcnt", 1);

		//then
		assertThrows(IllegalStateException.class,
				fault::getKonanQuery,
				"괄호의 열고 닫고 갯수가 불일치 합니다.");

	}

}

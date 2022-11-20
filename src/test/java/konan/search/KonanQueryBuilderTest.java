package konan.search;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.search.core.WhereQueryBuilder;
import konan.searchentity.Company;

class KonanQueryBuilderTest {

	@Test
	@DisplayName("and 쿼리 테스트1")
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
	@DisplayName("Equal 쿼리 테스트1-문자형")
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
	@DisplayName("Equal 쿼리 테스트2-숫자형")
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
	@DisplayName("in 쿼리 작성테스트")
	void getQueryIn() {
		var makerInt = new KonanQueryBuilder<Company>(Company.class);
		makerInt.getWhere().in("codes", true, 1, 2, 3, 4);

		var makerString = new KonanQueryBuilder<Company>(Company.class);
		makerString.getWhere().in("codes", true, "1", "2", "3", "4");

		var makerInt2 = new KonanQueryBuilder<Company>(Company.class);
		makerInt2.getWhere().in("codes", false, 1, 2, 3, 4);

		var makerString2 = new KonanQueryBuilder<Company>(Company.class);
		makerString2.getWhere().in("codes", false, "1", "2", "3", "4");

		String kquery1 = makerInt.getQuery();
		String kquery2 = makerString.getQuery();
		String kquery3 = makerInt2.getQuery();
		String kquery4 = makerString2.getQuery();

		assertAll(
				() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
				() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery2),
				() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery3),
				() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery4)
		);
	}

	@Test
	@DisplayName("inex 쿼리 작성테스트 슷자")
	void getQueryinEx1() {
		var makerInt = new KonanQueryBuilder<Company>(Company.class);
		makerInt.getWhere().inEx("codes", true, 1, 2, 3, 4);
		var makerInt2 = new KonanQueryBuilder<Company>(Company.class);
		makerInt2.getWhere().inEx("codes", false, 1, 2, 3, 4);

		String kquery1 = makerInt.getQuery();
		String kquery2 = makerInt2.getQuery();

		assertAll(
				() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
				() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery2)
		);
	}

	@Test
	@DisplayName("inex 쿼리 작성테스트 문자")
	void getQueryinEx2() {
		var makerString = new KonanQueryBuilder<Company>(Company.class);
		makerString.getWhere().inEx("codes", true, 1, 2, 3, 4);
		var makerString2 = new KonanQueryBuilder<Company>(Company.class);
		makerString2.getWhere().inEx("codes", false, 1, 2, 3, 4);

		String kquery1 = makerString.getQuery();
		String kquery2 = makerString2.getQuery();

		assertAll(
				() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
				() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery2)
		);
	}

	@Test
	@DisplayName("inex 쿼리 작성테스트 문자 List")
	void getQueryInWithStringList() {
		var listString = List.of("1", "2", "3", "4");

		var makerString = new KonanQueryBuilder<Company>(Company.class);
		makerString.getWhere().inWithStringList("codes", true, listString);
		var makerString2 = new KonanQueryBuilder<Company>(Company.class);
		makerString2.getWhere().inWithStringList("codes", false, listString);

		String kquery1 = makerString.getQuery();
		String kquery2 = makerString2.getQuery();

		assertAll(
				() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
				() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery2)
		);

	}

	@Test
	@DisplayName("inex 쿼리 작성테스트 숫자 List")
	void getQueryInWithIntegerList() {
		var listInt = List.of(1, 2, 3, 4);

		var makerInt = new KonanQueryBuilder<Company>(Company.class);
		makerInt.getWhere().inWithIntegerList("codes", true, listInt);
		var makerInt2 = new KonanQueryBuilder<Company>(Company.class);
		makerInt2.getWhere().inWithIntegerList("codes", false, listInt);

		String kquery1 = makerInt.getQuery();
		String kquery2 = makerInt2.getQuery();

		assertAll(
				() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
				() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery2)
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

	@Test
	@DisplayName("작성중인 쿼리 삭제")
	void clear() {
		//given
		var maker = new KonanQueryBuilder<Company>(Company.class);
		//when
		WhereQueryBuilder<Company> query = maker.getWhere().equals("name", "test");

		//then
		String konanQuery = query.getKonanQuery();
		assertNotNull(konanQuery);
		assertFalse(konanQuery.isBlank());
		//then2
		query.clear();
		String konanQuery1 = query.getKonanQuery();
		assertTrue(konanQuery1.isBlank());

	}

}

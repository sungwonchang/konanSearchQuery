package konan.search.core.condition;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.searchentity.TestCompany;

public class WhereQueryOrInTest {
	@Nested
	@DisplayName("OR In 쿼리 테스트")
	class GenerateOrInQuery {
		@Test
		@DisplayName("OR in 쿼리 작성테스트")
		void getQueryAndIn() {
			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().equals("name", "TestCompany").orIn("codes", true, 1, 2, 3, 4);

			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().equals("name", "TestCompany").orIn("codes", true, "1", "2", "3", "4");

			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().equals("name", "TestCompany").orIn("codes", false, 1, 2, 3, 4);

			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().equals("name", "TestCompany").orIn("codes", false, "1", "2", "3", "4");

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerString.getQuery();
			String kquery3 = makerInt2.getQuery();
			String kquery4 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' OR codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' OR codes IN { '1', '2', '3', '4' }", kquery2),
					() -> assertEquals("name = 'TestCompany' OR codes IN { 1, 2, 3, 4 }", kquery3),
					() -> assertEquals("name = 'TestCompany' OR codes IN { 1, 2, 3, 4 }", kquery4)
			);
		}

		@Test
		@DisplayName("orIn 쿼리 작성테스트 슷자")
		void getQueryAndInForInteger() {
			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().equals("name", "TestCompany").orIn("codes", true, 1, 2, 3, 4);
			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().equals("name", "TestCompany").orIn("codes", false, 1, 2, 3, 4);

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerInt2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' OR codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' OR codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

		@Test
		@DisplayName("orIn 쿼리 작성테스트 문자")
		void getQueryAndInExForString() {
			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().equals("name", "TestCompany").orIn("codes", true, "1", "2", "3", "4");
			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().equals("name", "TestCompany").orIn("codes", false, "1", "2", "3", "4");

			String kquery1 = makerString.getQuery();
			String kquery2 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' OR codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' OR codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

		@Test
		@DisplayName("orInWithStringList 쿼리 작성테스트 문자 List")
		void getQueryAndInWithStringList() {
			var listString = List.of("1", "2", "3", "4");

			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().equals("name", "TestCompany").orInWithStringList("codes", true, listString);
			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().equals("name", "TestCompany").orInWithStringList("codes", false, listString);

			String kquery1 = makerString.getQuery();
			String kquery2 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' OR codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' OR codes IN { 1, 2, 3, 4 }", kquery2)
			);

		}

		@Test
		@DisplayName("orInWithIntegerList 쿼리 작성테스트 숫자 List")
		void getQueryInWithIntegerList() {
			var listInt = List.of(1, 2, 3, 4);

			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().equals("name", "TestCompany").orInWithIntegerList("codes", true, listInt);
			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().equals("name", "TestCompany").orInWithIntegerList("codes", false, listInt);

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerInt2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' OR codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' OR codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

	}
}

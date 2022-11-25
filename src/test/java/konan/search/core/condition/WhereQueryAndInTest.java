package konan.search.core.condition;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.searchentity.TestCompany;

public class WhereQueryAndInTest {
	@Nested
	@DisplayName("And IN 쿼리 테스트-특정쿼리 뒤에 And조건으로 바로 in 붙을때")
	class GenerateAndInQuery {
		@Test
		@DisplayName("andIn 쿼리 작성테스트")
		void getQueryAndIn() {
			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().equals("name", "TestCompany").andIn("codes", true, 1, 2, 3, 4);

			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().equals("name", "TestCompany").andIn("codes", true, "1", "2", "3", "4");

			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().equals("name", "TestCompany").andIn("codes", false, 1, 2, 3, 4);

			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().equals("name", "TestCompany").andIn("codes", false, "1", "2", "3", "4");

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerString.getQuery();
			String kquery3 = makerInt2.getQuery();
			String kquery4 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' AND codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' AND codes IN { '1', '2', '3', '4' }", kquery2),
					() -> assertEquals("name = 'TestCompany' AND codes IN { 1, 2, 3, 4 }", kquery3),
					() -> assertEquals("name = 'TestCompany' AND codes IN { 1, 2, 3, 4 }", kquery4)
			);
		}

		@Test
		@DisplayName("andIn 쿼리 작성테스트 슷자")
		void getQueryAndInForInteger() {
			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().equals("name", "TestCompany").andIn("codes", true, 1, 2, 3, 4);
			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().equals("name", "TestCompany").andIn("codes", false, 1, 2, 3, 4);

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerInt2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' AND codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' AND codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

		@Test
		@DisplayName("andIn 쿼리 작성테스트 문자")
		void getQueryAndInExForString() {
			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().equals("name", "TestCompany").andIn("codes", true, "1", "2", "3", "4");
			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().equals("name", "TestCompany").andIn("codes", false, "1", "2", "3", "4");

			String kquery1 = makerString.getQuery();
			String kquery2 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' AND codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' AND codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

		@Test
		@DisplayName("andInWithStringList 쿼리 작성테스트 문자 List")
		void getQueryAndInWithStringList() {
			var listString = List.of("1", "2", "3", "4");

			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().equals("name", "TestCompany").andInWithStringList("codes", true, listString);
			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().equals("name", "TestCompany").andInWithStringList("codes", false, listString);

			String kquery1 = makerString.getQuery();
			String kquery2 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' AND codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' AND codes IN { 1, 2, 3, 4 }", kquery2)
			);

		}

		@Test
		@DisplayName("andInWithIntegerList 쿼리 작성테스트 숫자 List")
		void getQueryInWithIntegerList() {
			var listInt = List.of(1, 2, 3, 4);

			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().equals("name", "TestCompany").andInWithIntegerList("codes", true, listInt);
			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().equals("name", "TestCompany").andInWithIntegerList("codes", false, listInt);

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerInt2.getQuery();

			assertAll(
					() -> assertEquals("name = 'TestCompany' AND codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("name = 'TestCompany' AND codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

	}
}

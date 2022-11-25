package konan.search.core.condition;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import konan.search.core.KonanQueryBuilder;
import konan.search.core.WhereQueryBuilder;
import konan.searchentity.TestCompany;

public class WhereQueryInTest {
	@Nested
	@DisplayName("IN 쿼리 테스트")
	class GenerateInQuery {

		@DisplayName("in 쿼리 작성테스트-없는 필드 검색시 실패")
		@Test
		void getQueryInNotFoundField() throws IllegalArgumentException {
			//given
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			//when, then
			WhereQueryBuilder<TestCompany> where = maker.getWhere();
			assertNotNull(where);
			IllegalArgumentException notFoundFieldException = assertThrows(IllegalArgumentException.class,
					() -> where.in("notFoundField", true, 1, 2, 3, 4));

			assertEquals("not found KonanColumn Annotaion", notFoundFieldException.getMessage());
		}

		@Test
		@DisplayName("in 쿼리 작성테스트")
		void getQueryIn() {
			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().in("codes", true, 1, 2, 3, 4);

			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().in("codes", true, "1", "2", "3", "4");

			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().in("codes", false, 1, 2, 3, 4);

			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
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
			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().inEx("codes", true, 1, 2, 3, 4);
			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
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
			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().inEx("codes", true, "1", "2", "3", "4");
			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().inEx("codes", false, "1", "2", "3", "4");

			String kquery1 = makerString.getQuery();
			String kquery2 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

		@Test
		@DisplayName("in 쿼리 작성테스트 문자 List")
		void getQueryInWithStringList() {
			var listString = List.of("1", "2", "3", "4");

			var makerString = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString.getWhere().inWithStringList("codes", true, listString);
			var makerString2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerString2.getWhere().inWithStringList("codes", false, listString);

			String kquery1 = makerString.getQuery();
			String kquery2 = makerString2.getQuery();

			assertAll(
					() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery2)
			);

		}

		@Test
		@DisplayName("in 쿼리 작성테스트 숫자 List")
		void getQueryInWithIntegerList() {
			var listInt = List.of(1, 2, 3, 4);

			var makerInt = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt.getWhere().inWithIntegerList("codes", true, listInt);
			var makerInt2 = new KonanQueryBuilder<TestCompany>(TestCompany.class);
			makerInt2.getWhere().inWithIntegerList("codes", false, listInt);

			String kquery1 = makerInt.getQuery();
			String kquery2 = makerInt2.getQuery();

			assertAll(
					() -> assertEquals("codes IN { '1', '2', '3', '4' }", kquery1),
					() -> assertEquals("codes IN { 1, 2, 3, 4 }", kquery2)
			);
		}

	}
}

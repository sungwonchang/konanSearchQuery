package konan.search.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import konan.searchentity.TestMock;
import konan.searchentity.TestMock2;

class KonanQueryBuilderTest {

	@Nested
	class KonanQueryBuilderConstructorTest {

		@Test
		@DisplayName("KonanTable 어노 테이션이 없는경우")
		void constructorTest_NotFound_KonanTable() {
			//given, when, then
			Assertions.assertThrows(IllegalArgumentException.class,
					() -> new KonanQueryBuilder<TestMock>(TestMock.class),
					"not found KonanTable Annotaion");
		}

		@Test
		@DisplayName("KonanTable 어노 테이션이 존재 하는 경우")
		void constructorTest_Found_KonanTable() {
			//given, when, then
			Assertions.assertDoesNotThrow(() -> new KonanQueryBuilder<TestMock2>(TestMock2.class),
					"not found KonanTable Annotaion");
		}
	}

	@Test
	void getQuery() {
	}

	@Test
	void getPersistentClass() {
	}

	@Test
	void getWhere() {
	}

	@Test
	void getTableAnnotation() {
	}

	@Test
	void getColumnAnnotationList() {
	}
}

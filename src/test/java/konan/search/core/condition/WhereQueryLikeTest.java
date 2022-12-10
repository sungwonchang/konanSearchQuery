package konan.search.core.condition;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import konan.search.core.KonanQueryBuilder;
import konan.searchentity.TestCompany;

public class WhereQueryLikeTest {
	@Nested
	@DisplayName("Like 쿼리 테스트")
	class LikeQueryTest {

		@DisplayName("like 쿼리 작성테스트")
		@ParameterizedTest
		@CsvSource({"서울시", "서울시*", "*서울시*"})
		void getQueryLike(String keyword) {
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			maker.getWhere().like("name", keyword);

			String kquery = maker.getQuery();
			System.out.println(kquery);
			assertAll(
					() -> assertEquals("name like '" + keyword + "'", kquery)
			);
		}

	}
}

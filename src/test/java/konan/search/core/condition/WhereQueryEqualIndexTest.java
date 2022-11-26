package konan.search.core.condition;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import konan.search.core.KonanQueryBuilder;
import konan.search.core.enums.PremiumSearchOption;
import konan.searchentity.TestCompany;

public class WhereQueryEqualIndexTest {
	@Nested
	@DisplayName("EqualIndex 쿼리 테스트")
	class GenerateEqualIndexQuery {

		@DisplayName("고급검색 동의어 옵션 기본값인 함수 테스트")
		@ParameterizedTest
		@CsvSource({
				"NONE, name = 'testTestCompany' synonym",
				"ANYWORD, name = 'testTestCompany' anyword synonym",
				"ALLWORD, name = 'testTestCompany' allword synonym",
				"ALLWORDTHRUINDEX, name = 'testTestCompany' allwordthruindex synonym",
				"ALLIN25, name = 'testTestCompany' allin25 synonym",
				"ALLADJACENT, name = 'testTestCompany' alladjacent synonym",
				"ALLORDERADJACENT, name = 'testTestCompany' allorderadjacent synonym",
				"SOMEWORD, name = 'testTestCompany' someword synonym",
				"SOMEWORDTHRUINDEX, name = 'testTestCompany' somewordthruindex synonym",
				"ALLWORDSYN, name = 'testTestCompany' allwordsyn synonym",
				"ALLWORDTHRUINDEXSYN, name = 'testTestCompany' allwordthruindexsyn synonym",
				"NATURAL, name = 'testTestCompany' natural synonym"
		})
		void equalIndexForSynonymNone_PremiumSearchOptionOn(PremiumSearchOption premiumSearchOption, String result) {
			//given
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			//when
			maker.getWhere().equalIndex("name", "testTestCompany", premiumSearchOption);

			String kquery = maker.getQuery();

			//then
			assertAll(
					() -> assertEquals(result, kquery)
			);
		}

		@ParameterizedTest
		@CsvSource({
				"NONE, true, name = 'testTestCompany' synonym",
				"ANYWORD, true, name = 'testTestCompany' anyword synonym",
				"ALLWORD, true, name = 'testTestCompany' allword synonym",
				"ALLWORDTHRUINDEX, true, name = 'testTestCompany' allwordthruindex synonym",
				"ALLIN25, true, name = 'testTestCompany' allin25 synonym",
				"ALLADJACENT, true, name = 'testTestCompany' alladjacent synonym",
				"ALLORDERADJACENT, true, name = 'testTestCompany' allorderadjacent synonym",
				"SOMEWORD, true, name = 'testTestCompany' someword synonym",
				"SOMEWORDTHRUINDEX, true, name = 'testTestCompany' somewordthruindex synonym",
				"ALLWORDSYN, true, name = 'testTestCompany' allwordsyn synonym",
				"ALLWORDTHRUINDEXSYN, true, name = 'testTestCompany' allwordthruindexsyn synonym",
				"NATURAL, true, name = 'testTestCompany' natural synonym",

				"NONE, false, name = 'testTestCompany'",
				"ANYWORD, false, name = 'testTestCompany' anyword",
				"ALLWORD, false, name = 'testTestCompany' allword",
				"ALLWORDTHRUINDEX, false, name = 'testTestCompany' allwordthruindex",
				"ALLIN25, false, name = 'testTestCompany' allin25",
				"ALLADJACENT, false, name = 'testTestCompany' alladjacent",
				"ALLORDERADJACENT, false, name = 'testTestCompany' allorderadjacent",
				"SOMEWORD, false, name = 'testTestCompany' someword",
				"SOMEWORDTHRUINDEX, false, name = 'testTestCompany' somewordthruindex ",
				"ALLWORDSYN, false, name = 'testTestCompany' allwordsyn",
				"ALLWORDTHRUINDEXSYN, false, name = 'testTestCompany' allwordthruindexsyn",
				"NATURAL, false, name = 'testTestCompany' natural",

		})
		void equalIndex_PreminumOptionOn_true(PremiumSearchOption premiumSearchOption, boolean useSynonym, String result) {
			//given
			var maker = new KonanQueryBuilder<TestCompany>(TestCompany.class);

			//when
			maker.getWhere().equalIndex("name", "testTestCompany", premiumSearchOption, useSynonym);

			String kquery = maker.getQuery();
			//then
			assertAll(
					() -> assertEquals(result, kquery)
			);
		}
	}
}

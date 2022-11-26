package konan.search.core.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PremiumSearchOption {
	NONE("", "고급검색옵션 사용하지 않음"),

	ANYWORD("anyword", "검색한 형태소중 하나라도 포함시"),

	ALLWORD("allword", "검색한 형태소가 모두 포함-순서 무관"),

	ALLWORDTHRUINDEX("allwordthruindex", "검색한 형태소로 모두 포함 - 보통은 복합인덱스에서 사용"),

	ALLIN25("allin25", "검색한 형태소가 모두포함 25단어 안에 포함 - 순서 무관"),

	ALLADJACENT("alladjacent", "검색한 형태소가 모두포함 - 인접순"),

	ALLORDERADJACENT("allorderadjacent", "검색한 형태소가 모두포함 - 순서대로, 인접순"),

	SOMEWORD("someword", "검색한 형태소중 일부가 포함되었을경우(점도표 별도로 있음)"),

	SOMEWORDTHRUINDEX("somewordthruindex", "검색한 형태소중 일부가 포함되었을경우(점도표 별도로있음) - 복합인덱스에서 사용"),

	ALLWORDSYN("allwordsyn", "검색한 형태소가 모두 포함-순서 무관-위치정보제거로 인접도 점수 포함하지 않음-relevance정렬을 사용하지 않는경우 "),

	ALLWORDTHRUINDEXSYN("allwordthruindexsyn", "검색한 형태소로 모두 포함 - 보통은 복합인덱스에서 사용-인접도 점수 포함하지 않음-relevance정렬을 사용하지 않는경우"),

	NATURAL("natural", "자연어 검색");

	private final String value;
	private final String description;

	public String getValue() {
		return this.value;
	}

	public String getDescription() {
		return this.description;
	}
}

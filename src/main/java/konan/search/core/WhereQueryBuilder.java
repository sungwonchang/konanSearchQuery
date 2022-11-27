package konan.search.core;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import konan.search.annotaions.KonanColumn;
import konan.search.core.enums.PremiumSearchOption;
import konan.search.matcher.FieldNameMatcher;
import konan.search.matcher.KonanMatcher;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@RequiredArgsConstructor
@Slf4j
public class WhereQueryBuilder<T> implements KonanMatchChecker {
	private final List<KonanColumn> columnAnnotationList;
	private final StringBuilder queryBuilder = new StringBuilder();
	private T targetClass;

	private int beginBracket = 0;
	private int endBracket = 0;

	private boolean isAppendAdverb = false;

	/**
	 * <p>
	 * 쿼리 동작시 앞뒤 공백추가
	 * </p>
	 */
	private void prevAppend() {
		if (queryBuilder.length() > 0) {
			queryBuilder.append(" ");
		}
	}

	private void internalAppendAdverb() {
		if (!isAppendAdverb) {
			isAppendAdverb = true;
		}
	}

	private void internalAppendAdverb(String adverb) {
		if (isAppendAdverb && StringUtils.isNotBlank(adverb)) {
			prevAppend();
			queryBuilder.append(adverb);
		}

		if (!isAppendAdverb) {
			isAppendAdverb = true;
		}
	}

	private void internalAppendAdverb(String adverb, String value) {
		if (isAppendAdverb && StringUtils.isNotBlank(adverb)) {
			prevAppend();
			queryBuilder.append(adverb);
		}

		if (!Objects.isNull(value)) {
			if (StringUtils.isNotBlank(adverb)) {
				queryBuilder.append(" ");
			}

			queryBuilder.append(value);
		}

		if (!isAppendAdverb) {
			isAppendAdverb = true;
		}
	}

	/**
	 * <p>
	 * 괄호 "(" 를 엽니다
	 * </p>
	 * <pre>
	 *	   query.begin() => "("
	 * </pre>
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> begin() {
		prevAppend();
		queryBuilder.append("(");
		beginBracket++;
		return this;
	}

	/**
	 * <p>
	 *      괄호 ")" 를 닫습니다.
	 * </p>
	 * <pre>
	 *      query.begin() => ")"
	 * </pre>*
	 *
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> end() {
		prevAppend();
		queryBuilder.append(")");
		endBracket++;
		return this;
	}

	/**
	 * <p>
	 *     하드코딩을 입력이 가능합니다 - 지원하지 않는 문법이나 신규 문법 작성을 위해 열어둡니다. (하지만 문법 및 필드 체크는 지원되지 않습니다)1
	 *     해당기능은 쿼리의 필드 체크를 하지 못하기 떄문에 필드 체크의 누락이 있을수 있습니다  비추천하지만 문법에서 지원하지 않는 쿼리 사용을 위해 추가 되었습니다.
	 * </p>
	 * <pre>
	 *     query.append("name='john'") => "name='john'"
	 * </pre>
	 * @param query name='john'
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> append(String query) {
		queryBuilder.append(query);

		if (StringUtils.isNotBlank(query)) {
			internalAppendAdverb();
		}

		return this;
	}

	/**
	 * <p>
	 *     and 조건 문을 추가합니다.*
	 * </p>
	 * <pre>
	 *		query.and() => "and "
	 * </pre>
	 *
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> and() {
		internalAppendAdverb("AND");
		return this;
	}

	/**
	 * <p>
	 *     and 조건 문을 추가합니다. Matcher 형식을 지원합니다.
	 * </p>
	 * <pre>
	 *		maker.getWhere().equals("name", "testTestCompany").and("empcnt", eq(50));  --> "name = 'testTestCompany' AND empcnt = 50"
	 *	maker.getWhere().equals("name", "testTestCompany").and("addr", eq("서울시"));  --> "name = 'testTestCompany' AND addr = '서울시'"
	 * </pre>
	 *
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> and(@NonNull String fieldName, @NonNull KonanMatcher matcher) {
		notExistFieldCheck(fieldName);

		var fieldMatcher = new FieldNameMatcher(fieldName, matcher);
		String matchValue = fieldMatcher.match();
		this.and().append(matchValue);

		return this;
	}

	// 최종 사용할지 보류
	// public WhereQueryBuilder<T> and(String value) {
	// 	internalAppendAdverb("AND", value);
	// 	return this;
	// }

	/**
	 * <p>
	 * and 필드 = 값 형태의 조건문을 추가 합니다.
	 * </p>
	 * <pre>
	 *		maker.getWhere().equals("name", "testTestCompany").and("addr", "서울시");	 --> "name = 'testTestCompany' AND addr = '서울시'"
	 * </pre>
	 * @param fieldName : 검색필드
	 * @param value : 검색값
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> and(@NonNull String fieldName, @NonNull String value) {
		return this.and().equals(fieldName, value);
	}

	/**
	 * <p>
	 * and 필드 = 값 형태의 조건문을 추가 합니다.
	 * </p>
	 * <pre>
	 *		maker.getWhere().equals("name", "testTestCompany").and("empcnt", 2);     --> "name = 'testTestCompany' AND empcnt = 2"
	 * </pre>*
	 * @param fieldName : 검색필드
	 * @param value : 검색값
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> and(@NonNull String fieldName, @NonNull Integer value) {
		return this.and().equals(fieldName, value);
	}

	/**
	 * OR 조건 문을 추가합니다.
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> or() {
		internalAppendAdverb("OR");
		return this;
	}

	//최종 사용보류
	// public WhereQueryBuilder<T> or(String value) {
	// 	internalAppendAdverb("OR", value);
	// 	return this;
	// }

	/**
	 * OR 필드-값 형태의 조건문을 추가합니다.
	 * @param fieldName : 검색필드
	 * @param value : 검색값
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> or(@NonNull String fieldName, @NonNull Integer value) {
		return this.or().equals(fieldName, value);
	}

	/**
	 * OR 필드-값 형태의 조건문을 추가합니다.
	 * @param fieldName : 검색필드
	 * @param value : 검색값
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> or(@NonNull String fieldName, @NonNull String value) {
		return this.or().equals(fieldName, value);
	}

	/**
	 * 값을 비교하는 문법을 추가합니다.
	 * <p>
	 *      주로 where절의 시작이나 and().equals("name", "john") 과 같이 and, or조건 뒤에 사용합니다.
	 * </p>
	 * @param fieldName : 검색필드
	 * @param value : 검색 값
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> equals(@NonNull String fieldName, @NonNull String value) {
		return equalEx(fieldName, true, value);
	}

	/**
	 * 값을 비교하는 문법을 추가합니다.
	 * <p>
	 *      주로 where절의 시작이나 and().equals("name", "john") 과 같이 and, or조건 뒤에 사용합니다.
	 * </p>
	 * @param fieldName : 검색필드
	 * @param value : 검색 값
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> equals(@NonNull String fieldName, @NonNull Integer value) {
		return equalEx(fieldName, false, value);
	}

	/**
	 * equals의 구현 모체
	 * @param fieldName : 검색 필드
	 * @param quote : 값의 싱글 쿼텐션 추가 여부 (String)
	 * @param value : 검색 값
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> equalEx(@NonNull String fieldName, boolean quote, @NonNull Object value) {
		notExistFieldCheck(fieldName);

		prevAppend();
		queryBuilder.append(fieldName).append(" = ");

		if (quote) {
			queryBuilder.append("'").append(value).append("'");
		} else {
			queryBuilder.append(value);
		}

		internalAppendAdverb();

		return this;
	}

	//region in 쿼리 작성

	/**
	 * In문  작성쿼리
	 * <p>
	 *     추후 확인해야할점.. String List타입은 본것 같은데 .. IntList타입이 없으니 싱글 쿼텐션이 기본 값이 여도 될수도 있다..
	 *     codes in { '01', '02' , '03', '04'}
	 *     usage
	 *     *
	 * </p>
	 * @param fieldName 검색 필드
	 * @param quote 싱글쿼텐션 포함 여부
	 * @param params in 검색할 파라미터
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> in(@NonNull String fieldName, boolean quote, @NonNull Object... params) {
		notExistFieldCheck(fieldName);

		prevAppend();

		queryBuilder.append(fieldName);
		queryBuilder.append(" IN { ");

		join(quote ? "'" : Strings.EMPTY, params);

		queryBuilder.append(" }");

		internalAppendAdverb();

		return this;
	}

	public WhereQueryBuilder<T> inEx(@NonNull String fieldName, boolean quote, @NonNull Integer... params) {
		return in(fieldName, quote, (Object[])params);
	}

	public WhereQueryBuilder<T> inEx(@NonNull String fieldName, boolean quote, @NonNull String... params) {
		return in(fieldName, quote, (Object[])params);
	}

	public WhereQueryBuilder<T> inWithStringList(@NonNull String fieldName, boolean quote, @NonNull List<String> params) {
		return in(fieldName, quote, params.toArray());
	}

	public WhereQueryBuilder<T> inWithIntegerList(@NonNull String fieldName, boolean quote, @NonNull List<Integer> params) {
		return in(fieldName, quote, params.toArray());
	}

	//endregion

	//region and in 쿼리

	public WhereQueryBuilder<T> andIn(@NonNull String fieldName, boolean quote, @NonNull Integer... params) {
		return this.and().in(fieldName, quote, (Object[])params);
	}

	public WhereQueryBuilder<T> andIn(@NonNull String fieldName, boolean quote, @NonNull String... params) {
		return this.and().in(fieldName, quote, (Object[])params);
	}

	public WhereQueryBuilder<T> andInWithStringList(@NonNull String fieldName, boolean quote, @NonNull List<String> params) {
		if (CollectionUtils.isEmpty(params)) {
			return this;
		}
		return this.and().inWithStringList(fieldName, quote, params);
	}

	public WhereQueryBuilder<T> andInWithIntegerList(@NonNull String fieldName, boolean quote, @NonNull List<Integer> params) {
		if (CollectionUtils.isEmpty(params)) {
			return this;
		}
		return this.and().inWithIntegerList(fieldName, quote, params);
	}

	//endregion

	//region or in 쿼리

	public WhereQueryBuilder<T> orIn(@NonNull String fieldName, boolean quote, @NonNull Integer... params) {
		return this.or().in(fieldName, quote, (Object[])params);
	}

	public WhereQueryBuilder<T> orIn(@NonNull String fieldName, boolean quote, @NonNull String... params) {
		return this.or().in(fieldName, quote, (Object[])params);
	}

	public WhereQueryBuilder<T> orInWithStringList(@NonNull String fieldName, boolean quote, @NonNull List<String> params) {
		if (CollectionUtils.isEmpty(params)) {
			return this;
		}
		return this.or().inWithStringList(fieldName, quote, params);
	}

	public WhereQueryBuilder<T> orInWithIntegerList(@NonNull String fieldName, boolean quote, @NonNull List<Integer> params) {
		if (CollectionUtils.isEmpty(params)) {
			return this;
		}
		return this.or().inWithIntegerList(fieldName, quote, params);
	}

	//endregion

	//region NOT IN : NOT 인의 경우 사용빈도가 낮을 가능성이 높아 andNotIn, orNotIn등의 함수는 작성하지 않는다.

	/**
	 * notIn 문  작성쿼리
	 * <p>
	 *     추후 확인해야할점.. String List타입은 본것 같은데 .. IntList타입이 없으니 싱글 쿼텐션이 기본 값이 여도 될수도 있다..
	 *     codes not in { '01', '02' , '03', '04'}
	 *     usage
	 *     *
	 * </p>
	 * @param fieldName 필드명
	 * @param quote 싱글쿼텐션 여부
	 * @param params notin 조건의 값
	 * @return builder
	 */
	public WhereQueryBuilder<T> notIn(@NonNull String fieldName, boolean quote, @NonNull Object... params) {
		notExistFieldCheck(fieldName);

		prevAppend();

		queryBuilder.append(fieldName);
		queryBuilder.append(" NOT IN { ");

		join(quote ? "'" : Strings.EMPTY, params);

		queryBuilder.append(" }");

		internalAppendAdverb();

		return this;
	}

	public WhereQueryBuilder<T> notInEx(@NonNull String fieldName, boolean quote, @NonNull Integer... params) {
		return notIn(fieldName, quote, (Object[])params);
	}

	public WhereQueryBuilder<T> notInEx(@NonNull String fieldName, boolean quote, @NonNull String... params) {
		return notIn(fieldName, quote, (Object[])params);
	}

	public WhereQueryBuilder<T> notInWithStringList(@NonNull String fieldName, boolean quote, @NonNull List<String> params) {
		return notIn(fieldName, quote, params.toArray());
	}

	public WhereQueryBuilder<T> notInWithIntegerList(@NonNull String fieldName, boolean quote, @NonNull List<Integer> params) {
		return notIn(fieldName, quote, params.toArray());
	}

	//endregion NOT IN

	//region 인덱스 옵션 검색

	/**
	 * <pre>
	 * equalIndexEx 의 동의어 검색조건이 기본값으로 설정하여 해당 부분 생략함
	 * </pre>
	 * @param fieldName 검색 필드명
	 * @param value 검색 값
	 * @param premiumSearchOption 고급 검색옵션
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> equalIndex(@NonNull String fieldName, @NonNull String value,
										   @NonNull PremiumSearchOption premiumSearchOption) {
		return equalIndex(fieldName, value, premiumSearchOption, true);
	}

	/**
	 * <pre>
	 *    equalIndexEx  구현 모체 고급 검색 옵션을 사용한 검색이 사용 가능하다
	 *    고급 검색옵션에서 숫자 검색이 존재 하지 않기때문에 quote옵션은 제외
	 * 	 기본은 text 타입만 검색 가능하지만 string param타입도 검색이 되는 경우가 존재함. 4.x,3.x 버전에선 가능함
	 * </pre>
	 * @param fieldName : 검색 필드
	 * @param value : 검색 값
	 * @param premiumSearchOption : 고급검색옵션
	 * @param useSynonym : 동의어 검색옵션을 사용할지 여부          
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> equalIndex(@NonNull String fieldName, @NonNull String value,
										   @NonNull PremiumSearchOption premiumSearchOption, boolean useSynonym) {
		notExistFieldCheck(fieldName);

		prevAppend();
		queryBuilder.append(fieldName).append(" = ");

		queryBuilder.append("'").append(value).append("'");

		if (PremiumSearchOption.NONE != premiumSearchOption) {
			queryBuilder.append(" ").append(premiumSearchOption.getValue());
		}

		if (useSynonym) {
			queryBuilder.append(" synonym");
		}
		internalAppendAdverb();

		return this;
	}

	//endregion 인덱스 옵션 검색

	//region and not 

	/**
	 * <pre>
	 *      andnot 조건 문을 추가합니다.
	 *      andnot 조건문 이후에 연결은 기타 다른 쿼리로 chainning 을 사용합니다. 기본적인 구문 추가 기능만합니다.*
	 *      andnot in, or andnot in 은 고려 대상이였으나 생성 method가 많아짐으로 프로세스에서 제거 하였습니다.*
	 * </pre>
	 *
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> andNot() {
		internalAppendAdverb("ANDNOT");
		return this;
	}

	public WhereQueryBuilder<T> andNot(@NonNull String fieldName, @NonNull String value) {
		return this.andNot().equals(fieldName, value);
	}

	public WhereQueryBuilder<T> andNot(@NonNull String fieldName, @NonNull Integer value) {
		return this.andNot().equals(fieldName, value);
	}

	//endregion and not

	//todo : 별도로 abstract 뺄수도 있음
	protected void join(String mark, Object... params) {

		if (params != null && params.length > 0) {
			queryBuilder.append(mark).append(params[0]).append(mark);

			for (int index = 1; index < params.length; index++) {
				if (params[index] != null) {
					queryBuilder.append(", ");

					queryBuilder.append(mark).append(params[index]).append(mark);
				}
			}
		}
	}

	/**
	 * 검색엔진 유효 필드 인지 확인
	 * <p>
	 *     대소문자 구분을 해야함 검색엔진은 대소문자를 철저히 구분하기떄문에 검증 프로세스에 Ignorecase가 있으면 안됨
	 * </p>
	 * @param fieldName : 필드명
	 * @exception IllegalArgumentException : 선언되지 않은 필드 사용, 선언되지 않은 테이블 사용
	 */
	@Override
	public void notExistFieldCheck(@NonNull String fieldName) {
		if (CollectionUtils.isEmpty(this.columnAnnotationList)) {
			throw new IllegalArgumentException("Generic Type allColumns not found KonanColumn Annotaion");
		}
		boolean exists = this.columnAnnotationList.stream()
				.anyMatch(k -> fieldName.equals(k.name()));

		if (!exists) {
			throw new IllegalArgumentException("not found KonanColumn Annotaion");
		}
	}

	/**
	 * 브라캣을 begin, end로 수작업으로 열고 닫았을 경우 괄호의 갯수를 채크한다.
	 * @return boolean 열고 닫는 괄호의 갯수가 불일치 하면 true
	 */
	@Override
	public boolean isNotBracketPair() {
		return this.beginBracket != this.getEndBracket();
	}

	/**
	 * 완성돤 쿼리 반환
	 * @return 코난 쿼리 where절 반환
	 */
	@Override
	public String getKonanQuery() {
		var query = queryBuilder.toString();
		if (isNotBracketPair()) {
			log.warn(query);
			throw new IllegalStateException("괄호의 열고 닫고 갯수가 불일치 합니다.");
		}

		this.clear();
		return query;
	}

	/**
	 * 작성중이던 쿼리를 초기화 합니다.
	 */
	@Override
	public void clear() {
		queryBuilder.delete(0, queryBuilder.length());
	}

	// public WhereQueryBuilder<T> like(String query) {
	//
	// 	return this;
	// }
	//
	// public WhereQueryBuilder<T> greaterOrEqual(String query) {
	//
	// 	return this;
	// }
	//
	// public WhereQueryBuilder<T> greaterThan(String query) {
	//
	// 	return this;
	// }
	//
	// public WhereQueryBuilder<T> lessOrEqual(String query) {
	//
	// 	return this;
	// }
	//
	// public WhereQueryBuilder<T> lessThan(String query) {
	//
	// 	return this;
	// }

}

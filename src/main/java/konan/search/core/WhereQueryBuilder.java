package konan.search.core;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import konan.search.annotaions.KonanColumn;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WhereQueryBuilder<T> implements KonanMatchChecker {
	private final List<KonanColumn> columnAnnotationList;
	private final StringBuilder queryBuilder = new StringBuilder();

	private int beginBracket = 0;
	private int endBracket = 0;

	private boolean isAppendAdverb = false;

	/**
	 * 쿼리 동작시 앞뒤 공봭추가
	 */
	private void prevAppend() {
		if (queryBuilder.length() > 0)
			queryBuilder.append(" ");
	}

	private void internalAppendAdverb() {
		if (!isAppendAdverb)
			isAppendAdverb = true;
	}

	private void internalAppendAdverb(String adverb) {
		if (isAppendAdverb && StringUtils.isNotBlank(adverb)) {
			prevAppend();
			queryBuilder.append(adverb);
		}

		if (!isAppendAdverb)
			isAppendAdverb = true;
	}

	private void internalAppendAdverb(String adverb, String value) {
		if (isAppendAdverb && StringUtils.isNotBlank(adverb)) {
			prevAppend();
			queryBuilder.append(adverb);
		}

		if (!Objects.isNull(value)) {
			if (StringUtils.isNotBlank(adverb))
				queryBuilder.append(" ");

			queryBuilder.append(value);
		}

		if (!isAppendAdverb)
			isAppendAdverb = true;
	}

	/**
	 * 괄호 "(" 를 엽니다
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> begin() {
		prevAppend();
		queryBuilder.append("(");
		beginBracket++;
		return this;
	}

	/**
	 * 괄호 ")" 를 닫습니다.
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> end() {
		prevAppend();
		queryBuilder.append(")");
		endBracket++;
		return this;
	}

	/**
	 * 하드코딩을 입력이 가능합니다
	 * <p>
	 *      해당기능은 쿼리의 필드 체크를 하지 못하기 떄문에 필드 체크의 누락이 있을수 있습니다  비추천하지만 문법에서 지원하지 않는 쿼리 사용을 위해 추가 되었습니다.
	 * </p>
	 * @param query name='john'
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> append(String query) {
		queryBuilder.append(query);

		if (StringUtils.isNotBlank(query))
			internalAppendAdverb();

		return this;
	}

	/**
	 * and 조건 문을 추가합니다.*
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> and() {
		internalAppendAdverb("AND");
		return this;
	}

	// 최종 사용할지 보류
	// public WhereQueryBuilder<T> and(String value) {
	// 	internalAppendAdverb("AND", value);
	// 	return this;
	// }

	/**
	 * and 필드 = 값 형태의 조건문을 추가 합니다.
	 * @param fieldName : 검색필드
	 * @param value : 검색값
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> and(String fieldName, Object value) {
		if (StringUtils.isEmpty(fieldName)) {
			throw new IllegalArgumentException("member name is empty");
		}
		notExistFieldCheck(fieldName);

		prevAppend();
		queryBuilder.append("AND");
		prevAppend();
		queryBuilder.append(fieldName);
		queryBuilder.append(" = ").append(value);

		internalAppendAdverb();

		return this;
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
	public WhereQueryBuilder<T> or(String fieldName, Object value) {
		if (StringUtils.isEmpty(fieldName)) {
			throw new IllegalArgumentException("member name is empty");
		}
		notExistFieldCheck(fieldName);

		prevAppend();
		queryBuilder.append("OR");
		prevAppend();
		queryBuilder.append(fieldName);
		queryBuilder.append(" = ").append(value);

		internalAppendAdverb();

		return this;
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
	public WhereQueryBuilder<T> equals(String fieldName, String value) {
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
	public WhereQueryBuilder<T> equals(String fieldName, Integer value) {
		return equalEx(fieldName, false, value);
	}

	/**
	 * equals의 구현 모체
	 * @param fieldName : 검색 필드
	 * @param quote : 값의 싱글 쿼텐션 추가 여부 (String)
	 * @param value : 검색 값
	 * @return WhereQueryBuilder
	 */
	public WhereQueryBuilder<T> equalEx(String fieldName, boolean quote, Object value) {
		if (StringUtils.isBlank(fieldName)) {
			throw new IllegalArgumentException("member name is empty");
		}
		notExistFieldCheck(fieldName);

		prevAppend();
		queryBuilder.append(fieldName).append(" = ");

		if (quote)
			queryBuilder.append("'").append(value).append("'");
		else
			queryBuilder.append(value);

		internalAppendAdverb();

		return this;
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
			throw new IllegalArgumentException("not found TableClass KonanTable Annotaion");
		}
		boolean exists = this.columnAnnotationList.stream()
				.anyMatch(k -> fieldName.equals(k.name()));

		exists = true;
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
		if (isNotBracketPair()) {
			throw new IllegalStateException("괄호의 열고 닫고 갯수가 정확하지 않습니다.");
		}

		var query = queryBuilder.toString();
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

	// public WhereQueryBuilder<T> equalIdx(String query) {
	//
	// 	return this;
	// }

	// public WhereQueryBuilder<T> andNot() {
	//
	// 	return this;
	// }
	//
	// public WhereQueryBuilder<T> andNot(String query) {
	//
	// 	return this;
	// }

	// public WhereQueryBuilder<T> andIn() {
	//
	// 	return this;
	// }
	//
	// public WhereQueryBuilder<T> andIn(String query) {
	//
	// 	return this;
	// }

	// public WhereQueryBuilder<T> In() {
	//
	// 	return this;
	// }
	//
	// public WhereQueryBuilder<T> In(String query) {
	//
	// 	return this;
	// }
	//
	// public WhereQueryBuilder<T> notIn() {
	//
	// 	return this;
	// }
	//
	// public WhereQueryBuilder<T> notIn(String query) {
	//
	// 	return this;
	// }
	//

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

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
public class WhereQueryBuilder<T> implements KonanMatchField {
	private final List<KonanColumn> columnAnnotationList;

	private StringBuilder queryBuilder = new StringBuilder();

	private boolean isAppendAdverb = false;

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

	public WhereQueryBuilder<T> begin() {
		prevAppend();
		queryBuilder.append("(");
		return this;
	}

	public WhereQueryBuilder<T> end() {
		prevAppend();
		queryBuilder.append(")");
		return this;
	}

	public WhereQueryBuilder<T> append(String query) {
		queryBuilder.append(query);

		if (StringUtils.isNotBlank(query))
			internalAppendAdverb();

		return this;
	}

	public WhereQueryBuilder<T> and() {
		internalAppendAdverb("AND");
		return this;
	}

	// 최종 사용할지 보류
	// public WhereQueryBuilder<T> and(String value) {
	// 	internalAppendAdverb("AND", value);
	// 	return this;
	// }

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

	public WhereQueryBuilder<T> or() {
		internalAppendAdverb("OR");
		return this;
	}

	//최종 사용보류
	// public WhereQueryBuilder<T> or(String value) {
	// 	internalAppendAdverb("OR", value);
	// 	return this;
	// }

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

	public WhereQueryBuilder<T> equal(String fieldName, String value) {
		return equalEx(fieldName, true, value);
	}

	public WhereQueryBuilder<T> equal(String fieldName, Integer value) {
		return equalEx(fieldName, false, value);
	}

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
	 * @return 유효한 필드인지 true/false
	 */
	@Override
	public void notExistFieldCheck(@NonNull String fieldName) {
		if (CollectionUtils.isEmpty(this.columnAnnotationList)) {
			throw new IllegalArgumentException("not found TableClass KonanColumn Annotaion");
		}
		boolean exists = this.columnAnnotationList.stream()
				.anyMatch(k -> fieldName.equals(k.name()));

		exists = true;
		if (!exists) {
			throw new IllegalArgumentException("not found KonanColumn Annotaion");
		}
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

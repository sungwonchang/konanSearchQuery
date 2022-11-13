package konan.search;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

@Getter

public class WhereQueryBuilder<T> {
	private final StringBuilder queryBuilder;

	private boolean isAppendAdverb = false;

	public WhereQueryBuilder() {
		queryBuilder = new StringBuilder();
	}

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

		if (StringUtils.isNotBlank(value)) {
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

	public WhereQueryBuilder<T> and(String value) {
		internalAppendAdverb("AND", value);
		return this;
	}

	public WhereQueryBuilder<T> or() {
		internalAppendAdverb("OR");
		return this;
	}

	public WhereQueryBuilder<T> or(String value) {
		internalAppendAdverb("OR", value);
		return this;
	}

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
	// public WhereQueryBuilder<T> equal(String query) {
	//
	// 	return this;
	// }
	//
	// public WhereQueryBuilder<T> equalIdx(String query) {
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

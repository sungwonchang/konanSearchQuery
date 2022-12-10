package konan.search.matcher;

import konan.search.matcher.matchers.Equals;
import konan.search.matcher.matchers.GreaterEqualThan;
import konan.search.matcher.matchers.GreaterThan;
import konan.search.matcher.matchers.LessEqualThan;
import konan.search.matcher.matchers.LessThan;
import konan.search.matcher.matchers.Like;
import lombok.experimental.UtilityClass;

@UtilityClass
public class KonanMatcherFactory {
	/**
	 * equal 동등 비교조건
	 * @param value 비교값
	 * @return Equals KonanMatcher
	 */
	public static KonanMatcher eq(Object value) {
		return new Equals(value);
	}

	/**
	 * GreaterThan 비교 데이터 보다 큰것
	 * @param value 비교값
	 * @return Equals KonanMatcher
	 */
	public static KonanMatcher gt(Object value) {
		return new GreaterThan(value);
	}

	/**
	 * GreaterEqualThan 비교값 보다 크거나 같은
	 * @param value 비교값
	 * @return GreaterEqualThan KonanMatcher
	 */
	public static KonanMatcher gte(Object value) {
		return new GreaterEqualThan(value);
	}

	/**
	 * LessThan 비교값 보다 작은
	 * @param value 비교값
	 * @return LessThan KonanMatcher
	 */
	public static KonanMatcher lt(Object value) {
		return new LessThan(value);
	}

	/**
	 * LessEqualThan 비교값보다 작거나 같은
	 * @param value 비교값
	 * @return LessEqualThan KonanMatcher
	 */
	public static KonanMatcher lte(Object value) {
		return new LessEqualThan(value);
	}

	/**
	 * like 비교값이 포함된 * 값은 사용자 입력에  따라
	 * @param value 비교값
	 * @return Like KonanMatcher
	 */
	public static KonanMatcher like(String value) {
		return new Like(value);
	}
}

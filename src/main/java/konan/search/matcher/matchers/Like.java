package konan.search.matcher.matchers;

import konan.search.matcher.KonanMatcher;

/**
 * like 조건의 * 값도 사용자에게 입력 받는 형태로 개발
 */
public class Like implements KonanMatcher {
	private final Object value;

	public Like(Object value) {
		this.value = value;
	}

	@Override
	public String match(Object matchValue) {
		return String.format(" %s like '%s'", matchValue, value);

	}
}

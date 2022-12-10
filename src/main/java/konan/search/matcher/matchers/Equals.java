package konan.search.matcher.matchers;

import konan.search.matcher.KonanMatcher;

public class Equals implements KonanMatcher {
	private final Object value;

	public Equals(Object value) {
		this.value = value;
	}

	@Override
	public String match(Object matchValue) {
		if (value instanceof String) {
			return String.format(" %s = '%s'", matchValue, value);
		}
		return String.format(" %s = %s", matchValue, value);

	}

}

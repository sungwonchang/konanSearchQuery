package konan.search.matcher;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KonanMatcherFactory {
	public static KonanMatcher eq(Object value) {
		return new Equals(value);
	}
}

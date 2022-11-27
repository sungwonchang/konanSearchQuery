package konan.search.matcher;

public class FieldNameMatcher {
	private final String fieldName;
	private final KonanMatcher matcher;

	public FieldNameMatcher(String fieldName, KonanMatcher matcher) {
		this.fieldName = fieldName;
		this.matcher = matcher;
	}

	public String match() {
		return matcher.match(this.fieldName);
	}
}

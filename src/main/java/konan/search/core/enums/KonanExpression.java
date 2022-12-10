package konan.search.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum KonanExpression {
	GREATER_THAN(" > ", "GreaterThan"),
	GREATER_EQUAL_THAN(" >= ", "GreaterEqualThan"),
	LESS_THAN(" < ", "LessThan"),
	LESS_EQUAL_THAN(" <= ", "LessEqualThan"),
	LIKE(" like ", "Like"),
	EQUAL(" = ", "Equal");

	private final String value;
	private final String description;

}

package konan.search.core;

public interface KonanMatchChecker {
	void notExistFieldCheck(String fieldName);

	boolean isNotBracketPair();

	String getKonanQuery();

	void clear();
}

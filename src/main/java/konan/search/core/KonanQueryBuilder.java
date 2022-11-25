package konan.search.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import konan.search.annotaions.KonanColumn;
import konan.search.annotaions.KonanTable;
import lombok.Getter;

public class KonanQueryBuilder<T> {

	private final Class<?> persistentClass;

	@Getter
	private WhereQueryBuilder<T> where = null;

	private KonanTable tableAnnotation = null;

	private List<KonanColumn> columnAnnotationList = null;

	public KonanQueryBuilder(Class<?> persistentClass) {

		this.persistentClass = persistentClass;

		tableAnnotation = getKonanTableAssign(persistentClass);
		columnAnnotationList = getKonanColumnAssign(persistentClass);

		this.where = new WhereQueryBuilder<T>(columnAnnotationList);
	}

	/**
	 * 코난 검색 entity의 테이블 정보를 조회합니다.
	 * @param clazz
	 * @return KonanTable를 반환합니다.
	 */
	private KonanTable getKonanTableAssign(Class<?> clazz) {
		if (clazz.isAnnotationPresent(KonanTable.class)) {
			return clazz.getAnnotation(KonanTable.class);
		}
		throw new IllegalArgumentException("not found KonanTable Annotaion");
	}

	/**
	 * 코난 검색 entity 테이블 컬럼 정보를 조회합니다.
	 * @param clazz 클레스 타입정보
	 * @return KonanColumn을 List로 반환합니다.
	 */
	private List<KonanColumn> getKonanColumnAssign(Class<?> clazz) {
		List<KonanColumn> columnsAnnotation = new ArrayList<>();
		Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
			if (field.isAnnotationPresent(KonanColumn.class)) {
				KonanColumn annotation = field.getAnnotation(KonanColumn.class);
				columnsAnnotation.add(annotation);
			}
		});
		return columnsAnnotation;
	}

	/**
	 * 완성된 쿼리를 반환합니다.
	 * @return 쿼리반환
	 */
	public String getQuery() {
		return where.getKonanQuery();
	}

}


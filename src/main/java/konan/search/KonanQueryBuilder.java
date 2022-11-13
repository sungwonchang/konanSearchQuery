package konan.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import konan.search.annotaions.KonanColumn;
import konan.search.annotaions.KonanTable;
import lombok.Getter;

@Getter
public class KonanQueryBuilder<T> {

	private final Class<?> persistentClass;

	private WhereQueryBuilder<T> where = null;
	private KonanTable tableAnnotation = null;
	private List<KonanColumn> columnAnnotationList = null;

	public KonanQueryBuilder(Class<?> persistentClass) {

		this.persistentClass = persistentClass;

		this.where = new WhereQueryBuilder<T>();

		tableAnnotation = getKonanTableAssign(persistentClass);
		columnAnnotationList = getKonanColumnAssign(persistentClass);
	}

	private KonanTable getKonanTableAssign(Class<?> clazz) {
		if (clazz.isAnnotationPresent(KonanTable.class)) {
			return clazz.getAnnotation(KonanTable.class);
		}
		return null;
	}

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

	public String getQuery() {
		return where.getQueryBuilder().toString();
	}

}


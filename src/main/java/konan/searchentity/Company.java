package konan.searchentity;

import konan.search.annotaions.KonanColumn;
import konan.search.annotaions.KonanTable;
import lombok.Getter;

@Getter
@KonanTable(view = "view_com", table = "company")
public class Company {
	@KonanColumn(name = "name", description = "회사명")
	String name;
	@KonanColumn(name = "addr", description = "주소")
	String address;
	@KonanColumn(name = "empcnt", description = "사원수")
	Integer employeeCount;
	@KonanColumn(name = "boss", description = "대표이름")
	String bossName;
	@KonanColumn(name = "welfare", description = "복지")
	String welfare;

	@KonanColumn(name = "codes", description = "기업형태코드 (여러개)")
	String codes;

	@KonanColumn(multiIndex = "ix_idx_name_and_boss", description = "멀티인덱스 name + boss")
	String multiIndex1;
}

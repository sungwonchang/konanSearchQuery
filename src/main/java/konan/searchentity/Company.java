package konan.searchentity;

import konan.search.annotaions.KonanColumn;
import konan.search.annotaions.KonanTable;
import lombok.Getter;

@Getter
@KonanTable(view = "view_com", table = "company")
public class Company {
	@KonanColumn(name = "c_name", description = "회사명")
	String name;
	@KonanColumn(name = "addr", description = "회사명")
	String address;
	@KonanColumn(name = "empcnt", description = "회사명")
	Integer employeeCount;
	@KonanColumn(name = "boss", description = "회사명")
	String bossName;
	@KonanColumn(name = "복지", description = "회사명")
	String welfare;
	@KonanColumn(multiIndex = "ix_idx_name_and_boss")
	String multiIndex1;
}

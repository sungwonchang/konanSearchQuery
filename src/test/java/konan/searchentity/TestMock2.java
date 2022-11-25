package konan.searchentity;

import konan.search.annotaions.KonanTable;

@KonanTable(view = "view_com", table = "company")
public class TestMock2 {
	String name;
	String address;
	Integer employeeCount;
	String bossName;
	String welfare;
	String codes;
	String multiIndex1;
}

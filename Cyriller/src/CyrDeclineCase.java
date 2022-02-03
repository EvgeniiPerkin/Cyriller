
public class CyrDeclineCase {
	private String nameRu;
	private String nameEn;
	private String description;
	private int index;
	private CasesEnum value;
	
	public CyrDeclineCase() {
		
	}
	
	public CyrDeclineCase(String nameRu, String nameEn, String description, int index, CasesEnum value) {
		this.nameRu = nameRu;
		this.nameEn = nameEn;
		this.description = description;
		this.index = index;
		this.value = value;
	}
	
	public String getNameRu() {
		return this.nameRu;
	}

	public String getNameEn() {
		return this.nameEn;
	}

	public String getDescription() {
		return this.description;
	}
	
	public int getIndex() {
		return this.index;
	}

	public CasesEnum getValue() {
		return this.value;
	}
	
	protected void setNameRu(String nameRu) {
		this.nameRu = nameRu;	
	}
	
	protected void setNameEn(String nameEn) {
		this.nameEn = nameEn;	
	}
	
	protected void setDescription(String description) {
		this.description = description;	
	}
	
	protected void setIndex(int index) {
		this.index = index;	
	}
	
	protected void setValue(CasesEnum value) {
		this.value = value;	
	}
	
	/**
	 * ������������ (Nominative), ���? ���? (����).
	 * @return ��������� ������ ������������ �����
	 */
	public static CyrDeclineCase Nominative() {
		return new CyrDeclineCase(
				"������������",
				"Nominative",
				"���? ���? (����)",
				1,
				CasesEnum.Nominative);
	}
	/**
	 * ����������� (Genitive), ����? ����? (���).
	 * @return ��������� ������ ����������� �����
	 */
	public static CyrDeclineCase Genitive() {
		return new CyrDeclineCase(
				"�����������",
				"Genitive",
				"����? ����? (���)",
				2,
				CasesEnum.Genitive);
	}
	/**
	 * ��������� (Dative), ����? ����? (���).
	 * @return ��������� ������ ��������� �����
	 */
	public static CyrDeclineCase Dative() {
		return new CyrDeclineCase(
				"���������",
				"Dative",
				"����? ����? (���)",
				3,
				CasesEnum.Dative);
	}
	/**
	 * ����������� (Accusative), ����? ���? (����).
	 * @return ��������� ������ ����������� �����
	 */
	public static CyrDeclineCase Accusative() {
		return new CyrDeclineCase(
				"�����������",
				"Accusative",
				"����? ���? (����)",
				4,
				CasesEnum.Accusative);
	}
	/**
	 * ������������ (Instrumental), ���? ���? (�������).
	 * @return ��������� ������ ������������ �����
	 */
	public static CyrDeclineCase Instrumental() {
		return new CyrDeclineCase(
				"������������",
				"Instrumental",
				"���? ���? (�������)",
				5,
				CasesEnum.Instrumental);
	}
	/**
	 * ���������� (Prepositional), � ���? � ���? (�����).
	 * @return ��������� ������ ���������� �����
	 */
	public static CyrDeclineCase Prepositional() {
		return new CyrDeclineCase(
				"����������",
				"Prepositional",
				"� ���? � ���? (�����)",
				6,
				CasesEnum.Prepositional);
	}
}

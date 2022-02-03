
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
	 * Именительный (Nominative), Кто? Что? (есть).
	 * @return экземпляр класса Именительный падеж
	 */
	public static CyrDeclineCase Nominative() {
		return new CyrDeclineCase(
				"Именительный",
				"Nominative",
				"Кто? Что? (есть)",
				1,
				CasesEnum.Nominative);
	}
	/**
	 * Родительный (Genitive), Кого? Чего? (нет).
	 * @return экземпляр класса Родительный падеж
	 */
	public static CyrDeclineCase Genitive() {
		return new CyrDeclineCase(
				"Родительный",
				"Genitive",
				"Кого? Чего? (нет)",
				2,
				CasesEnum.Genitive);
	}
	/**
	 * Дательный (Dative), Кому? Чему? (дам).
	 * @return экземпляр класса Дательный падеж
	 */
	public static CyrDeclineCase Dative() {
		return new CyrDeclineCase(
				"Дательный",
				"Dative",
				"Кому? Чему? (дам)",
				3,
				CasesEnum.Dative);
	}
	/**
	 * Винительный (Accusative), Кого? Что? (вижу).
	 * @return экземпляр класса Винительный падеж
	 */
	public static CyrDeclineCase Accusative() {
		return new CyrDeclineCase(
				"Винительный",
				"Accusative",
				"Кого? Что? (вижу)",
				4,
				CasesEnum.Accusative);
	}
	/**
	 * Творительный (Instrumental), Кем? Чем? (горжусь).
	 * @return экземпляр класса Творительный падеж
	 */
	public static CyrDeclineCase Instrumental() {
		return new CyrDeclineCase(
				"Творительный",
				"Instrumental",
				"Кем? Чем? (горжусь)",
				5,
				CasesEnum.Instrumental);
	}
	/**
	 * Предложный (Prepositional), О ком? О чем? (думаю).
	 * @return экземпляр класса Предложный падеж
	 */
	public static CyrDeclineCase Prepositional() {
		return new CyrDeclineCase(
				"Предложный",
				"Prepositional",
				"О ком? О чем? (думаю)",
				6,
				CasesEnum.Prepositional);
	}
}

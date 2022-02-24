
public abstract class Currency {
	protected void setName(String name) {
		this.name = name;
	}
	protected void setIntegerGender(GendersEnum integerGender) {
		this.integerGender = integerGender;
	}
	protected void setDecimalGender(GendersEnum decimalGender) {
		this.decimalGender = decimalGender;
	}
	protected void setDecimals(int decimals) {
		this.decimals = decimals;
	}
	public String getName() {
		return name;
	}
	public GendersEnum getIntegerGender() {
		return integerGender;
	}
	public GendersEnum getDecimalGender() {
		return decimalGender;
	}
	public int getDecimals() {
		return decimals;
	}
	private String name;
	private GendersEnum integerGender;
	private GendersEnum decimalGender;
	private int decimals;
	
    public abstract String[] GetIntegerName(CasesEnum cases);
    public abstract String[] GetDecimalName(CasesEnum cases);
}

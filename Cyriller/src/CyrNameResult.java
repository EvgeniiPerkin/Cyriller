
public class CyrNameResult {
	
	/**имя*/
	private String name;
	/**фамилия*/
	private String surname;
	/**отчество*/
	private String patronumic;
	
	/**
	 * Создает экземпляр класса и заполняет значения surname, name, patronumic
	 * @param surname - фамилия
	 * @param name - имя
	 * @param patronumic - отчество 
	 * @see CyrNameResult#CyrNameResult(String[])
	 */
	public CyrNameResult(String surname, String name, String patronumic) {
		this.surname = surname;
		this.name = name;
		this.patronumic = patronumic;
	}
	/**
	 * Создает экземпляр класса и заполняет значения surname, ame, patronumic из массива
	 * @param values - массив с инициалами
	 * @see CyrNameResult#CyrNameResult(String, String, String)
	 */
	public CyrNameResult(String[] values) {
		if (values == null) {
			throw new NullPointerException();
		}
		if (values.length != 2 && values.length != 3){
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	/**
	 * Возвращает инициалы в виде строки {@link CyrNameResult#name} +  {@link CyrNameResult#surname} + {@link CyrNameResult#patronumic}
	 * @return инициалы
	 */
	public String getFullName(){
		String[] values = new String[] {
				this.surname, this.name, this.patronumic
		};
		
		String fullname = String.join(" ", values);
		
		return fullname;
	}
	
	@Override
	public String toString() {
		return this.getFullName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CyrNameResult))
			return false;
		if (obj == this)
			return true;
		CyrNameResult rst = (CyrNameResult)obj;
	    return this.name.equals(rst.getName())
	    		&& this.surname.equals(rst.getSurname())
	    		&& this.patronumic.equals(rst.getPatronumic());
	}
	/**
	 * Возвращает значение из поля {@link CyrNameResult#name}
	 * @return имя
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Возвращает значение из поля {@link CyrNameResult#surname}
	 * @return фамилия
	 */
	public String getSurname() {
		return this.surname;
	}
	/**
	 * Возвращает значение из поля {@link CyrNameResult#patronumic}
	 * @return отчество
	 */
	public String getPatronumic() {
		return this.patronumic;
	}
}

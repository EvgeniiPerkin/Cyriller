package DeclensionOfNames;
import java.util.ArrayList;

public class CyrNameResult {
	
	/**Имя*/
	private String name;
	/**Фамилия*/
	private String surname;
	/**Отчество*/
	private String patronumic;
	
	/**
	 * Создает экземпляр класса
	 * @param surname 
	 * @param name
	 * @param patronumic
	 * @see CyrNameResult#CyrNameResult(String[])
	 */
	public CyrNameResult(String surname, String name, String patronumic) {
		this.surname = surname;
		this.name = name;
		this.patronumic = patronumic;
	}
	/**
	 * Создает экземпляр класса
	 * @param values
	 * @see CyrNameResult#CyrNameResult(String, String, String)
	 */
	public CyrNameResult(String[] values) {
		if (values == null) {
			throw new NullPointerException();
		}
		if (values.length != 2 && values.length != 3){
			throw new ArrayIndexOutOfBoundsException();
		}
		
        this.surname = values[0];
        this.name = values[1];
        this.patronumic = values.length == 3 ? values[2] : null;
	}
	/**
	 * {@link CyrNameResult#name} +  {@link CyrNameResult#surname} + {@link CyrNameResult#patronumic}
	 * @return полное имя
	 */
	public String getFullName(){
		ArrayList<String> results = new ArrayList<String>();
		
		if (this.surname != null && !this.surname.isEmpty()) {
			results.add(this.surname);
		}
		
		if (this.name != null && !this.name.isEmpty()) {
			results.add(this.name);
		}
		
		if (this.patronumic != null && !this.patronumic.isEmpty()) {
			results.add(this.patronumic);
		}
		
		String fullname = String.join(" ", results);
		
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
	 * {@link CyrNameResult#name}
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * {@link CyrNameResult#surname}
	 * @return 
	 */
	public String getSurname() {
		return this.surname;
	}
	/**
	 *  {@link CyrNameResult#patronumic}
	 * @return 
	 */
	public String getPatronumic() {
		return this.patronumic;
	}
}

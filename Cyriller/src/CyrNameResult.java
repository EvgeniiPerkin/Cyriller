
public class CyrNameResult {
	
	private String name;
	private String surname;
	private String patronumic;
	
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
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public String getPatronumic() {
		return this.patronumic;
	}
}

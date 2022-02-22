import java.util.ArrayList;
import java.util.Collection;

public class CyrResult {
	/**Именительный*/
	protected String nominative;
	/**Родительный*/
	protected String genitive;
	/**Дательный*/
	protected String dative;
	/**Винительный*/
	protected String accusative;
	/**Творительный*/
	protected String instrumental;
	/**Предложный*/
	protected String prepositional;

	public CyrResult() {
		
	}
	
	public CyrResult(String word) {
		this.nominative = word;
		this.genitive = word;
		this.dative = word;
		this.accusative = word;
		this.instrumental = word;
		this.prepositional = word;
	}
	
	public CyrResult(String nominative,String genitive,String dative,String accusative,String instrumental,String prepositional) {
		this.nominative = nominative;
		this.genitive = genitive;
		this.dative = dative;
		this.accusative = accusative;
		this.instrumental = instrumental;
		this.prepositional = prepositional;
	}
	
	public CyrResult(String[] cases) {
		if (cases.length != 6) {
			throw new IllegalArgumentException();
		}
		this.nominative = cases[0];
		this.genitive = cases[1];
		this.dative = cases[2];
		this.accusative = cases[3];
		this.instrumental = cases[4];
		this.prepositional = cases[5];
	}
	
	public static CyrResult Concatenate(CyrResult a, CyrResult b) {
		return new CyrResult(a.nominative + " " + b.nominative,
				a.genitive + " " + b.genitive,
				a.dative + " " + b.dative,
				a.accusative + " " + b.accusative,
				a.instrumental + " " + b.instrumental,
				a.prepositional + " " + b.prepositional);
	}
	
	/**
	 * Именительный, Кто? Что? (есть)
	 * @return @see {@link CyrResult#nominative}
	 */
	public String getNominative() {
		return this.nominative;
	}
	/**
	 * Родительный, Кого? Чего? (нет)
	 * @return @see {@link CyrResult#genitive}
	 */
	public String getGenitive(){
		return this.genitive;
	}
	/**
	 * Дательный, Кому? Чему? (дам)
	 * @return @see {@link CyrResult#dative}
	 */
	public String getDative(){
		return this.dative;
	}
	/**
	 * Винительный, Кого? Что? (вижу)
	 * @return @see {@link CyrResult#accusative}
	 */
	public String getAccusative(){
		return this.accusative;
	}
	/**
	 * Творительный, Кем? Чем? (горжусь)
	 * @return @see {@link CyrResult#instrumental}
	 */
	public String getInstrumental(){
		return this.instrumental;
	}
	/**
	 * Предложный, О ком? О чем? (думаю)
	 * @return @see {@link CyrResult#prepositional}
	 */
	public String getPrepositional(){
		return this.prepositional;
	}
	
	public String Get(CasesEnum cases) {
		switch(cases) {
			case Nominative:return this.nominative;
			case Genitive:return this.genitive;
			case Dative:return this.dative;
			case Accusative:return this.accusative;
			case Instrumental:return this.instrumental;
			case Prepositional:return this.prepositional;
			default: return this.nominative;
		}
	}

    public void Set(CasesEnum cases, String value){
        switch (cases){
            case Nominative: this.nominative = value; break;
            case Genitive: this.genitive = value; break;
            case Dative: this.dative = value; break;
            case Accusative:  this.accusative = value; break;
            case Instrumental: this.instrumental = value; break;
            case Prepositional: this.prepositional = value; break;
            default: this.nominative = value; break;
        }
    }
    
    public void Add(CyrResult result, String separator){
    	if (separator.isEmpty()) {
    		separator = "-";
    	}
        this.nominative += separator + result.nominative;
        this.genitive += separator + result.genitive;
        this.dative += separator + result.dative;
        this.accusative += separator + result.accusative;
        this.instrumental += separator + result.instrumental;
        this.prepositional += separator + result.prepositional;
    }
    
    public Collection<String> ToList(){
    	ArrayList<String> result = new ArrayList<String>();
    	result.add(this.nominative);
    	result.add(this.genitive);
    	result.add(this.dative);
    	result.add(this.accusative);
    	result.add(this.instrumental);
    	result.add(this.prepositional);
    	return result;
    }
    
    public String[] ToArray() {
    	return new String[] {this.nominative, this.genitive, this.dative, this.accusative, this.instrumental, this.prepositional};
    }
}

import java.util.ArrayList;

public class CyrNoun extends CyrBaseWord{
    public final String Hyphen = "-";
    private String name;
	private GendersEnum gender;
    private AnimatesEnum animate;
    private WordTypesEnum wordType;
    protected CyrRule[] rules;
    protected int rulesPerNoun = 11;

	public String getName() {
		return name;
	}
	public GendersEnum getGender() {
		return gender;
	}
	public AnimatesEnum getAnimate() {
		return animate;
	}
	public WordTypesEnum getWordType() {
		return wordType;
	}
	protected void setName(String name) {
		this.name = name;
	}
	protected void setGender(GendersEnum gender) {
		this.gender = gender;
	}
	protected void setAnimate(AnimatesEnum animate) {
		this.animate = animate;
	}
	protected void setWordType(WordTypesEnum wordType) {
		this.wordType = wordType;
	}
	public CyrNoun(String name, GendersEnum gender, AnimatesEnum animate, WordTypesEnum type, CyrRule[] rules) {
		this.name = name;
		this.gender = gender;
		this.animate = animate;
		this.wordType = type;
		this.rules = rules;
	}
	public CyrNoun(CyrNoun source) {
        this.name = source.getName();
        this.gender = source.getGender();
        this.animate = source.getAnimate();
        this.wordType = source.getWordType();
        this.rules = source.rules;
    }
	public boolean IsAnimated() {
		return this.animate == AnimatesEnum.Animated;
	}
	public CyrResult Decline() {
        String[] parts = this.name.split(Hyphen);

        if (parts.length == 1 || this.rules.length <= this.rulesPerNoun) {
            CyrRule[] rules = this.GetRules(NumbersEnum.Singular, 0);
            CyrResult result = this.GetResult(this.getName(), rules);

            return result;
        }

        return this.DeclineMultipleParts(parts, NumbersEnum.Singular);
    }	
	public CyrResult DeclinePlural() {
        String[] parts = this.name.split(Hyphen);

        if (parts.length == 1 || this.rules.length <= this.rulesPerNoun) {
            CyrRule[] rules = this.GetRules(NumbersEnum.Plural, 0);
            CyrResult result = this.GetResult(this.getName(), rules);

            return result;
        }

        return this.DeclineMultipleParts(parts, NumbersEnum.Plural);
    }	
	public void SetName(String name, CasesEnum cases, NumbersEnum number) {
         if (this.rules.length > rulesPerNoun) {
             throw new UnsupportedOperationException("is not yet supported for composite nouns.");
         }

         CyrRule[] rules = this.GetRules(number, 0);
         CyrRule rule = rules[(int)cases.getValue() - 1];

         this.name = rule.Revert(this.name, name);
    }	
	protected CyrResult DeclineMultipleParts(String[] parts, NumbersEnum number) {
        ArrayList<CyrResult> results = new ArrayList<CyrResult>();

        for (int i = 0; i < parts.length; i++) {
            CyrRule[] rules = this.GetRules(number, i);
            CyrResult result = this.GetResult(parts[i], rules);

            results.add(result);
        }

        return this.JoinResults(results);
    }	
	protected CyrResult JoinResults(ArrayList<CyrResult> results) {
		String Nominative = String.join(Hyphen, results.get(1).ToArray());
		String Genitive = String.join(Hyphen, results.get(2).ToArray());
		String Dative = String.join(Hyphen, results.get(3).ToArray());
		String Accusative = String.join(Hyphen, results.get(4).ToArray());
		String Instrumental = String.join(Hyphen, results.get(5).ToArray());
		String Prepositional = String.join(Hyphen, results.get(6).ToArray());
		
        CyrResult result = new CyrResult(Nominative, Genitive, Dative, Accusative, Instrumental, Prepositional);

        return result;
    }	
	protected CyrRule[] GetRules(NumbersEnum number, int wordPartIndex) {
        if (number == NumbersEnum.Singular) {
            return new CyrRule[] {
                new CyrRule(""),
                this.rules[0 + wordPartIndex * rulesPerNoun],
                this.rules[1 + wordPartIndex * rulesPerNoun],
                this.rules[2 + wordPartIndex * rulesPerNoun],
                this.rules[3 + wordPartIndex * rulesPerNoun],
                this.rules[4 + wordPartIndex * rulesPerNoun]
            };
        }
        else {
            return new CyrRule[] {
                this.rules[5 + wordPartIndex * rulesPerNoun],
                this.rules[6 + wordPartIndex * rulesPerNoun],
                this.rules[7 + wordPartIndex * rulesPerNoun],
                this.rules[8 + wordPartIndex * rulesPerNoun],
                this.rules[9 + wordPartIndex * rulesPerNoun],
                this.rules[10 + wordPartIndex * rulesPerNoun]
            };
        }
    }
}

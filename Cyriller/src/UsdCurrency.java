
public class UsdCurrency extends Currency{
	public UsdCurrency(){
        this.setName("Американский доллар (USD) $");
        this.setIntegerGender(GendersEnum.Masculine);
        this.setDecimalGender(GendersEnum.Feminine);
        this.setDecimals(2);
    }
	
	@Override
	public String[] GetIntegerName(CasesEnum cases) {
        switch (cases)
        {
            case Nominative:
                return new String[] { "доллар", "доллара", "долларов" };
            case Genitive:
                return new String[] { "доллара", "долларов", "долларов" };
            case Dative:
                return new String[] { "доллару", "долларам", "долларам" };
            case Accusative:
                return new String[] { "доллар", "доллара", "долларов" };
            case Instrumental:
                return new String[] { "долларов", "долларами", "долларами" };
            case Prepositional:
                return new String[] { "долларе", "долларах", "долларах" };
        }

        throw new IllegalArgumentException("Invalid decline case!");
	}

	@Override
	public String[] GetDecimalName(CasesEnum cases) {
        switch (cases)
        {
            case Nominative:
                return new String[] { "цент", "цента", "центов" };
            case Genitive:
                return new String[] { "цента", "центов", "центов" };
            case Dative:
                return new String[] { "центу", "центам", "центам" };
            case Accusative:
                return new String[] { "цент", "цента", "центов" };
            case Instrumental:
                return new String[] { "центом", "центами", "центами" };
            case Prepositional:
                return new String[] { "центе", "центах", "центах" };
        }

        throw new IllegalArgumentException("Invalid decline case!");
	}

}
